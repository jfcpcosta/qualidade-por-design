package loja.notificacoes;

import loja.encomendas.Order;
import loja.precos.PriceBreakdown;

public class ConfirmationEmail {

    public String body(Order order, PriceBreakdown price) {
        return "Obrigado pela sua encomenda " + order.id() + "! Total pago: " + price.total();
    }
}
