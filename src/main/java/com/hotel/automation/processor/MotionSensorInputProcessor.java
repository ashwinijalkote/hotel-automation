package com.hotel.automation.processor;

import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.entity.Floor;
import com.hotel.automation.entity.Hotel;
import com.hotel.automation.entity.SubCorridor;
import com.hotel.automation.entity.base.Equipment;
import com.hotel.automation.entity.constants.EquipmentState;
import com.hotel.automation.inputs.MotionSensorInput;
import com.hotel.automation.processor.command.Command;
import com.hotel.automation.processor.command.SetEquipmentState;
import com.hotel.automation.processor.criteria.Criteria;
import com.hotel.automation.processor.criteria.PowerConsumptionCriteria;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Optional;

public class MotionSensorInputProcessor {
    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);

    private Hotel hotel;
    private Command command;
    private Criteria criteria;
    private LinkedHashMap<SubCorridor, SubCorridor> prevSubCorMap = new LinkedHashMap<>();

    public MotionSensorInputProcessor(Hotel hotel) {
        this.hotel = hotel;
        criteria = new PowerConsumptionCriteria();
    }

    private SubCorridor findOtherSubCorridorForFloor(int floorNumber, int subCorridorNumber) {
        Optional<Integer> optionalOtherSubCorridorNumber = hotel.getFloor(floorNumber)
                .getSubCorridors().keySet().stream()
                .filter(num -> num != subCorridorNumber).findFirst();
        if (optionalOtherSubCorridorNumber.isPresent()) {
            return hotel.getFloor(floorNumber).getSubCorridor(optionalOtherSubCorridorNumber.get());
        }
        return null;
    }

    private void setEquipmentAtSubcorridor(Equipment equipment, EquipmentState equipmentState) {
        command = new SetEquipmentState(equipment, equipmentState);
        command.execute();
    }

    public void process(MotionSensorInput motionSensorInput) {

        logger.info(Thread.currentThread().getName() + " Started Processing for : "
                + motionSensorInput.getFloorNumber()
                + " " + motionSensorInput.getSubCorridorNumber()
                + " " + motionSensorInput.isMovementDetected());

        Floor floor = hotel.getFloors().get(motionSensorInput.getFloorNumber());
        SubCorridor subCorridor =  floor.getSubCorridors().get(motionSensorInput.getSubCorridorNumber());
        boolean isMovement = motionSensorInput.isMovementDetected();

        synchronized (this) {
            if (isMovement) {
                setEquipmentAtSubcorridor(subCorridor.getLight(), EquipmentState.ON);
                if (!criteria.criteriaMet(floor)) {
                    logger.info("Power consumption criteria not met: " + floor.getPowerConsumption());
                    SubCorridor otherSubCorridor = findOtherSubCorridorForFloor(1, motionSensorInput.getSubCorridorNumber());
                    if (otherSubCorridor == null) {
                        logger.info("Other sub corridor not found");
                        return;
                    }
                    prevSubCorMap.put(subCorridor, otherSubCorridor);
                    setEquipmentAtSubcorridor(otherSubCorridor.getAc(), EquipmentState.OFF);
                    logger.info("other subcorridor found : switching AC to " + otherSubCorridor.getAc().getEquipmentState());
                    logger.info("Power consumption " + floor.getPowerConsumption());
                    command.execute();
                }
            } else {
                SubCorridor prevSubCorridor = prevSubCorMap.get(subCorridor);
                setEquipmentAtSubcorridor(subCorridor.getLight(), EquipmentState.OFF);
                if (prevSubCorridor != null) {
                    setEquipmentAtSubcorridor(prevSubCorridor.getAc(), EquipmentState.ON);
                    if (!criteria.criteriaMet(floor)) {
                        setEquipmentAtSubcorridor(prevSubCorridor.getAc(), EquipmentState.OFF);
                    }
                    logger.info("Prev sub corridor found: AC state: " + prevSubCorridor.getAc().getEquipmentState());
                    logger.info("Power consumption: " + floor.getPowerConsumption());
                }
            }
            logger.info(Thread.currentThread().getName() + " Done processing for : " +
                    +motionSensorInput.getFloorNumber()
                    + " " + motionSensorInput.getSubCorridorNumber()
                    + " " + motionSensorInput.isMovementDetected() + "\n Printing hotel State:\n");
            logger.info(hotel.toString());
        }
    }
}
