package loja.faturacao;

import loja.encomendas.Order;
import loja.precos.PriceBreakdown;

/** Shows the price on the invoice. Same numbers as the charge, by construction. */
public class InvoiceService {

    public String invoice(Order order, PriceBreakdown price) {
        return """
                Fatura %s
                Subtotal: %s
                Desconto: -%s
                Total:    %s
                """.formatted(order.id(), price.subtotal(), price.discount(), price.total());
    }
}
