package com.automation.framework;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomationFramework {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Read input file
            List<String> registrations = readVehicleRegistrations("car_input.txt");

            // Step 2: Process each registration
            Map<String, String> extractedData = new HashMap<>();
            for (String reg : registrations) {

                driver = WebDriverFactory.createDriver();
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                CarValuationPage carValuationPage = new CarValuationPage(driver);

                driver.get("https://carwow.co.uk/");

                // Step 3: Interact with page
                carValuationPage.acceptCookies();
                carValuationPage.navigateToSellCarPage();
                carValuationPage.enterRegistration(reg);
                carValuationPage.submitCarValuation();
                carValuationPage.continueToNextStep();

                // Get mileage input and update value
                WebElement mileageInput = driver.findElement(By.xpath("//input[@class='car-valuation__input input-full-width valid' and @data-distance-input-target='userInput']"));
                String mileageValue = mileageInput.getAttribute("value");

                // Generate and input new mileage
                int updatedMileage = MileageGenerator.generateMileage(Integer.parseInt(mileageValue.replace(",", "")));
                carValuationPage.enterMileage(String.valueOf(updatedMileage));

                carValuationPage.submitMileage();
                carValuationPage.enterUserDetails("ram.bhargavi@yahoo.in", "test", "07979797979", "SW25AP");
                carValuationPage.showValuation();
                carValuationPage.login("Welcome1@");

                String valuation = carValuationPage.getValuationPrice();
                extractedData.put(reg, valuation);

                driver.quit();
            }

            // Step 4: Compare results with expected output
            compareWithExpectedResults("car_output.txt", extractedData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static List<String> readVehicleRegistrations(String filePath) throws IOException {
        List<String> registrations = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        Pattern pattern = Pattern.compile("[A-Z]{2}\\s?[0-9]{2}\\s?[A-Z]{3}");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                registrations.add(matcher.group());
            }
        }
        return registrations;
    }

    private static void compareWithExpectedResults(String expectedFile, Map<String, String> actualData) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(expectedFile));
        Map<String, String> expectedData = new HashMap<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length > 1) {
                expectedData.put(parts[0].trim(), parts[3].trim());
            }
        }

        for (Map.Entry<String, String> entry : actualData.entrySet()) {
            String reg = entry.getKey();
            String actualValue = entry.getValue();
            String expectedValue = expectedData.get(reg);

            int actualIntValue = Integer.parseInt(actualValue.replaceAll("[^0-9]", ""));
            int expectedIntValue = Integer.parseInt(expectedValue.replaceAll("[^0-9]", ""));
            int tolerance = 500;

            // Check if the difference between expected and actual values is within the tolerance
            if (Math.abs(actualIntValue - expectedIntValue) <= tolerance) {
                System.out.printf("Match for %s: Expected=%d, Actual=%d (within tolerance)\n", reg, expectedIntValue, actualIntValue);
            } else {
                System.out.printf("Mismatch for %s: Expected=%d, Actual=%d\n", reg, expectedIntValue, actualIntValue);
            }
        }
    }
}
