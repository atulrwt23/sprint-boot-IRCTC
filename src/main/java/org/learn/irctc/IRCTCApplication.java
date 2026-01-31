package org.learn.irctc;

import org.learn.di.ApplicationContext;
import org.learn.di.ComponentScanner;
import org.learn.irctc.service.BookingService;
import org.learn.irctc.service.CancellationService;

public class IRCTCApplication {
    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------");
        ApplicationContext.getInstance();
        System.out.println("---------------------------------------------------------");
    }

//    private static void performBookingAction() {
//        ApplicationContext applicationContext = ApplicationContext.getInstance();
//        applicationContext.init();
//        BookingService bookingService = applicationContext.get(BookingService.class);
//        CancellationService cancellationService = applicationContext.get(CancellationService.class);
//
//        if (bookingService.equals(cancellationService.getBookingService())) {
//            System.out.println("Both BookingService instances are the same (Singleton)");
//        } else {
//            System.out.println("BookingService instances are different");
//        }
//    }
//
//    private static void createBooking(ApplicationContext applicationContext) {
//        BookingService bookingService = applicationContext.get(BookingService.class);
//        CancellationService cancellationService = applicationContext.get(CancellationService.class);
//
//        bookingService.bookTicket("User1", "StationA", "StationB");
//        cancellationService.cancelBooking(123);
//    }

}
