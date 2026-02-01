package org.learn.irctc;

import org.learn.di.ApplicationContext;
import org.learn.irctc.repository.BookingRepository;
import org.learn.irctc.service.BookingService;
import org.learn.irctc.service.CancellationService;

public class IRCTCApplication {
    public static void main(String[] args) {
        System.out.println("====================================================================");
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        BookingRepository bean = applicationContext.getBean(BookingRepository.class);
        BookingService bookingService = applicationContext.getBean(BookingService.class);
        bookingService.bookTicket("user", "start", "destination");
        System.out.println(bean);
        System.out.println("====================================================================");
    }
}
