package loja.config;

/**
 * Global switches read at runtime. Marketing turns campaigns on in production;
 * the test suite never does, so nothing here is exercised by the tests.
 */
public final class LojaConfig {

    private LojaConfig() {
    }

    public static boolean blackFridayActive() {
        return Boolean.getBoolean("loja.blackfriday");
    }
}
