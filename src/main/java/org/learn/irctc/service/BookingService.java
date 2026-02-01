package org.learn.irctc.service;

import di.annotation.Component;
import org.learn.irctc.repository.BookingRepository;

@Component
public class BookingService {
    private final StationService stationService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final DiscountService discountService;
    private final BookingRepository bookingRepository;

    public BookingService(StationService stationService, UserService userService, PaymentService paymentService, DiscountService discountService, BookingRepository bookingRepository) {
        this.stationService = stationService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.discountService = discountService;
        this.bookingRepository = bookingRepository;
    }

    public void bookTicket(String user, String start, String destination) {
        System.out.println("Booking Service : Booking ticket for user " + user + " from " + start + " to " + destination);
        validateUser(user);
        searchStation(start);
        searchStation(destination);
        applyDiscounts(user);
        processPayment(user);
        saveBooking(user, start, destination);
    }

    private void saveBooking(String user, String start, String destination) {
        bookingRepository.save(user, start, destination);
    }

    private void processPayment(String user) {
        paymentService.processPayment(1000);
    }

    private void applyDiscounts(String user) {
        discountService.applyDiscount(1);
    }

    private void searchStation(String station) {
        stationService.searchStation(station);
    }

    private void validateUser(String user) {
        System.out.println("Booking Service : Validating user " + user);
        userService.getUserDetails();
    }
}
