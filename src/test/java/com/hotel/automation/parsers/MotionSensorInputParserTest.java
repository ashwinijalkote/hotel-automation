package com.hotel.automation.parsers;


import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.MotionSensorInput;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class MotionSensorInputParserTest {

    private StringLineParser stringLineParser;
    private MotionSensorInputParser motionSensorInputParse;

    @Test
    public void testParseInputForMovementInput() throws InvalidInputException, FileNotFoundException {
        File file = new File("src/test/resources/testFileForMovementDetected.txt");
        stringLineParser = new StringLineParser(file);
        motionSensorInputParse = new MotionSensorInputParser(stringLineParser);

        MotionSensorInput motionSensorInput = (MotionSensorInput) motionSensorInputParse.parseInput();
        Assert.assertEquals("Floor number parsed incorrectly",1, motionSensorInput.getFloorNumber());
        Assert.assertEquals("sub corridor number parsed incorrectly",2, motionSensorInput.getSubCorridorNumber());
        Assert.assertTrue("movement parsed incorrectly", motionSensorInput.isMovementDetected());
    }

    @Test
    public void testParseInputForNoMovementInput() throws InvalidInputException, FileNotFoundException {
        File file = new File("src/test/resources/testFileForNoMovement.txt");
        stringLineParser = new StringLineParser(file);
        motionSensorInputParse = new MotionSensorInputParser(stringLineParser);

        MotionSensorInput motionSensorInput = (MotionSensorInput) motionSensorInputParse.parseInput();
        Assert.assertEquals("Floor number parsed incorrectly",1, motionSensorInput.getFloorNumber());
        Assert.assertEquals("sub corridor number parsed incorrectly",2, motionSensorInput.getSubCorridorNumber());
        Assert.assertFalse("movement parsed incorrectly", motionSensorInput.isMovementDetected());
    }
}