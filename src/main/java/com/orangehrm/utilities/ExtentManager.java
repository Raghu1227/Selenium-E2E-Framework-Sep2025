package com.orangehrm.utilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new HashMap<>();

   //Intialize the ExtentReports instance
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
          String reportPath=System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("OrangeHRM Automation Test Report");
            sparkReporter.config().setDocumentTitle("OrangeHRM Test Results");
            sparkReporter.config().setTheme(Theme.DARK);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "OrangeHRM");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Test Suite", "OrangeHRM Automation Tests");
            extent.setSystemInfo("Test Framework", "TestNG");
            extent.setSystemInfo("Test Execution Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            extent.setSystemInfo("Test Execution Thread", String.valueOf(Thread.currentThread().getId()));
            extent.setSystemInfo("Test Execution User","Raghu");

        }
        return extent;
    }
    //Start a new test in the ExtentReports instance
    public synchronized static ExtentTest startTest(String testName, String description) {
        ExtentTest extentTest = getReporter().createTest(testName, description);
        test.set(extentTest);
        return extentTest;
    }
    // End the current test in the ExtentReports instance
    public synchronized static void endTest() {
        getReporter().flush();
    }

    //Get Current Thread Test
    public synchronized static ExtentTest getTest() {
        return test.get();
    }

    //Method to get the name of the current test
    public synchronized static String getTestName() {
        //return getTest().getModel().getName();
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            return currentTest.getModel().getName();
        } else {
            return "No test is currently running";
        }
    }
    public static void  logInfo(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            currentTest.info(message);
        } else {
            System.out.println("No test is currently running to log the message: " + message);
        }
    }
    //Log a step validation
    public static void logStep(String logMessage) {
        getTest().info(logMessage);
    }

    //Log a step validation with screenshot
    public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenshotMessage) {
        getTest().pass(logMessage);
        attachScreenshot(driver, screenshotMessage);

    }

    //Log a step validation for API testing without screenshot
    public static void logStepValidationForAPI(String logMessage) {
        getTest().pass(logMessage);

    }
    //Log failure for API testing without screenshot
    public static void logFailureAPI(String logMessage){
        String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
        getTest().fail(colorMessage);

    }

    public static void logFailure(WebDriver driver, String logMessage, String screenshotMessage){
        String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
        getTest().fail(colorMessage);
        attachScreenshot(driver, screenshotMessage);
        // Here you can add code to capture a screenshot and attach it to the report
        // For example, using a method like `attachScreenshot(driver, screenshotMessage);`

    }
    public static void logSkip(String logMessage){
        String colorMessage = String.format("<span style='color:orange;'>%s</span>", logMessage);
        getTest().skip(colorMessage);

    }

    //Take a screenshot with data and time in the file
    public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
        // Implement your screenshot logic here
        // For example, you can use a method to capture the screenshot and return the file path

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src= ts.getScreenshotAs(OutputType.FILE);
       //Format date and time for file name
       String timestamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
      //Save the screenshot to a file
      String destPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + screenshotName + "_" + timestamp + ".png";
     File finaldestpath= new File(destPath);
        try {
            FileUtils.copyFile(src, finaldestpath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Convert screenshot to Base64 string if needed
        String base64Format=convertToBase64(src);
        return base64Format;

       /*//Convert screenshot to Base64 string if needed
        String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        //Attach the screenshot to the ExtentTest
        getTest().addScreenCaptureFromBase64String(base64Screenshot, screenshotName);
        //Attach the screenshot to the ExtentTest
        getTest().addScreenCaptureFromPath(finaldestpath.getAbsolutePath(), screenshotName);
        return "base64Screenshot"; // Placeholder for actual screenshot path*/
    }

    public static String convertToBase64(File screenShotFile) {
        String base64Format = "";

        try {
            byte[] fileContent = fileContent=FileUtils.readFileToByteArray(screenShotFile);
            base64Format= Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Format ;

    }

    //Attach screenshot to report using Base64
    public synchronized static void attachScreenshot(WebDriver driver,String message) {
        try {
            String base64Screenshot = takeScreenshot(driver, getTestName());
            //getTest().addScreenCaptureFromBase64String(base64Screenshot, getTestName());
            getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, getTestName()).build());
        } catch (Exception e) {
            getTest().fail("Failed to attach screenshot: " + e.getMessage());
            e.printStackTrace();
        }

    }

    //Get WebDriver for the current thread
    public static void registerDriver(WebDriver driver) {
        driverMap.put(Thread.currentThread().getId(), driver);
    }
}
