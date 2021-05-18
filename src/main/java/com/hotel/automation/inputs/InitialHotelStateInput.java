package com.hotel.automation.inputs;

public class InitialHotelStateInput implements Input {
    private int floorsCount;
    private int mainCorridorsCount;
    private int subCorridorsCount;

    public InitialHotelStateInput(int numFloors, int numMainCorridors, int numSubCorridors) {
        this.floorsCount = numFloors;
        this.mainCorridorsCount = numMainCorridors;
        this.subCorridorsCount = numSubCorridors;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public int getMainCorridorsCount() {
        return mainCorridorsCount;
    }

    public int getSubCorridorsCount() {
        return subCorridorsCount;
    }
}
