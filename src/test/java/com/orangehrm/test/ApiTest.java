package com.orangehrm.test;


import com.orangehrm.utilities.ApiUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ApiTest {

    @Test()
    public void verifyGetUserAPI() {
        SoftAssert softAssert = new SoftAssert();

        // Step1- Define API endpoint
        System.out.println("Verifying GET User API");
        String endpoint = "https://jsonplaceholder.typicode.com/users/1";
        ExtentManager.logStep("Verifying GET User API with endpoint: " + endpoint);
        // Step2- Send get request
        ExtentManager.logStep("Sending GET request to: " + endpoint);
        Response response=ApiUtility.sendGetRequest(endpoint);
        // Step3- Validate status code
        int expectedStatusCode=200;
        ExtentManager.logStep("Validating status code: " + expectedStatusCode);
        boolean statusCodeValidation=ApiUtility.validateStatusCode(response,expectedStatusCode);
        softAssert.assertTrue(statusCodeValidation,"Status code validation failed. Expected: " + expectedStatusCode + ", Actual: " + response.getStatusCode());
        if(statusCodeValidation){
            ExtentManager.logStepValidationForAPI( "Status code validation passed: " + expectedStatusCode);
        }else{
            ExtentManager.logFailureAPI("Status code validation failed. Expected: " + expectedStatusCode + ", Actual: " + response.getStatusCode());
        }

        // Step4- Validate username
        String expectedUsername="Bret";
        ExtentManager.logStep("Validating username from JSON path: " + expectedUsername);
        String userName=ApiUtility.getJsonValue(response,"username");
        boolean isUserNameValid=expectedUsername.equals(userName);
        softAssert.assertTrue(isUserNameValid,"Username validation failed. Expected: " + expectedUsername + ", Actual: " + userName);
        if(isUserNameValid){
            ExtentManager.logStepValidationForAPI( "Username validation passed: " + expectedUsername);
        }else{
            ExtentManager.logFailureAPI("Username validation failed. Expected: " + expectedUsername + ", Actual: " + userName);
        }

        // Step4- Validate email
        String expectedEmail="Sincere@april.biz";
        ExtentManager.logStep("Validating email from JSON path: " + expectedEmail);
        String email=ApiUtility.getJsonValue(response,"email");
        boolean isEmailValid=expectedEmail.equals(email);
        softAssert.assertTrue(isEmailValid,"Email validation failed. Expected: " + expectedEmail + ", Actual: " + email);
        if(isEmailValid){
            ExtentManager.logStepValidationForAPI( "Email validation passed: " + expectedEmail);
        }else{
            ExtentManager.logFailureAPI("Email validation failed. Expected: " + expectedEmail + ", Actual: " + email);
        }
        softAssert.assertAll();
    }
}
