package com.hotel.automation.entity;

import com.hotel.automation.app.HotelAutomationApp;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Floor {
    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);

    private HashMap<Integer, MainCorridor> mainCorridors;
    private Floor(HashMap<Integer, MainCorridor> mainCorridors, HashMap<Integer, SubCorridor> subCorridors) {
        this.mainCorridors = mainCorridors;
        this.subCorridors = subCorridors;
    }

    private HashMap<Integer, SubCorridor> subCorridors;

    public static class Builder {
        HashMap<Integer, MainCorridor> mainCorridors = new HashMap<>();
        HashMap<Integer, SubCorridor> subCorridors= new HashMap<>();

        public Builder addMainCorridors(int numMainCorridors) {
            for( int i=0; i< numMainCorridors; i++) {
                mainCorridors.put(i+1, new MainCorridor());
            }
            return this;
        }

        public Builder addSubCorridors(int numSubCorridors) {
            for( int i=0; i< numSubCorridors; i++) {
                subCorridors.put(i+1, new SubCorridor());
            }
            return this;
        }

        public Floor build() {
            return new Floor(mainCorridors, subCorridors);
        }
    }

    public int getPowerConsumption( ) {
        AtomicInteger powerConsumption = new AtomicInteger();
        mainCorridors.forEach((index, mainCorridor) -> {
            powerConsumption.addAndGet(mainCorridor.getPowerConsumption());
            logger.info("Main corridor pc " + mainCorridor.getPowerConsumption() + "  " + powerConsumption);
        });
        subCorridors.forEach((index, subCorridor) -> {
            powerConsumption.addAndGet(subCorridor.getPowerConsumption());
            logger.info("sub corridor pc " + subCorridor.getPowerConsumption() + "  " + powerConsumption);
        });
        return powerConsumption.intValue();
    }

    public HashMap<Integer, MainCorridor> getMainCorridors() {
        return mainCorridors;
    }

    public HashMap<Integer, SubCorridor> getSubCorridors() {
        return subCorridors;
    }

    public SubCorridor getSubCorridor(int subCorridorNumber) {
        return subCorridors.get(subCorridorNumber);
    }

    public MainCorridor getMainCorridor(int mainCorridorNumber) {
        return mainCorridors.get(mainCorridorNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        mainCorridors.forEach((mainCorridorNumber, mainCorridor) -> {
            sb.append("Main corridor " +  mainCorridorNumber + " " +
                    "Light: " + mainCorridor.getLight().getEquipmentState() + " " +
                    "AC: "+ mainCorridor.getAc().getEquipmentState() + "\n");

        });
        subCorridors.forEach((subCorridorNumber, subCorridor) -> {
            sb.append("Sub corridor " +  subCorridorNumber + " " +
                    "Light: " + subCorridor.getLight().getEquipmentState() + " " +
                    "AC: " + subCorridor.getAc().getEquipmentState() + "\n");

        });
        return sb.toString();
    }
}

