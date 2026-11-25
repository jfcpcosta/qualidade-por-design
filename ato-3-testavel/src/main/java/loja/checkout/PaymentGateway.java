package loja.checkout;

import loja.precos.Money;

public interface PaymentGateway {

    void charge(String orderId, Money amount);
}
