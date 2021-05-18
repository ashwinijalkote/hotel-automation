package com.hotel.automation.processor;

import com.hotel.automation.entity.Hotel;
import com.hotel.automation.entity.constants.EquipmentState;
import com.hotel.automation.inputs.MotionSensorInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MotionSensorInputProcessorTest {

    Hotel hotel;
    MotionSensorInputProcessor motionSensorInputProcessor;

    @Before
    public void setup() {
        hotel = new Hotel(2, 1, 2);
        motionSensorInputProcessor = new MotionSensorInputProcessor(hotel);
        Assert.assertNotNull(hotel);

    }

    @Test
    public void testProcessSensorInput() {
        //given
        MotionSensorInput motionSensorInput = new MotionSensorInput(1, 2, true);
        //when
        motionSensorInputProcessor.process(motionSensorInput);
        //then

        Assert.assertEquals(hotel.getFloor(1)
                .getSubCorridor(2)
                .getLight().getEquipmentState(), EquipmentState.ON);
        Assert.assertEquals("Light at subcorridor 2 not ON",
                EquipmentState.ON,
                hotel.getFloor(1).getSubCorridor(2).getAc().getEquipmentState());
        Assert.assertEquals("AC at subcorridor 1 not OFF",
                EquipmentState.OFF,
                hotel.getFloor(1).getSubCorridor(1).getAc().getEquipmentState());
        Assert.assertEquals("Power consumption doesnt match",
                30,
                hotel.getFloor(1).getPowerConsumption());
        Assert.assertEquals("Total Power consumption doesnt match",
                65,
                hotel.getPowerConsumption());

        //given
        motionSensorInput = new MotionSensorInput(1, 2, false);
        //when
        motionSensorInputProcessor.process(motionSensorInput);
        //then
        Assert.assertEquals(hotel.getFloors().get(1).getSubCorridors().get(2).getLight().getEquipmentState(), EquipmentState.OFF);
        Assert.assertEquals(hotel.getFloors().get(1).getSubCorridors().get(1).getAc().getEquipmentState(), EquipmentState.ON);
        Assert.assertEquals("Power consumption doesnt match",35, hotel.getFloor(1).getPowerConsumption());
        Assert.assertEquals("Total Power consumption doesnt match",70, hotel.getPowerConsumption());
    }

}