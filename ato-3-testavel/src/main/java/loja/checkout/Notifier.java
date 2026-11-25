package loja.checkout;

import loja.encomendas.Order;
import loja.precos.PriceBreakdown;

public interface Notifier {

    void orderConfirmed(Order order, PriceBreakdown price);
}
