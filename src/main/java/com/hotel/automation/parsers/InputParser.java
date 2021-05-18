package com.hotel.automation.parsers;

import com.hotel.automation.exception.InvalidInputException;
import com.hotel.automation.inputs.Input;

public interface InputParser {

    Input parseInput() throws InvalidInputException;
}
