package com.hotel.automation.processor.command;

import com.hotel.automation.entity.base.Equipment;
import com.hotel.automation.entity.constants.EquipmentState;

public class SetEquipmentState implements Command {
    Equipment equipment;
    EquipmentState equipmentState;

    public SetEquipmentState(Equipment equipment, EquipmentState equipmentState) {
        this.equipment = equipment;
        this.equipmentState = equipmentState;
    }

    @Override
    public void execute() {
        if (equipmentState == EquipmentState.ON) {
            equipment.switchOn();
        } else {
            equipment.switchOff();
        }
    }
}
