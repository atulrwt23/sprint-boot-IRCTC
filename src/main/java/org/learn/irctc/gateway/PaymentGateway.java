package org.learn.irctc.gateway;

import org.learn.di.annotation.Component;

@Component
public class PaymentGateway {
    public void processPayment(int bookingId) {
        System.out.println("Processing payment for booking " + bookingId);
    }
}
