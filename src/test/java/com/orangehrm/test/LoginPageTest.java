package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 0, enabled = true, description = "Verify valid login with correct credentials")
    public void verifyValidLoginTest(String username, String password) {
      //  ExtentManager.startTest("Verify Valid Login Test", "This test verifies the valid login functionality of OrangeHRM application.");// This has been implemented in TestListener.java
        ExtentManager.logStep("Starting valid login test with valid credentials.");
        // Navigate to the login page
       // loginPage.login("admin", "admin123");
        loginPage.login(username, password);
        ExtentManager.logStep("Entered valid credentials: admin/admin123");
        Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "OrangeHRM logo is not displayed.");
        Assert.assertTrue(homePage.verifyAdminTab(), "Admin Tab should be visible after successfully login.");
       ExtentManager.logStep("Successfully logged in and verified the Admin tab is visible.");
        homePage.clickuserField();
        homePage.clickLogout();
        ExtentManager.logStep("Logged out successfully.");
        staticWait(2);
    }

    @Test(dataProvider = "invalidLoginData", dataProviderClass = DataProviders.class, priority = 1, enabled = true, description = "Verify invalid login with incorrect credentials")
    public void verifyInValidLoginTest(String username, String password) {
        //ExtentManager.startTest("Verify Invalid Login Test", "This test verifies the invalid login functionality of OrangeHRM application with incorrect credentials.");// This has been implemented in TestListener.java
        ExtentManager.logStep("Starting valid login test with Invalid credentials.");
        // Navigate to the login page
        //loginPage.login("admin", "admin12345");
        loginPage.login(username, password);
        ExtentManager.logStep("Entered invalid credentials: admin/admin12345");
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),
                "Error message is not displayed as expected: " + expectedErrorMessage);
        ExtentManager.logStep("Verified the error message for invalid login.");

    }

}
