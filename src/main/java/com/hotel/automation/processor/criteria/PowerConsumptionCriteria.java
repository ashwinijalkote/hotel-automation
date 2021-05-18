package com.hotel.automation.processor.criteria;

import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.entity.Floor;
import org.apache.log4j.Logger;

public class PowerConsumptionCriteria implements Criteria {
    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);

    private int MAX_POWER_CONSUMPTION_PER_MAIN_CORRIDOR = 15;
    private int MAX_POWER_CONSUMPTION_PER_SUB_CORRIDOR = 10;

    private int getMaxPowerConsumption(Floor floor) {
        return floor.getMainCorridors().size() * MAX_POWER_CONSUMPTION_PER_MAIN_CORRIDOR +
        floor.getSubCorridors().size() * MAX_POWER_CONSUMPTION_PER_SUB_CORRIDOR;
    }

    @Override
    public boolean criteriaMet(Floor floor) {
        logger.info("inside Power consumption :" + floor.getPowerConsumption() + " " + getMaxPowerConsumption(floor));
        return floor.getPowerConsumption() <= getMaxPowerConsumption(floor);
    }
}
