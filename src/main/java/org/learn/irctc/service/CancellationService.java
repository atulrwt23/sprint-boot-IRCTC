package org.learn.irctc.service;


import di.annotation.Component;

@Component
public class CancellationService {
    private final BookingService bookingService;

    public CancellationService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void cancelBooking(int bookingId) {
        System.out.println("Cancellation Service : Canceling booking " + bookingId);
    }

    public BookingService getBookingService() {
        return bookingService;
    }
}
