package com.nc.stepdefinitions;

import com.nc.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("🔄 Memulai scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            System.err.println("❌ Scenario GAGAL: " + scenario.getName());
        } else {
            System.out.println("✅ Scenario BERHASIL: " + scenario.getName());
        }

        // ✅ Always close browser after scenario
        DriverFactory.quitDriver();
        System.out.println("🧹 Browser ditutup setelah scenario: " + scenario.getName());
    }
}
