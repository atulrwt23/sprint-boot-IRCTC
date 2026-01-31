package org.learn.irctc.repository;

import org.learn.di.annotation.Component;
import org.learn.irctc.model.Station;

import java.util.HashMap;

@Component
public class StationRepository {
    private static final HashMap<String, Station>stations = new HashMap<>();

    static {
        stations.put("Delhi", new Station("Delhi", "01"));
        stations.put("Gurgaon", new Station("Gurgaon", "02"));
        stations.put("Noida", new Station("Noida", "03"));
        stations.put("Mumbai", new Station("Mumbai", "04"));
        stations.put("Bangalore", new Station("Bangalore", "05"));
        stations.put("Chennai", new Station("Chennai", "06"));
    }

    public static Station getStation(String stationName) {
        System.out.println("Station Repository : Getting Station " + stationName);
        return stations.get(stationName);
    }
}
