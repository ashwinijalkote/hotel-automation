package com.hotel.automation.parsers;

import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.Input;
import com.hotel.automation.inputs.MotionSensorInput;
import com.hotel.automation.inputs.StringInput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotionSensorInputParser implements InputParser {
    private static final String PATTERN_STRING_FOR_MOVEMENT =
            ".*Movement[\\s]+in[\\s]+Floor[\\s]+([\\d]+)[\\s]*,[\\s]+Subcorridor[\\s]+([\\d]+).*";
    private static final String PATTERN_STRING_FOR_NO_MOVEMENT =
            ".*No[\\s]+movement[\\s]+in[\\s]+Floor[\\s]+([\\d]+)[\\s]*,[\\s]*Sub[\\s]+corridor[\\s]+([\\d]+)[\\s]+for[\\s]+a[\\s]+minute";

    InputParser inputParser;
    private int floorNumber;
    private int subCorridorNumber;
    private boolean isMovementDetected;

    public MotionSensorInputParser(StringLineParser stringLineParser) {
        inputParser = stringLineParser;
    }

    @Override
    public Input parseInput() throws InvalidInputException {
        StringInput input = parseStringInput();
        if (input == null ) return null;
        return getExtractedInputFromPattern(input.getStringInput());
    }

    public StringInput parseStringInput() throws InvalidInputException {
        Input input = inputParser.parseInput();
        return (input == null ? null : (StringInput) input);
    }

    private MotionSensorInput getExtractedInputFromPattern(String input) throws InvalidInputException {
        Pattern patternForMovement = Pattern.compile(PATTERN_STRING_FOR_MOVEMENT);
        Pattern patternForNoMovement = Pattern.compile(PATTERN_STRING_FOR_NO_MOVEMENT);
        Matcher matcherForMovement = patternForMovement.matcher(input);
        Matcher matcherForNoMovement = patternForNoMovement.matcher(input);
        if (matcherForMovement.matches()) {
            this.floorNumber = Integer.parseInt(matcherForMovement.group(1));
            this.subCorridorNumber = Integer.parseInt(matcherForMovement.group(2));
            this.isMovementDetected = true;
            return new MotionSensorInput(floorNumber, subCorridorNumber, isMovementDetected);
        } else if (matcherForNoMovement.matches()){
                this.floorNumber = Integer.parseInt(matcherForNoMovement.group(1));
                this.subCorridorNumber = Integer.parseInt(matcherForNoMovement.group(2));
                this.isMovementDetected = false;
            return new MotionSensorInput(floorNumber, subCorridorNumber, isMovementDetected);
        } else{
            throw new InvalidInputException("invalid input in file at " + input);
        }
    }
}
