package loja;

import java.math.BigDecimal;
import java.util.List;

import loja.checkout.CheckoutService;
import loja.encomendas.Order;
import loja.encomendas.OrderLine;
import loja.erp.ErpExport;
import loja.faturacao.InvoiceService;
import loja.notificacoes.ConfirmationEmail;
import loja.precos.Discounts;
import loja.precos.PriceBreakdown;
import loja.precos.PricingPolicy;

/** Same Black Friday as act 0. The price is calculated once and shown four times. */
public class Sexta {

    public static void main(String[] args) {
        List<Order> orders = List.of(
                order("BF-1001", "BEMVINDO10", line("AURICULARES", "49.99", 1)),
                order("BF-1002", null, line("CAPA", "12.49", 3), line("CABO-USB", "7.95", 2)),
                order("BF-1003", "BEMVINDO10", line("CABO-USB", "7.95", 3), line("CAPA", "12.49", 1)));

        PricingPolicy pricing = new PricingPolicy();
        Discounts discounts = new Discounts(true);

        CheckoutService checkout = new CheckoutService();
        InvoiceService invoices = new InvoiceService();
        ConfirmationEmail email = new ConfirmationEmail();
        ErpExport erp = new ErpExport();

        System.out.printf("%n%-9s %10s %10s %10s %10s%n", "ENCOMENDA", "COBRADO", "FATURA", "EMAIL", "ERP");

        for (Order order : orders) {
            PriceBreakdown price = pricing.price(order, discounts.applicableTo(order));

            String invoiced = lastAmount(invoices.invoice(order, price).strip());
            String emailed = lastAmount(email.body(order, price));
            String exported = BigDecimal.valueOf(Long.parseLong(lastField(erp.csvLine(order, price))), 2)
                    .toPlainString();

            System.out.printf("%-9s %10s %10s %10s %10s  ok%n",
                    order.id(), checkout.totalToCharge(price).amount(), invoiced, emailed, exported);
        }

        System.out.println();
    }

    private static String lastAmount(String text) {
        String[] words = text.split("\\s+");

        return words[words.length - 2];
    }

    private static String lastField(String csvLine) {
        return csvLine.substring(csvLine.lastIndexOf(';') + 1);
    }

    private static Order order(String id, String coupon, OrderLine... lines) {
        return new Order(id, "cliente@exemplo.pt", List.of(lines), coupon);
    }

    private static OrderLine line(String sku, String price, int quantity) {
        return new OrderLine(sku, new BigDecimal(price), quantity);
    }
}
