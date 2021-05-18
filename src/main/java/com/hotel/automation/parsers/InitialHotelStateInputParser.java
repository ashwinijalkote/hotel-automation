package com.hotel.automation.parsers;

import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.InitialHotelStateInput;
import com.hotel.automation.inputs.Input;
import com.hotel.automation.inputs.StringInput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitialHotelStateInputParser implements InputParser{
    InputParser inputParser;

    private static final String  PATTERN_STRING_FOR_FLOORS_COUNT = ".*Number[\\s]+of[\\s]+floors:[\\s]+([\\d]+).*";
    private static final String  PATTERN_STRING_FOR_MAIN_CORRIDOR_COUNT = ".*Main[\\s]+corridors[\\s]+per[\\s]+floor:[\\s]+([\\d]+).*";
    private static final String  PATTERN_STRING_FOR_SUB_CORRIDOR_COUNT = ".*Sub[\\s]+corridors[\\s]+per[\\s]+floor:[\\s]+([\\d]+).*";

    public InitialHotelStateInputParser(StringLineParser stringLineParser) {
        inputParser = stringLineParser;
    }

    @Override
    public Input parseInput() throws InvalidInputException {
        int floorCount = parseFloorCount();
        int mainCorridorCount = parseMainCorridorCount();
        int subCorridorCount = parseSubCorridorCount();
        return new InitialHotelStateInput(floorCount, mainCorridorCount, subCorridorCount);
    }

    public StringInput parseStringInput() throws InvalidInputException {
        return (StringInput) inputParser.parseInput();
    }


    private int parseFloorCount() throws InvalidInputException {
        return getExtractedInputFromPattern(PATTERN_STRING_FOR_FLOORS_COUNT);
    }

    private int parseMainCorridorCount() throws InvalidInputException {
        return getExtractedInputFromPattern(PATTERN_STRING_FOR_MAIN_CORRIDOR_COUNT);
    }

    private int parseSubCorridorCount() throws InvalidInputException {
        return getExtractedInputFromPattern(PATTERN_STRING_FOR_SUB_CORRIDOR_COUNT);
    }

    private int getExtractedInputFromPattern(String pattrenString) throws InvalidInputException {
        Pattern pattern = Pattern.compile(pattrenString);
        String input = parseStringInput().getStringInput();
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String floorCountString = matcher.group(1);
            return Integer.parseInt(floorCountString);
        }
        throw new InvalidInputException("invalid input in file at " + input);
    }
}
