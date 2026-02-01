package org.learn.irctc.service;

import di.annotation.Component;

@Component
public class DiscountService {
    private final OfferService offerService;

    public DiscountService(OfferService offerService) {
        this.offerService = offerService;
    }

    public void applyDiscount(int bookingId) {
        System.out.println("Discount Service : Applying discount for booking " + bookingId);
        offerService.applyOffer();
    }
}
