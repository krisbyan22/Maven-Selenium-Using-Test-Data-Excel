package com.nc.listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.nc.utils.DriverFactory;


public class TestListener implements TestLifecycleListener {

    @Override
    public void beforeTestStart(TestResult result) {
        System.out.println("Starting test: " + result.getName());
    }

    @Override
    public void afterTestStop(TestResult result) {
        if (result.getStatus().name().equals("FAILED") || result.getStatus().name().equals("BROKEN")) {
            takeScreenshot("Test Failed Screenshot");
        }
        System.out.println("Finished test: " + result.getName() + " - Status: " + result.getStatus());
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot(String name) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }
}
