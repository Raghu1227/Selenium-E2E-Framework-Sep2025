package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private ActionDriver actiondriver;

    // Locators for login page elements

    // These locators can be updated based on the actual HTML structure of the login page

    private By adminTab = By.xpath("//*[text()='Admin']");
    private By orangeHRMLogo = By.xpath("//img[@alt='client brand banner']");
    private By userfield = By.xpath("//p[@class='oxd-userdropdown-name']");
    private By logout = By.xpath("//a[text()='Logout']");

    private By pimTab = By.xpath("//*[text()='PIM']");
    private By employeeSearch = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By emplFirstAndMidName = By.xpath("div[class='oxd-table-card'] div:nth-child(3) div:nth-child(1)");
    private By emplLastName = By.xpath("div[class='oxd-table-card'] div:nth-child(4) div:nth-child(1)");

   /* public HomePage(WebDriver driver){
        this.actiondriver = new ActionDriver(driver);
    }*/

    // Constructor to initialize the ActionDriver
    public HomePage(WebDriver driver) {
        this.actiondriver = BaseClass.getActionDriver();
    }

    // Method to verify if the OrangeHRM logo is displayed
    public boolean verifyOrangeHRMLogo() {
        return actiondriver.isDisplayed(orangeHRMLogo);
    }

    public boolean verifyAdminTab() {
        return actiondriver.isDisplayed(adminTab);
    }

    // Method to click on the Manda User
    public void clickuserField() {
        actiondriver.click(userfield);
    }

    public void clickLogout() {
        actiondriver.click(logout);
    }

    public void clickPimTab() {
        actiondriver.click(pimTab);
    }

    public void employeeSearch(String value) {
        actiondriver.enterText(employeeSearch, value);
        actiondriver.click(searchButton);
        actiondriver.scrollToElement(emplFirstAndMidName);
        //actiondriver.scrollToElement(emplLastName);

    }

    //Verify Employee First and Middle Name
    public boolean verifyEmployeeFirstAndMidName(String emplFirstAndMidNameFromDB) {

        return actiondriver.compareText(emplFirstAndMidName, emplFirstAndMidNameFromDB);

    }

    public boolean verifyEmployeeLastName(String emplLastNameFromDB) {

        return actiondriver.compareText(emplLastName, emplLastNameFromDB);
    }
}



