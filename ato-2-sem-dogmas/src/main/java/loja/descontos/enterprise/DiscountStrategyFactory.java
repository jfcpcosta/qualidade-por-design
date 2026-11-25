package loja.descontos.enterprise;

public interface DiscountStrategyFactory {

    DiscountStrategy create(DiscountType type);
}
