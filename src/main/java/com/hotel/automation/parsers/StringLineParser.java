package com.hotel.automation.parsers;

import com.hotel.automation.inputs.Input;
import com.hotel.automation.inputs.StringInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StringLineParser implements InputParser {
    Scanner scanner;
    public static final String EXIT_STRING = "exit";

    public StringLineParser(File source) throws FileNotFoundException {
        scanner = new Scanner(source);
    }

    @Override
    public Input parseInput() {
        String nextLine = scanner.nextLine();
        if (nextLine.equals(EXIT_STRING)) {
            scanner.close();
            return null;
        }
        return new StringInput(nextLine);
    }
}
