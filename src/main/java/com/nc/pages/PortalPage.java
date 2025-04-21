// PortalPage.java - Placeholder for src/main/java/pages
package com.nc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class PortalPage {
    private WebDriver driver;

    public PortalPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToPortal(String schemaCode, String schemaName) {
        String xpath = String.format("//div[contains(@id, 'btnschema') and .//div[text()='%s' or contains(text(), '%s')]][@style]", schemaCode, schemaName);
        WebElement portalElement = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        portalElement.click();
    }
}
