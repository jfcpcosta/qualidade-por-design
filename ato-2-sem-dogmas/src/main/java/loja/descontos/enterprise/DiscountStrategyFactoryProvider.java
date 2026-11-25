package loja.descontos.enterprise;

/** In case we ever need a different factory. We never did. */
public final class DiscountStrategyFactoryProvider {

    private static DiscountStrategyFactory instance = new DefaultDiscountStrategyFactory();

    private DiscountStrategyFactoryProvider() {
    }

    public static DiscountStrategyFactory getFactory() {
        return instance;
    }

    public static void setFactory(DiscountStrategyFactory factory) {
        instance = factory;
    }
}
