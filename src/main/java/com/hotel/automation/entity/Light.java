package com.hotel.automation.entity;

import com.hotel.automation.entity.base.Equipment;
import com.hotel.automation.entity.constants.EquipmentState;

public class Light implements Equipment {
    EquipmentState equipmentState;
    private final int POWER_CONSUMPTION = 5;

    public Light(EquipmentState equipmentState) {
        this.equipmentState = equipmentState;
    }

    public EquipmentState getEquipmentState() {
        return equipmentState;
    }

    @Override
    public void switchOn() {
        equipmentState = EquipmentState.ON;
    }

    @Override
    public void switchOff() {
        equipmentState = EquipmentState.OFF;
    }

    public int getPowerConsumption() {
        return getEquipmentState().equals(EquipmentState.OFF) ? 0 : POWER_CONSUMPTION;
    }
}
