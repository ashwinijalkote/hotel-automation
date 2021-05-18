package com.hotel.automation.processor.criteria;

import com.hotel.automation.entity.Floor;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class PowerConsumptionCriteriaTest extends TestCase {

    @Test
    public void testPowerConsumptionForDefaultFloorState() {
        Floor floor = new Floor.Builder()
                .addMainCorridors(1)
                .addSubCorridors(2)
                .build();
        Assert.assertEquals("floor consumption criteria not met", (15*1 + 10*2), floor.getPowerConsumption());
    }

    @Test
    public void testPowerConsumptionForFloor() {
        Floor floor = new Floor.Builder()
                .addMainCorridors(1)
                .addSubCorridors(2)
                .build();
        floor.getSubCorridors().get(1).getLight().switchOn();
        Assert.assertEquals("floor consumption criteria not met", (15*1 + 10*2 + 5), floor.getPowerConsumption());
    }

}