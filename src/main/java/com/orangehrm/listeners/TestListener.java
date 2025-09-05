package com.orangehrm.listeners;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;

public class TestListener implements ITestListener, IAnnotationTransformer {

    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    // Implement methods from ITestListener interface to handle test events
    // For example, you can log test start, end, and failure events here

    // Triggered when a test starts
    @Override
    public void onTestStart(ITestResult result) {
        // Code to execute when a test starts
        System.out.println("Test started: " + result.getName());
        // Start a new test in ExtentReports
        String testName = result.getMethod().getMethodName();
        ExtentManager.startTest(testName, "Test started: " + testName);
    }

    // Triggered when a test passes, fails, or is skipped
    @Override
    public void onTestSuccess(ITestResult result) {
        // Code to execute when a test passes
        System.out.println("Test passed: " + result.getName());
        String testName = result.getMethod().getMethodName();
        if (!result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test passed: " + testName, "Screenshot for " + testName);
        } else {
            ExtentManager.logStepValidationForAPI("Test passed: " + testName);
        }
    }

    // Triggered when a test fails or is skipped
    @Override
    public void onTestFailure(ITestResult result) {
        // Code to execute when a test fails
        System.out.println("Test failed: " + result.getName());
        String testName = result.getMethod().getMethodName();
        String failureMessage = result.getThrowable() != null ? result.getThrowable().getMessage() : "No exception message";
        ExtentManager.logStep(failureMessage);
        if (!result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test failed: " + testName, "Screenshot for " + testName);
        } else {
            ExtentManager.logFailureAPI("Test failed: " + testName);
        }

    }

    // Triggered when a test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        // Code to execute when a test is skipped
        System.out.println("Test skipped: " + result.getName());
        String testName = result.getMethod().getMethodName();
        ExtentManager.logSkip("Test skipped: " + testName);
        // You can also log the reason for skipping if available
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("Reason for skipping: " + throwable.getMessage());
            ExtentManager.logStep("Reason for skipping: " + throwable.getMessage());
        }

    }

    // You can add more methods as needed for other events like configuration success/failure, etc.
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Code to execute when a test fails but is within the success percentage
        System.out.println("Test failed but within success percentage: " + result.getName());
    }

    //Triggered when a suite starts
    @Override
    public void onStart(ITestContext context) {
        // Code to execute before any test starts
        System.out.println("Test suite started: " + context.getName());
        // Start a new test in ExtentReports
        ExtentManager.getReporter();
    }

    // Triggered when a suite finishes
    @Override
    public void onFinish(ITestContext context) {
        // Code to execute after all tests in the suite have finished
        System.out.println("Test suite finished: " + context.getName());
        // End the current test in ExtentReports
        ExtentManager.endTest();

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        // Code to execute when a test fails due to timeout
        System.out.println("Test failed due to timeout: " + result.getName());
    }


}
