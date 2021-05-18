package com.hotel.automation.inputs;

public class MotionSensorInput implements Input {
    private boolean isMovementDetected;
    private int floorNumber;
    private int subCorridorNumber;

    public MotionSensorInput(int floorNumber, int subCorridorNumber, boolean isMovementDetected) {
        this.floorNumber = floorNumber;
        this.subCorridorNumber = subCorridorNumber;
        this.isMovementDetected = isMovementDetected;
    }

    public boolean isMovementDetected() {
        return isMovementDetected;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSubCorridorNumber() {
        return subCorridorNumber;
    }

    @Override
    public String toString() {
        return "MotionSensorInput{" +
                "isMovementDetected=" + isMovementDetected +
                ", floorNumber=" + floorNumber +
                ", subCorridorNumber=" + subCorridorNumber +
                '}';
    }
}
