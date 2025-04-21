package com.nc.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    private final String excelPath;
    private final Map<String, Map<String, Map<String, String>>> sheetDataCache = new HashMap<>();

    public ExcelReader(String excelPath) {
        this.excelPath = excelPath;
    }

    public Map<String, String> getTestData(String sheetName, String testDataID) {
        if (!sheetDataCache.containsKey(sheetName)) {
            System.out.println("🔍 Sheet belum diload: " + sheetName);
            loadSheetData(sheetName);
        }

        Map<String, Map<String, String>> sheetData = sheetDataCache.get(sheetName);

        if (sheetData != null) {
            System.out.println("📋 Sheet keys: " + sheetData.keySet());
            return sheetData.get(testDataID);
        }

        return null;
    }

    private void loadSheetData(String sheetName) {
        try (InputStream fis = getFileFromClasspath(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            System.out.println("📥 Membuka file dari classpath: " + excelPath);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("❌ Sheet tidak ditemukan: " + sheetName);
                return;
            }

            Row header = sheet.getRow(0);
            if (header == null) return;

            Map<String, Map<String, String>> sheetData = new HashMap<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowMap = new HashMap<>();
                String key = null;

                for (int j = 0; j < header.getLastCellNum(); j++) {
                    Cell headerCell = header.getCell(j);
                    Cell cell = row.getCell(j);
                    if (headerCell == null) continue;

                    String colName = headerCell.getStringCellValue();
                    String cellValue = (cell != null) ? getCellValueAsString(cell) : "";

                    rowMap.put(colName, cellValue);

                    if ("testData".equalsIgnoreCase(colName)) {
                        key = cellValue;
                    }
                }

                if (key != null && !key.isEmpty()) {
                    System.out.println("🔑 Memuat testData ID: " + key);
                    sheetData.put(key, rowMap);
                }
            }

            sheetDataCache.put(sheetName, sheetData);
            System.out.println("✅ Sheet " + sheetName + " berhasil dimuat. Total ID: " + sheetData.keySet().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream getFileFromClasspath(String path) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("❌ Gagal menemukan file: " + path + " dari classpath.");
        }
        return is;
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
