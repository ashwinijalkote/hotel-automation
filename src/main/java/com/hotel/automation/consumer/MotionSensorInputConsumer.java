package com.hotel.automation.consumer;

import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.inputs.MotionSensorInput;
import com.hotel.automation.processor.MotionSensorInputProcessor;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class MotionSensorInputConsumer implements Callable<String> {
    MotionSensorInputProcessor motionSensorInputProcessor;
    MotionSensorInput motionSensorInput;
    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);

    public MotionSensorInputConsumer(MotionSensorInput motionSensorInput, MotionSensorInputProcessor motionSensorInputProcessor) {
        this.motionSensorInputProcessor = motionSensorInputProcessor;
        this.motionSensorInput = motionSensorInput;
    }

    @Override
    public String call() {
        logger.info("Consumer thread got input: " + motionSensorInput.toString());
        logger.info(Thread.currentThread().getName() + " : Consuming " + motionSensorInput.toString());
        motionSensorInputProcessor.process(motionSensorInput);
        return Thread.currentThread().getName();
    }

}
