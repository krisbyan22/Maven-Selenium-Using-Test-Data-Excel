package com.nc.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String fileName) {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path destPath = Paths.get("target/screenshots", fileName + "_" + timestamp + ".png");
            try {
                Files.createDirectories(destPath.getParent());
                Files.copy(sourceFile.toPath(), destPath);
                System.out.println("Screenshot saved to: " + destPath.toString());
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        } else {
            System.err.println("Driver does not support screenshots");
        }
    }

    public static byte[] getScreenshotAsBytes(WebDriver driver) {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }
}
