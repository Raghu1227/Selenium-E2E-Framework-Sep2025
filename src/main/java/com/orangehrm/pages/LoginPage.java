package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage  {

    private ActionDriver actiondriver;

    // Locators for login page elements

    // These locators can be updated based on the actual HTML structure of the login page

    private By usernameFieldLocator = By.name("username"); // Locator for username field
    private By passwordFieldLocator = By.name("password"); // Locator for password field
    private By loginButtonLocator = By.xpath("//button[@type='submit']"); // Locator for login button
    private By loginErrorMessageLocator = By.xpath("//*[text()='Invalid credentials']"); // Locator for login error message
   /* private By loginSuccessMessageLocator = By.id("loginSuccessMessage"); // Locator for login success message
    private By forgotPasswordLinkLocator = By.id("forgotPasswordLink"); // Locator for forgot password link
    private By rememberMeCheckboxLocator = By.id("rememberMeCheckbox"); // Locator for remember me checkbox
    private By loginPageTitleLocator = By.id("loginPageTitle"); // Locator for login page title
    private By loginPageUrlLocator = By.id("loginPageUrl"); // URL for the login page*/

    // Constructor to initialize the ActionDriver
    /*public LoginPage(WebDriver driver){
        this.actiondriver = new ActionDriver(driver);
    }*/

     public LoginPage(WebDriver driver) {
        this.actiondriver = BaseClass.getActionDriver();
    }
    public void enterUsername(String username) {
        actiondriver.enterText(usernameFieldLocator, username);
    }
    public void enterPassword(String password) {
        actiondriver.enterText(passwordFieldLocator, password);
    }
    public void clickLoginButton() {
        actiondriver.click(loginButtonLocator);
    }
    public void login(String username, String password) {

        /*actiondriver.enterText(usernameFieldLocator, "admin");
        actiondriver.enterText(passwordFieldLocator, "admin123");
        actiondriver.click(loginButtonLocator);*/

        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginErrorMessageDisplayed() {
        return actiondriver.isDisplayed(loginErrorMessageLocator);
    }
    public String getLoginErrorMessage() {
        return actiondriver.getText(loginErrorMessageLocator);
    }
    public boolean verifyErrorMessage(String expectedMessage) {
        return actiondriver.compareText(loginErrorMessageLocator, expectedMessage);

    }
    public boolean isLoginButtonDisplayed() {
        return actiondriver.isDisplayed(loginButtonLocator);
    }
    public boolean isUsernameFieldDisplayed() {
        return actiondriver.isDisplayed(usernameFieldLocator);
    }
    public boolean isPasswordFieldDisplayed() {
        return actiondriver.isDisplayed(passwordFieldLocator);
    }

    public void validateLoginErrorMessage(String expectedMessage) {
        String loginErrorMessageLocator = getLoginErrorMessage();
        if (!loginErrorMessageLocator.equals(expectedMessage)) {
            throw new AssertionError("Expected login error message: " + expectedMessage + ", but got: " + loginErrorMessageLocator);
        }
    }

    /*
    public String getLoginSuccessMessage() {
        return actiondriver.getText(loginSuccessMessage);
    }
    public void clickForgotPasswordLink() {
        actiondriver.click(forgotPasswordLink);
    }
    public void clickRememberMeCheckbox() {
        actiondriver.click(rememberMeCheckbox);
    }
    public String getLoginPageTitle() {
        return actiondriver.getTitle(loginPageTitle);
    }
    public String getLoginPageUrl() {
        return actiondriver.getCurrentUrl(loginPageUrl);
    }
    public void navigateToLoginPage() {
        actiondriver.navigateToUrl(getLoginPageUrl());
    }
    public boolean isLoginPageDisplayed() {
        return actiondriver.isElementDisplayed(loginPageTitle);
    }

    public boolean isLoginSuccessMessageDisplayed() {
        return actiondriver.isElementDisplayed(loginSuccessMessage);
    }
    public boolean isForgotPasswordLinkDisplayed() {
        return actiondriver.isElementDisplayed(forgotPasswordLink);
    }
    public boolean isRememberMeCheckboxDisplayed() {
        return actiondriver.isElementDisplayed(rememberMeCheckbox);
    }
    public boolean isLoginButtonDisplayed() {
        return actiondriver.isElementDisplayed(loginButton);
    }
    public boolean isUsernameFieldDisplayed() {
        return actiondriver.isElementDisplayed(usernameField);
    }
    public boolean isPasswordFieldDisplayed() {
        return actiondriver.isElementDisplayed(passwordField);
    }
    public void clearUsernameField() {
        actiondriver.clearField(usernameField);
    }
    public void clearPasswordField() {
        actiondriver.clearField(passwordField);
    }
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    public void logout() {
        // Implement logout functionality if needed
        // This could involve clicking a logout button or link
        // For example:
        // actiondriver.click("logoutButton");
    }
    public void resetPassword(String email) {
        clickForgotPasswordLink();
        // Assuming there's a method to enter email in the forgot password flow
        actiondriver.type("forgotPasswordEmailField", email);
        actiondriver.click("resetPasswordButton");
    }
    public void rememberMe() {
        clickRememberMeCheckbox();
    }
    public void navigateToForgotPasswordPage() {
        actiondriver.click(forgotPasswordLink);
    }
    public void navigateToRememberMePage() {
        actiondriver.click(rememberMeCheckbox);
    }
    public void navigateToLoginPage(String url) {
        actiondriver.navigateToUrl(url);
    }
    public void validateLoginPageElements() {
        if (!isLoginPageDisplayed()) {
            throw new AssertionError("Login page is not displayed");
        }
        if (!isUsernameFieldDisplayed()) {
            throw new AssertionError("Username field is not displayed");
        }
        if (!isPasswordFieldDisplayed()) {
            throw new AssertionError("Password field is not displayed");
        }
        if (!isLoginButtonDisplayed()) {
            throw new AssertionError("Login button is not displayed");
        }
        if (!isForgotPasswordLinkDisplayed()) {
            throw new AssertionError("Forgot password link is not displayed");
        }
        if (!isRememberMeCheckboxDisplayed()) {
            throw new AssertionError("Remember me checkbox is not displayed");
        }
    }

    public void validateLoginSuccessMessage(String expectedMessage) {
        String actualMessage = getLoginSuccessMessage();
        if (!actualMessage.equals(expectedMessage)) {
            throw new AssertionError("Expected login success message: " + expectedMessage + ", but got: " + actualMessage);
        }
    }
    public void validateLoginPageTitle(String expectedTitle) {
        String actualTitle = getLoginPageTitle();
        if (!actualTitle.equals(expectedTitle)) {
            throw new AssertionError("Expected login page title: " + expectedTitle + ", but got: " + actualTitle);
        }
    }
    public void validateLoginPageUrl(String expectedUrl) {
        String actualUrl = getLoginPageUrl();
        if (!actualUrl.equals(expectedUrl)) {
            throw new AssertionError("Expected login page URL: " + expectedUrl + ", but got: " + actualUrl);
        }
    }
    public void validateLoginPage() {
        validateLoginPageElements();
        validateLoginPageTitle("OrangeHRM");
        validateLoginPageUrl("https://example.com/login");
    }
    public void validateLogin(String username, String password, String expectedErrorMessage) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        if (!isLoginErrorMessageDisplayed()) {
            throw new AssertionError("Login error message is not displayed");
        }
        validateLoginErrorMessage(expectedErrorMessage);
    }
    public void validateSuccessfulLogin(String username, String password, String expectedSuccessMessage) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        if (!isLoginSuccessMessageDisplayed()) {
            throw new AssertionError("Login success message is not displayed");
        }
        validateLoginSuccessMessage(expectedSuccessMessage);
    }
    public void validateRememberMeFunctionality(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickRememberMeCheckbox();
        clickLoginButton();
        // Assuming there's a method to check if the user is remembered
        if (!isUsernameFieldDisplayed() || !isPasswordFieldDisplayed()) {
            throw new AssertionError("Remember me functionality failed");
        }
    }
    public void validateForgotPasswordFunctionality(String email) {
        clickForgotPasswordLink();
        // Assuming there's a method to enter email in the forgot password flow
        actiondriver.type("forgotPasswordEmailField", email);
        actiondriver.click("resetPasswordButton");
        // Validate that the reset password confirmation message is displayed
        if (!actiondriver.isElementDisplayed("resetPasswordConfirmationMessage")) {
            throw new AssertionError("Forgot password functionality failed");
        }
    }
    public void validateLogoutFunctionality() {
        // Implement logout functionality validation
        // This could involve clicking a logout button or link and verifying the user is redirected to the login page
        // For example:
        // actiondriver.click("logoutButton");
        // if (!isLoginPageDisplayed()) {
        //     throw new AssertionError("Logout functionality failed");
        // }
    }
    public void validateLoginPageNavigation(String url) {
        navigateToLoginPage(url);
        if (!isLoginPageDisplayed()) {
            throw new AssertionError("Failed to navigate to login page: " + url);
        }
    }
    public void validateForgotPasswordPageNavigation() {
        navigateToForgotPasswordPage();
        if (!actiondriver.isElementDisplayed("forgotPasswordPageTitle")) {
            throw new AssertionError("Failed to navigate to forgot password page");
        }
    }
    public void validateRememberMePageNavigation() {
        navigateToRememberMePage();
        if (!actiondriver.isElementDisplayed("rememberMePageTitle")) {
            throw new AssertionError("Failed to navigate to remember me page");
        }
    }
    public void validateLoginPageElementsVisibility() {
        if (!isUsernameFieldDisplayed()) {
            throw new AssertionError("Username field is not visible on the login page");
        }
        if (!isPasswordFieldDisplayed()) {
            throw new AssertionError("Password field is not visible on the login page");
        }
        if (!isLoginButtonDisplayed()) {
            throw new AssertionError("Login button is not visible on the login page");
        }
        if (!isForgotPasswordLinkDisplayed()) {
            throw new AssertionError("Forgot password link is not visible on the login page");
        }
        if (!isRememberMeCheckboxDisplayed()) {
            throw new AssertionError("Remember me checkbox is not visible on the login page");
        }
    }
*/


    // Additional methods for login page functionality can be added here
}
