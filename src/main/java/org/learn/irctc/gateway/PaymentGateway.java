package org.learn.irctc.gateway;


import di.annotation.Component;

@Component
public class PaymentGateway {
    public void processPayment(int bookingId) {
        System.out.println("Processing payment for booking " + bookingId);
    }
}
