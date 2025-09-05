package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginClass2 extends BaseClass {

    @Test
    public void loginTest() {
       // ExtentManager.startTest("Login Test", "This test verifies the login functionality of the OrangeHRM application.");// This has been implemented in TestListener.java
        // Implement the login test logic here
        System.out.println(getDriver().getTitle());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        Assert.assertEquals(getDriver().getTitle(), "OrangeHRM");
        ExtentManager.logStep("Successfully navigated to the OrangeHRM login page.");
        ExtentManager.logSkip("Skipping this test for demonstration purposes.");
        throw new SkipException("Skipping this test for demonstration purposes.");

    }
}
