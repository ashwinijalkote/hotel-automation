package com.hotel.automation.processor.command;

import com.hotel.automation.entity.AC;
import com.hotel.automation.entity.Light;
import com.hotel.automation.entity.base.Equipment;
import com.hotel.automation.entity.constants.EquipmentState;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class SetEquipmentStateTest extends TestCase {
    Equipment equipment;

    @Test
    public void testExecuteCommandForLight() {
        equipment = new Light(EquipmentState.ON);
        Command command = new SetEquipmentState(equipment, EquipmentState.OFF);
        command.execute();
        Assert.assertEquals("light not set to off", equipment.getEquipmentState(), EquipmentState.OFF);
    }

    @Test
    public void testExecuteCommandForAC() {
        equipment = new AC(EquipmentState.ON);
        Command command = new SetEquipmentState(equipment, EquipmentState.OFF);
        command.execute();
        Assert.assertEquals("ac not set to off", equipment.getEquipmentState(), EquipmentState.OFF);
    }

}