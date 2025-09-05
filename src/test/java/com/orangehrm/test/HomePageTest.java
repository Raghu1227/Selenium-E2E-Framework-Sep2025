package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 0, enabled = true, description = "Verify OrangeHRM logo is displayed on the home page after login")
    public void verifyOrangeHRMLogoTest(String username, String password) {
       // ExtentManager.startTest("Verify OrangeHRM Logo Test", "This test verifies the visibility of the OrangeHRM logo on the home page."); // This has been implemented in TestListener.java
       // ExtentManager.logStep("Starting valid login test with valid credentials.");
        // Navigate to the login page
       // loginPage.login("admin", "admin123");
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login(username, password);
        ExtentManager.logStep("Entered valid credentials: admin/admin123");
        Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "OrangeHRM logo is not displayed.");
        ExtentManager.logStep("Successfully verified the OrangeHRM logo is displayed on the home page.");
        //assert homePage.verifyOrangeHRMLogo() : "OrangeHRM logo is not displayed.";
    }
}
