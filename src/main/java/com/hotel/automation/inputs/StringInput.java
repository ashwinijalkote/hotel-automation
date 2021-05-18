package com.hotel.automation.inputs;

public class StringInput implements Input {
    String stringInput;

    public StringInput(String stringInput) {
        this.stringInput = stringInput;
    }

    public String getStringInput() {
        return stringInput;
    }
}
