package loja;

import java.math.BigDecimal;
import java.util.List;

import loja.checkout.CheckoutService;
import loja.encomendas.Order;
import loja.encomendas.OrderLine;
import loja.erp.ErpExport;
import loja.faturacao.InvoiceService;
import loja.notificacoes.ConfirmationEmail;

/**
 * Simulates Black Friday in production: the campaign switch is on
 * and every order goes through the four places that know the price.
 */
public class Sexta {

    public static void main(String[] args) {
        System.setProperty("loja.blackfriday", "true");

        List<Order> orders = List.of(
                order("BF-1001", "BEMVINDO10", line("AURICULARES", "49.99", 1)),
                order("BF-1002", null, line("CAPA", "12.49", 3), line("CABO-USB", "7.95", 2)),
                order("BF-1003", "BEMVINDO10", line("CABO-USB", "7.95", 3), line("CAPA", "12.49", 1)));

        CheckoutService checkout = new CheckoutService();
        InvoiceService invoices = new InvoiceService();
        ConfirmationEmail email = new ConfirmationEmail();
        ErpExport erp = new ErpExport();

        System.out.printf("%n%-9s %10s %10s %10s %10s%n", "ENCOMENDA", "COBRADO", "FATURA", "EMAIL", "ERP");

        for (Order order : orders) {
            BigDecimal charged = checkout.totalToCharge(order);
            BigDecimal invoiced = invoices.invoiceTotal(order);
            double emailed = email.totalShown(order);
            BigDecimal exported = BigDecimal.valueOf(erp.totalInCents(order), 2);

            boolean allEqual = charged.compareTo(invoiced) == 0
                    && charged.compareTo(BigDecimal.valueOf(emailed)) == 0
                    && charged.compareTo(exported) == 0;

            System.out.printf("%-9s %10s %10.2f %10.2f %10s  %s%n",
                    order.id(), charged, invoiced, emailed, exported, allEqual ? "ok" : "<-- DIFERENTE");
        }

        System.out.println();
    }

    private static Order order(String id, String coupon, OrderLine... lines) {
        return new Order(id, "cliente@exemplo.pt", List.of(lines), coupon);
    }

    private static OrderLine line(String sku, String price, int quantity) {
        return new OrderLine(sku, new BigDecimal(price), quantity);
    }
}
