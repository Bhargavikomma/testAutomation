package com.automation.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CarValuationPage {

    private WebDriver driver;

    public CarValuationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptCookies() {

        // Switch to the iframe by ID
        driver.switchTo().frame("sp_message_iframe_1189152");

        // Interact with the button
        WebElement acceptButton = driver.findElement(By.xpath("//button[@title='Accept all' and @aria-label='Accept all']"));
        acceptButton.click();

        // Switch back to the default content
        driver.switchTo().defaultContent();
    }

    public void navigateToSellCarPage() {
        WebElement sellCarButton = driver.findElement(By.xpath("//a[contains(@class, 'btn-full-width') and contains(text(), 'Sell my car')]"));
        sellCarButton.click();
    }

    public void enterRegistration(String reg) {
        WebElement regInput = driver.findElement(By.id("registration_number"));
        regInput.sendKeys(reg);
    }

    public void submitCarValuation() {
        WebElement getValuationButton = driver.findElement(By.xpath("//input[@value='Sell my car']"));
        getValuationButton.click();
    }

    public void continueToNextStep() {
        WebElement continueButton = driver.findElement(By.xpath("//a[contains(text(),'Continue')]"));
        continueButton.click();
    }

    public void enterMileage(String mileage) {
        WebElement mileageInput = driver.findElement(By.xpath("//input[@class='car-valuation__input input-full-width valid' and @data-distance-input-target='userInput']"));
        mileageInput.clear();
        mileageInput.sendKeys(mileage);
    }

    public void submitMileage() {
        WebElement getMileageButton = driver.findElement(By.xpath("//button[@data-distance-input-target='submit']"));
        getMileageButton.click();
    }

    public void enterUserDetails(String email, String name, String phone, String postcode) {
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='user[email]']"));
        emailInput.sendKeys(email);

        WebElement nameInput = driver.findElement(By.xpath("//input[@name='user[name]']"));
        nameInput.sendKeys(name);

        WebElement mobileNumInput = driver.findElement(By.xpath("//input[@name='user[phone_number]']"));
        mobileNumInput.sendKeys(phone);

        WebElement postcodeInput = driver.findElement(By.xpath("//input[@name='user[postcode]']"));
        postcodeInput.sendKeys(postcode);
    }

    public void showValuation() {
        WebElement valuationButton = driver.findElement(By.xpath("//button[contains(text(),'Show me my valuation')]"));
        valuationButton.click();
    }

    public void login(String password) {
        WebElement passwordInput = driver.findElement(By.xpath("//*[@id='user_password']"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
    }

    public String getValuationPrice() {
        WebElement valuationPrice = driver.findElement(By.xpath("//h1[@class='estimated-price__card-price-range']"));
        String valuationText = valuationPrice.getText();

        // Clean the value by removing any non-numeric characters (e.g. "£" and commas)
        String getValue = valuationText.replaceAll("[^0-9]", "");

        return getValue;
    }
}
