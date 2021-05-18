package com.hotel.automation.entity;

import com.hotel.automation.entity.base.Equipment;
import com.hotel.automation.entity.constants.EquipmentState;

public class SubCorridor {
    Equipment ac;
    Equipment light;

    SubCorridor() {
        this.ac = new AC(EquipmentState.ON);
        this.light = new Light(EquipmentState.OFF);
    }

    public Equipment getAc() {
        return ac;
    }

    public Equipment getLight() {
        return light;
    }

    public int getPowerConsumption( ) {
        return this.ac.getPowerConsumption() + this.light.getPowerConsumption();
    }

}
