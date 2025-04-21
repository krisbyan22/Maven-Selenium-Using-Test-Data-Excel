package com.nc.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import com.nc.enums.BrowserType;




public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initializeDriver(String browserName) {
        if (browserName == null || browserName.trim().isEmpty()) {
            throw new RuntimeException("❌ Browser belum diset! Cek config.properties atau parameter sistem.");
        }
    
        BrowserType browser;
        try {
            browser = BrowserType.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Browser tidak dikenali: " + browserName);
        }
    
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                driver.set(new ChromeDriver(options));
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;
            default:
                throw new RuntimeException("❌ Browser belum didukung: " + browserName);
        }
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            System.out.println("🧹 Closing browser...");
            driver.get().quit();
            driver.remove();
        } else {
            System.out.println("🔇 Browser was already null or closed.");
        }
    }
    
}
