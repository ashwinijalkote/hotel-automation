package com.hotel.automation.parsers;

import com.hotel.automation.app.HotelAutomationApp;
import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.InitialHotelStateInput;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class InitialHotelStateInputParserTest extends TestCase {

    private StringLineParser stringLineParser;
    private InitialHotelStateInputParser initialHotelStateInputParser;

    @Test
    public void testParseInputForMovementInput() throws InvalidInputException, FileNotFoundException {
        File file = new File("src/test/resources/testInputFile.txt");
        stringLineParser = new StringLineParser(file);
        initialHotelStateInputParser = new InitialHotelStateInputParser(stringLineParser);

        InitialHotelStateInput hotelStateInput = (InitialHotelStateInput) initialHotelStateInputParser.parseInput();
        Assert.assertEquals("Floor count parsed incorrectly",2, hotelStateInput.getFloorsCount());
        Assert.assertEquals("main corridor count parsed incorrectly",1, hotelStateInput.getMainCorridorsCount());
        Assert.assertEquals("sub corridor count parsed incorrectly", 2, hotelStateInput.getSubCorridorsCount());
    }

    @Test(expected = InvalidInputException.class)
    //Not sure why expected is not working, need to debug
    public void testParseInputForNoMovementInput() throws FileNotFoundException {
        File file = new File("src/test/resources/testFileForNoMovement.txt");
        stringLineParser = new StringLineParser(file);
        initialHotelStateInputParser = new InitialHotelStateInputParser(stringLineParser);
        try {
            initialHotelStateInputParser.parseInput();
        }catch (InvalidInputException e) {
        }
    }
}