package org.learn.irctc.repository;

import org.learn.di.annotation.Component;
import org.learn.di.annotation.Primary;

@Component
@Primary
public class MongoBookingRepository implements BookingRepository {
    @Override
    public void save(String user, String start, String destination) {
        System.out.println("Mongo Booking Repository : Saving booking for user " + user + " from " + start + " to " + destination);
    }
}
