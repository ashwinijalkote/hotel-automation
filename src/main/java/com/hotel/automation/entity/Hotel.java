package com.hotel.automation.entity;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Hotel {
    LinkedHashMap<Integer, Floor> floors;

    public Hotel(int floorCount, int numMainCorridors, int numSubCorridors) {
        floors = new LinkedHashMap<>();
        for(int i =0; i < floorCount; i++ ) {
            floors.put(i+1, new Floor.Builder()
                    .addMainCorridors(numMainCorridors)
                    .addSubCorridors(numSubCorridors)
                    .build()
            );
        }
        this.floors = floors;
    }

    public LinkedHashMap<Integer, Floor> getFloors() {
        return floors;
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    public int getPowerConsumption( ) {
        AtomicInteger powerConsumption = new AtomicInteger();
        floors.forEach((index, floor) -> {
            powerConsumption.addAndGet(floor.getPowerConsumption());
        });
        return powerConsumption.intValue();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        floors.forEach((floorNumber, floor) -> {
            sb.append("Floor " + floorNumber + "\n");
            sb.append(floor.toString() + "\n");
        });
        return sb.toString();
    }
}
