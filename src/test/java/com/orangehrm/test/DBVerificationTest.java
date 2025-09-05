package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class DBVerificationTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(dataProvider = "emplVerification", dataProviderClass = DataProviders.class, priority = 2, enabled = true, description = "Verify employee name from DB and UI")
    public void verifyEmployeeNameVerificationFromDB(String emp_id, String emp_name) {
        // Implement database verification logic here
        // Example: Fetch employee names from the database and compare with UI
        SoftAssert softAssert = getSoftAssert();
        ExtentManager.logStep("Logging With Admin credentials.");
        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
        ExtentManager.logStep("Click on PIM tab.");
        homePage.clickPimTab();
        ExtentManager.logStep("Searching for employee with ID 1.");
        homePage.employeeSearch(emp_name);
        ExtentManager.logStep("Get the Employee Name from database.");
        String employee_id = emp_id.trim();
        Map<String, String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);
        String dbFirstName = employeeDetails.get("firstname");
        String dbLastName = employeeDetails.get("lastname");
        String dbMiddleName = employeeDetails.get("middlename");
        String employeeFirstAndMidName = (dbFirstName + " " + dbMiddleName).trim();
        ExtentManager.logStep("Employee Name from DB: " + employeeFirstAndMidName);
        softAssert.assertEquals(homePage.verifyEmployeeFirstAndMidName(employeeFirstAndMidName), "First and Middle names do not match!");
        ExtentManager.logStep("Verified the employee lastname from UI and DB are same.");
        softAssert.assertEquals(homePage.verifyEmployeeLastName(dbLastName), "Last names do not match!");
        ExtentManager.logStep("DB validation of employee name completed successfully. ");
        softAssert.assertAll();
       /* homePage.clickuserField();
        homePage.clickLogout();
        ExtentManager.logStep("Logged out successfully.");
        staticWait(2);*/
    }
}
