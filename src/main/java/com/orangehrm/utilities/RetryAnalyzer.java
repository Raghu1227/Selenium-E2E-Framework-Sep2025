package com.orangehrm.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount=0;
    private static final int maxRetryCount=2; // Set your max retry count here

    @Override
    public boolean retry(ITestResult result) {

    if(retryCount < maxRetryCount){
            retryCount++;
            return true;
        }
        return false;
    }

}
