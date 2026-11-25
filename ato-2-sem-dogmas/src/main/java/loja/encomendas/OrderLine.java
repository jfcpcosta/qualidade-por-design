package loja.encomendas;

import java.math.BigDecimal;

public record OrderLine(String sku, BigDecimal unitPrice, int quantity) {
}
