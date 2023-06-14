package com.hotel.automation.controller;

import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.entity.Hotel;
import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.InitialHotelStateInput;
import com.hotel.automation.inputs.Input;
import com.hotel.automation.inputs.MotionSensorInput;
import com.hotel.automation.inputs.NullInput;
import com.hotel.automation.parsers.InitialHotelStateInputParser;
import com.hotel.automation.parsers.StringLineParser;
import com.hotel.automation.processor.MotionSensorInputProcessor;
import com.hotel.automation.producer.MotionSensorInputProducer;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class HotelAutomationController {

    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);

    public void runAutomation() throws FileNotFoundException {

        File file = new File(HotelAutomationApp.class.getClassLoader().getResource("inputFile.txt").getFile());
        StringLineParser stringLineParser = new StringLineParser(file);

        Hotel hotel = getHotelInitialState(stringLineParser);
        LinkedBlockingQueue<Input> motionSensorInputQueue = new LinkedBlockingQueue<>();

        getMotionSensorInputs(stringLineParser, motionSensorInputQueue);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        logger.info("Starting consumer thread");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                processMotionSensorInputs(hotel, motionSensorInputQueue);
            }
        });
    }

    private Hotel getHotelInitialState(StringLineParser stringLineParser) {
        InitialHotelStateInputParser initialHotelStateInputParser = new InitialHotelStateInputParser(stringLineParser);
        InitialHotelStateInput initialHotelStateInput = null;
        try {
            initialHotelStateInput = (InitialHotelStateInput) initialHotelStateInputParser.parseInput();
        } catch (
                InvalidInputException e) {
            logger.error(e);
            System.exit(0);
        }

        Hotel hotel = new Hotel(initialHotelStateInput.getFloorsCount(),
                initialHotelStateInput.getMainCorridorsCount(),
                initialHotelStateInput.getSubCorridorsCount());
        logger.info("Printing initial state: \n" + hotel.toString());
        return hotel;
    }

    public void getMotionSensorInputs(StringLineParser stringLineParser, LinkedBlockingQueue<Input> motionSensorInputQueue) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        logger.info("Starting producer thread");
        executorService.execute(new MotionSensorInputProducer(stringLineParser, motionSensorInputQueue));
    }

    public void processMotionSensorInputs(Hotel hotel, LinkedBlockingQueue<Input> motionSensorInputQueue) {

        MotionSensorInputProcessor motionSensorInputProcessor = new MotionSensorInputProcessor(hotel);
        ExecutorService executor = Executors.newFixedThreadPool(hotel.getFloors().size());
        while (true) {
            if (!motionSensorInputQueue.isEmpty()) {
                Input input = null;
                try {
                    input = motionSensorInputQueue.take();
                } catch (InterruptedException e) {

                }
                if (input instanceof NullInput) {
                    executor.shutdown();
                    return; //exit condition
                }
                Input finalInput = input;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        motionSensorInputProcessor.process((MotionSensorInput) finalInput);
                    }
                });
            }
        }
    }

}
