package com.nc.stepdefinitions;
import com.nc.pages.PortalPage;
import com.nc.utils.ConfigReader;
import com.nc.utils.DriverFactory;
import com.nc.utils.ExcelReader;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import java.util.Map;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;



public class PortalSteps {
    private PortalPage portalPage;
    private Map<String, String> testData;

    @Before
    public void setup() {
        WebDriver driver = DriverFactory.getDriver();
        portalPage = new PortalPage(driver);
    }

    @Given("User navigates to portal from testDataID {string}")
    public void userNavigatesToPortal(String testDataID) {
        // Ambil path file Excel dari config.properties
        String filePath = ConfigReader.getProperty("testDataPath");
        ExcelReader excelReader = new ExcelReader(filePath);
    
        // Ambil test data berdasarkan sheet dan ID
        testData = excelReader.getTestData("Login", testDataID);
    
        if (testData == null) {
            System.err.println("❌ Test data untuk ID '" + testDataID + "' tidak ditemukan!");
            throw new RuntimeException("Test data not found for ID: " + testDataID);
        }
    
        String schemaCode = testData.get("schemaCode");
        String schemaName = testData.get("schemaName");
        if (schemaCode == null || schemaCode.trim().isEmpty()) {
            throw new RuntimeException("schemaCode kosong atau tidak ditemukan di test data ID: " + testDataID);
        }

        if (schemaName == null || schemaName.trim().isEmpty()) {
            throw new RuntimeException("schemaName kosong atau tidak ditemukan di test data ID: " + testDataID);
        }
    
        // Navigasi ke portal
        portalPage.navigateToPortal(schemaCode, schemaName);
    }
    
    

    
}
