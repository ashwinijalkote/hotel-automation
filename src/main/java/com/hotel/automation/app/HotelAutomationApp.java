package com.hotel.automation.app;

import com.hotel.automation.controller.HotelAutomationController;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;

public class HotelAutomationApp {
    static final Logger logger = Logger.getLogger(HotelAutomationApp.class);

    public static void main(String args[]) throws FileNotFoundException {
        BasicConfigurator.configure();
        HotelAutomationController hotelAutomationController = new HotelAutomationController();
        hotelAutomationController.runAutomation();
    }
}
