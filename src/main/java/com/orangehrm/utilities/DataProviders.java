package com.orangehrm.utilities;

import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProviders {

    private static final String EXCEL_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/TestData.xlsx";

    @DataProvider (name = "validLoginData")
    public static Object[][] validLoginData() {
        return getSheetData("validLoginData");
    }
    @DataProvider (name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        return getSheetData("invalidLoginData");
    }

    @DataProvider (name = "emplVerification")
    public static Object[][] emplVerification() {
        return getSheetData("emplVerification");
    }


    public static Object[][] getSheetData(String sheetName) {
        List<String[]> sheetData = ExcelReaderUtility.getSheetData(EXCEL_FILE_PATH, sheetName);

        Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
        for (int i = 0; i < sheetData.size(); i++) {
            data[i] = sheetData.get(i);
        }
        return data;

    }

}
