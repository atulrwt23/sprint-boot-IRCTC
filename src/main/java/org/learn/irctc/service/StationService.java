package org.learn.irctc.service;

import di.annotation.Component;
import org.learn.irctc.repository.StationRepository;

@Component
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
