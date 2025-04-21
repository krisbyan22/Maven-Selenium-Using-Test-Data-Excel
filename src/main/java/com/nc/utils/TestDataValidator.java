package com.nc.utils;
import java.util.Map;
public class TestDataValidator {
    public static void validateTestData(String testDataID, Map<String, String> testData, String... requiredKeys) {
        if (testData == null) {
            throw new RuntimeException("❌ Test data tidak ditemukan untuk ID: " + testDataID);
        }

        for (String key : requiredKeys) {
            String value = testData.get(key);
            if (value == null || value.trim().isEmpty()) {
                throw new RuntimeException("❌ Nilai untuk '" + key + "' kosong atau tidak ditemukan di testData ID: " + testDataID);
            }
        }
    }
    
}
