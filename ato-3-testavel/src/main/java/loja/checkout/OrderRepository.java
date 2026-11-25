package loja.checkout;

import loja.encomendas.Order;
import loja.precos.PriceBreakdown;

public interface OrderRepository {

    void save(Order order, PriceBreakdown price);
}
