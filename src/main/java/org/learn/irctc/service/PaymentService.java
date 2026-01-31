package org.learn.irctc.service;

import org.learn.irctc.gateway.PaymentGateway;

public class PaymentService {
    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void processPayment(int bookingId) {
        System.out.println("Payment Service : Processing payment for booking " + bookingId);
        paymentGateway.processPayment(bookingId);
    }
}
