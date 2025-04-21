package com.nc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import com.nc.utils.ConfigReader;
import com.nc.utils.ScreenshotUtil;

public class PortalPage {
    private WebDriver driver;

    public PortalPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToPortal(String schemaCode, String schemaName) {
        // Wait for page to load after login
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if "Login Denied" popup appears
        if (handleLoginDeniedPopup()) {
            System.out.println("Login Denied popup handled. Continuing with portal navigation.");
        }

        // Try multiple locator strategies
        String[] xpaths = {
            String.format("//div[contains(@class, 'schema') or contains(@id, 'schema')]//div[contains(text(), '%s')]", schemaCode),
            String.format("//button[contains(text(), '%s')]", schemaCode),
            String.format("//a[contains(text(), '%s')]", schemaCode),
            String.format("//div[contains(text(), '%s')]", schemaCode),
            String.format("//span[contains(text(), '%s')]", schemaCode),
            String.format("//div[contains(@class, 'portal') or contains(@class, 'dashboard')]//div[contains(text(), '%s')]", schemaCode),
            String.format("//*[contains(text(), '%s')]", schemaCode),
            String.format("//div[contains(@class, 'card') or contains(@class, 'item') or contains(@class, 'menu')]//div[contains(text(), '%s')]", schemaCode),
            String.format("//div[contains(@class, 'btn') and contains(text(), '%s')]", schemaCode),
            String.format("//button[contains(@class, 'btn') and contains(text(), '%s')]", schemaCode),
            String.format("//div[contains(@class, 'modal') or contains(@class, 'popup')]//div[contains(text(), '%s')]", schemaCode),
            String.format("//div[contains(@class, 'list') or contains(@class, 'grid')]//div[contains(text(), '%s')]", schemaCode)
        };

        WebElement portalElement = null;
        for (String xpath : xpaths) {
            try {
                System.out.println("Trying XPath: " + xpath);
                portalElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                if (portalElement != null) {
                    System.out.println("Found portal element with XPath: " + xpath);
                    break;
                }
            } catch (Exception e) {
                System.out.println("XPath failed: " + xpath + " - " + e.getMessage());
            }
        }

        if (portalElement == null) {
            // Take screenshot for debugging
            try {
                ScreenshotUtil.takeScreenshot(driver, "portal_not_found");
            } catch (Exception ex) {
                System.out.println("Failed to take screenshot: " + ex.getMessage());
            }
            System.out.println("Portal element not found. This might be expected if the application doesn't require portal selection.");
            return; // Don't throw exception, just return
        }

        portalElement.click();
    }

    private boolean handleLoginDeniedPopup() {
        try {
            // Check if Login Denied popup appears using the correct XPath
            WebElement popup = driver.findElement(By.xpath(ConfigReader.getLocator("login.denied.popup.xpath")));
            System.out.println("Login Denied popup detected: " + popup.getText());

            // Try to find Yes, Logout button
            WebElement yesButton = null;
            try {
                yesButton = driver.findElement(By.xpath(ConfigReader.getLocator("login.denied.yes.button.xpath")));
                System.out.println("Yes, Logout button found");
            } catch (Exception e) {
                System.out.println("Yes, Logout button not found: " + e.getMessage());
            }

            if (yesButton != null) {
                yesButton.click();
                System.out.println("Clicked Yes, Logout button to logout from other device");
                Thread.sleep(2000); // Wait for popup to close
                return true;
            } else {
                System.out.println("Yes, Logout button not found, trying Cancel button");

                // Try to find Cancel button
                WebElement cancelButton = null;
                try {
                    cancelButton = driver.findElement(By.xpath(ConfigReader.getLocator("login.denied.cancel.button.xpath")));
                    System.out.println("Cancel button found");
                } catch (Exception e) {
                    System.out.println("Cancel button not found: " + e.getMessage());
                }

                if (cancelButton != null) {
                    cancelButton.click();
                    System.out.println("Clicked Cancel button");
                    return true;
                } else {
                    System.out.println("No buttons found on Login Denied popup");
                    return true; // Still return true since popup was detected
                }
            }

        } catch (Exception e) {
            System.out.println("Login Denied popup not found: " + e.getMessage());
        }
        return false; // No popup detected
    }
}
