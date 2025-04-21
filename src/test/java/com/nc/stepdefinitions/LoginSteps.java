package com.nc.stepdefinitions;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.nc.pages.LoginPage;
import com.nc.utils.ConfigReader;
import com.nc.utils.DriverFactory;
import com.nc.utils.ExcelReader;
import com.nc.utils.TestDataValidator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import org.junit.Assert;

public class LoginSteps {
    private LoginPage loginPage;
    private Map<String, String> testData;

    @Before
    public void setup() {
        String browser = ConfigReader.getProperty("browser");
        DriverFactory.initializeDriver(browser);

        WebDriver driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getBaseUrl());

        loginPage = new LoginPage(driver);
    }

    @Given("User login with testDataID {string}")
    public void userLoginWithTestDataID(String testDataID) {
        String excelPath = ConfigReader.getProperty("testDataPath");
        String filePath = ConfigReader.getProperty("testDataPath");
        System.out.println("📂 Path Excel: " + filePath);

        ExcelReader excelReader = new ExcelReader(excelPath);
        testData = excelReader.getTestData("Login", testDataID);
        System.out.println("📊 Data ditemukan: " + testData);

        TestDataValidator.validateTestData(testDataID, testData, "username", "password");
        loginPage.login(testData.get("username"), testData.get("password"));
    }

    @Then("User should see Nexchief title")
    public void userShouldSeeNexchiefTitle() {
        Assert.assertTrue("Login tidak berhasil", loginPage.isLoginSuccessful());
    }
}
