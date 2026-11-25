package loja.erp;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

/** Nightly CSV for the ERP. Works in cents "to avoid floating point problems". */
public class ErpExport {

    public String csvLine(Order order) {
        long cents = 0;

        for (OrderLine line : order.lines()) {
            cents += line.unitPrice().movePointRight(2).longValue() * line.quantity();
        }

        int discountPercent = 0;

        if ("BEMVINDO10".equals(order.couponCode())) {
            discountPercent = 10;
        }

        long netCents = cents * (100 - discountPercent) / 100;

        return order.id() + ";" + netCents;
    }

    public long totalInCents(Order order) {
        String line = csvLine(order);

        return Long.parseLong(line.substring(line.indexOf(';') + 1));
    }
}
