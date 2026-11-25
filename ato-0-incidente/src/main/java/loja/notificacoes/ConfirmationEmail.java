package loja.notificacoes;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

/** Owned by the CRM team. "It's just an email." */
public class ConfirmationEmail {

    public String body(Order order) {
        double total = 0;

        for (OrderLine line : order.lines()) {
            total += line.unitPrice().doubleValue() * line.quantity();
        }

        if ("BEMVINDO10".equalsIgnoreCase(order.couponCode())) {
            total = total * 0.9;
        }

        return "Obrigado pela sua encomenda " + order.id()
                + "! Total pago: " + String.format("%.2f", total) + " €";
    }

    public double totalShown(Order order) {
        String body = body(order);
        String amount = body.substring(body.lastIndexOf(": ") + 2, body.length() - 2);

        return Double.parseDouble(amount.replace(',', '.'));
    }
}
