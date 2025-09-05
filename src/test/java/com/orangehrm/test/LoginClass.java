package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginClass extends BaseClass {

    @Test
    public void loginTest() {
        //ExtentManager.startTest("Login Test", "This test verifies the login functionality of the OrangeHRM application.");// This has been implemented in TestListener.java

        // Implement the login test logic here
        System.out.println(getDriver().getTitle());
        //ExtentManager.startTest("Verifying the title of the OrangeHRM login page.", "This test checks if the title of the OrangeHRM login page is correct.");// This has been implemented in TestListener.java
        ExtentManager.logStep("Navigating to the OrangeHRM login page.");
        Assert.assertEquals(getDriver().getTitle(), "OrangeHRM");
        ExtentManager.logStep("Successfully navigated to the OrangeHRM login page.");
    }
}
