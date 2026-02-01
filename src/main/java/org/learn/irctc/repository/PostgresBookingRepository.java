package org.learn.irctc.repository;

import di.annotation.Component;

@Component
public class PostgresBookingRepository implements BookingRepository {
    @Override
    public void save(String user, String start, String destination) {
        System.out.println("Booking Repository : Saving booking for user " + user + " from " + start + " to " + destination);
    }
}
