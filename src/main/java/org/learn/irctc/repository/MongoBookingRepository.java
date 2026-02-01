package org.learn.irctc.repository;


import di.annotation.Component;
import di.annotation.Primary;
import di.annotation.Property;

@Component
@Primary
public class MongoBookingRepository implements BookingRepository {

    private final String mongoUrl;

    public MongoBookingRepository(@Property("dm.mongo.connection-uri") String mongoUrl) {
        this.mongoUrl = mongoUrl;
    }

    @Override
    public void save(String user, String start, String destination) {
        System.out.println("Mongo Booking Repository : Connecting to Mongo at " + mongoUrl + "");
        System.out.println("Mongo Booking Repository : Saving booking for user " + user + " from " + start + " to " + destination);
    }
}
