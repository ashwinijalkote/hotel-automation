package com.hotel.automation.producer;

import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.Input;
import com.hotel.automation.inputs.NullInput;
import com.hotel.automation.parsers.InputParser;
import com.hotel.automation.parsers.MotionSensorInputParser;
import com.hotel.automation.parsers.StringLineParser;
import org.apache.log4j.Logger;

import java.util.Queue;

public class MotionSensorInputProducer implements Runnable{
    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);
    private InputParser inputParser;
    private Queue<Input> queue;

    public MotionSensorInputProducer(InputParser stringLineParser, Queue<Input> queue) {
        this.inputParser = stringLineParser;
        this.queue = queue;
    }

    @Override
    public void run() {
        MotionSensorInputParser motionSensorInputParser = new MotionSensorInputParser((StringLineParser) inputParser);
        Input motionSensorInput = null;
        while(true) {

            try {
                // not req as we have only one producer
                //synchronized(this) {
                    motionSensorInput = motionSensorInputParser.parseInput();
                    if (motionSensorInput == null) {
                        queue.add(new NullInput());
                        return;
                    } else {
                        logger.info("Added to queue" + motionSensorInput.toString());
                        queue.add(motionSensorInput);
                    }


               } catch(InvalidInputException e) {
                logger.error(e);
            }
        }
    }
}
