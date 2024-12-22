package com.automation.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {

    public static WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        return new ChromeDriver();
    }
}
