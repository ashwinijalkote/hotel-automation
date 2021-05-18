package com.hotel.automation.entity.base;

import com.hotel.automation.entity.constants.EquipmentState;

public interface Equipment {
    void switchOn();
    void switchOff();
    EquipmentState getEquipmentState();
    int getPowerConsumption();
}
