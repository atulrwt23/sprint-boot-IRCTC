package org.learn.irctc.repository;

public interface BookingRepository {
    void save(String user, String start, String destination);
}
