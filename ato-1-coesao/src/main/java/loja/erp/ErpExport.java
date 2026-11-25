package loja.erp;

import loja.encomendas.Order;
import loja.precos.PriceBreakdown;

public class ErpExport {

    public String csvLine(Order order, PriceBreakdown price) {
        return order.id() + ";" + price.total().cents();
    }
}
