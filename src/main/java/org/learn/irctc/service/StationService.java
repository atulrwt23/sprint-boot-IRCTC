package org.learn.irctc.service;

import org.learn.irctc.repository.StationRepository;

public class StationService {
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void searchStation(String station) {
        System.out.println("Station Service : Searching station " + station);
        stationRepository.getStation(station);
    }
}
