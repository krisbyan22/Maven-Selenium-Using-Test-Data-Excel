package com.nc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import com.nc.utils.ConfigReader;



public class LoginPage {
    private WebDriver driver;

    private By usernameInput = By.xpath(ConfigReader.getLocator("login.username.xpath"));
    private By passwordInput = By.xpath(ConfigReader.getLocator("login.password.xpath"));
    private By loginButton = By.xpath(ConfigReader.getLocator("login.submit.xpath"));
    private By nexchiefTitle = By.xpath(ConfigReader.getLocator("login.title.xpath"));

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        WebElement usernameElement = driver.findElement(usernameInput);
        WebElement passwordElement = driver.findElement(passwordInput);
        WebElement loginButtonElement = driver.findElement(loginButton);

        usernameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButtonElement.click();
    }

    public boolean isLoginSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Wait for the Nexchief title to appear
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(nexchiefTitle));
            if (titleElement.isDisplayed()) {
                System.out.println("Nexchief title found and displayed");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Nexchief title not found: " + e.getMessage());
        }

        // Check if we're not on the login page anymore (username field should not be visible)
        try {
            WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(usernameInput));
            if (!usernameElement.isDisplayed()) {
                System.out.println("Username field not visible - likely logged in successfully");
                return true;
            } else {
                System.out.println("Username field still visible - still on login page");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Username field not found - likely on different page (logged in)");
            return true;
        }
    }
}

