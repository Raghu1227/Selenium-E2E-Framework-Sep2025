package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtility {

    public static Response sendGetRequest(String endpoint) {
        // Placeholder for sending a GET request
        System.out.println("Sending GET request to: " + endpoint);
        return RestAssured.get(endpoint);
    }

    public static Response sendPostRequest(String endpoint, String payLoad) {
        // Placeholder for sending a POST request
        System.out.println("Sending POST request to: " + endpoint);
        return RestAssured.given().header("Content-Type","application/json")
                                  .body(payLoad).post(endpoint);
    }

    public static boolean validateStatusCode(Response response, int expectedStatusCode) {
        // Placeholder for status code validation
        System.out.println("Validating status code: " + expectedStatusCode);
        return response.getStatusCode() == expectedStatusCode;
    }

    public static String getJsonValue(Response response, String value) {
        // Placeholder for extracting value from JSON response
        System.out.println("Extracting value from JSON path: " + value);
        String value1 = response.jsonPath().getString(value);
        System.out.println("Extracted Value: " + value1);
        return value1;
    }

}
