package loja.encomendas;

import java.util.List;

public record Order(String id, String customerEmail, List<OrderLine> lines, String couponCode) {
}
