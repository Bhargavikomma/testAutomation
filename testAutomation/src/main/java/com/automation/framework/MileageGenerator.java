package com.automation.framework;

import java.util.Random;

public class MileageGenerator {

    public static int generateMileage(int currentMileage) {
        return new Random().nextInt(1000) + currentMileage;
    }
}
