package com.orangehrm.actiondriver;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;
import org.apache.logging.log4j.Logger;


public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;
    //public static final Logger logger = (Logger) LoggerManager.getLogger();
    public static final Logger logger=BaseClass.logger;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicit_wait"));
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(explicitWait));
        logger.info("WebDriver instance is created");
       // this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10)); // Default explicit wait time set to 10 seconds
    }

    // Method to click on an element using its By locator
    public void click(By by) {
        try {
            applyBorder(by, "green");
            waitforElementToBeClickable(by);
            driver.findElement(by).click();
           // ExtentManager.logInfo("Clicked on element: " + by.toString());
            ExtentManager.logStep("Clicked on element: " + by.toString());
            logger.info("Clicked on element: " );
        } catch (Exception e) {
            applyBorder(by, "red");
            logger.info("Element not clickable: " + by.toString());
            ExtentManager.logFailure(BaseClass.getDriver(),
                    "Unable to click on element: " + by.toString(),e.getMessage());
            logger.error("Unable to click on element.");
            e.printStackTrace();
        }
    }
    // Method to send text to an element using its By locator
    public void enterText(By by, String value) {
        try {
            waitforElementToBeVisible(by);
            applyBorder(by, "green");
            //driver.findElement(by).clear(); // Clear the field before sending keys
            //driver.findElement(by).sendKeys(value);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
            logger.info("Entered text: " + value + " into element: " + by.toString());

        } catch (Exception e) {
            applyBorder(by, "red");
            logger.error("Element not visible or not intractable: " + e.getMessage());
            e.printStackTrace();
        }
    }
   // Method to get text from an element using its By locator
    public String getText(By by) {
        try {
            waitforElementToBeVisible(by);
            applyBorder(by, "green");
            String text = driver.findElement(by).getText();
            logger.info("Text from element: " + text);
        } catch (Exception e) {
            applyBorder(by, "red");
            logger.error("Element not visible or not intractable: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
     public boolean compareText(By by,String expectedText){

        waitforElementToBeVisible(by);
        String actualText = driver.findElement(by).getText();

        if (actualText.equals(expectedText)) {
            applyBorder(by, "green");
            logger.info("Text matches: " + actualText);
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Text matches: " + actualText, "Text comparison successful");
            return true;
        } else {
            applyBorder(by, "red");
            logger.error("Text does not match. Expected: " + expectedText + ", Actual: " + actualText);
            ExtentManager.logFailure(BaseClass.getDriver(),
                    "Text does not match. Expected: " + expectedText + ", Actual: " + actualText, "Text comparison failed");
            return false;
        }
       }

       public boolean isDisplayed(By by){
        waitforElementToBeVisible(by);
        boolean isDisplayed = driver.findElement(by).isDisplayed();
        if (isDisplayed) {
            applyBorder(by, "green");
            logger.info("Element is displayed: " + by.toString());
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is displayed: " + by.toString(), "Element visibility check successful");
            return isDisplayed;
        } else {
            applyBorder(by, "red");
            logger.error("Element is not displayed: " + by.toString());
            ExtentManager.logFailure(BaseClass.getDriver(),
                    "Element is not displayed: " + by.toString(), "Element visibility check failed");
            return isDisplayed;
        }

       }
       public void isEnabled(By by){
        waitforElementToBeVisible(by);
        boolean isEnabled = driver.findElement(by).isEnabled();
        if (isEnabled) {
            logger.info("Element is enabled: " + by.toString());
        } else {
            logger.error("Element is not enabled: " + by.toString());
        }
       }
       public void isSelected(By by){
        waitforElementToBeVisible(by);
        boolean isSelected = driver.findElement(by).isSelected();
        if (isSelected) {
            logger.info("Element is selected: " + by.toString());
        } else {
            logger.error("Element is not selected: " + by.toString());
        }
       }
       public  void switchToFrame(By by) {
           try {
               waitforElementToBeVisible(by);
               driver.switchTo().frame(driver.findElement(by));
               logger.info("Switched to frame: " + by.toString());
           } catch (Exception e) {
               logger.error("Failed to switch to frame: " + by.toString());
               e.printStackTrace();
           }
       }
       public void switchToDefaultContent() {
           try {
               driver.switchTo().defaultContent();
               logger.info("Switched to default content.");
           } catch (Exception e) {
               logger.error("Failed to switch to default content.");
               e.printStackTrace();
           }
       }
       public  void switchToWindow(String windowHandle) {
           try {
               driver.switchTo().window(windowHandle);
               logger.info("Switched to window: " + windowHandle);
           } catch (Exception e) {
               logger.error("Failed to switch to window: " + windowHandle);
               e.printStackTrace();
           }
       }

         public void switchToNewWindow() {
              try {
                String originalWindow = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                     if (!windowHandle.equals(originalWindow)) {
                          driver.switchTo().window(windowHandle);
                          logger.info("Switched to new window: " + windowHandle);
                          break;
                     }
                }
              } catch (Exception e) {
                logger.error("Failed to switch to new window.");
                e.printStackTrace();
              }
         }
         public void switchToAlert() {
             try {
                 driver.switchTo().alert();
                 logger.info("Switched to alert.");
             } catch (Exception e) {
                 logger.error("Failed to switch to alert.");
                 e.printStackTrace();
             }
         }
         public void switchToAlertAndAccept() {
             try {
                 driver.switchTo().alert().accept();
                 System.out.println("Accepted alert.");
             } catch (Exception e) {
                 System.out.println("Failed to accept alert.");
                 e.printStackTrace();
             }
         }
          public void switchToAlertAndDismiss() {
              try {
                  driver.switchTo().alert().dismiss();
                  System.out.println("Dismissed alert.");
              } catch (Exception e) {
                  System.out.println("Failed to dismiss alert.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndGetText() {
              try {
                  String alertText = driver.switchTo().alert().getText();
                  System.out.println("Alert text: " + alertText);
              } catch (Exception e) {
                  System.out.println("Failed to get alert text.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndSendKeys(String text) {
              try {
                  driver.switchTo().alert().sendKeys(text);
                  System.out.println("Sent keys to alert: " + text);
              } catch (Exception e) {
                  System.out.println("Failed to send keys to alert.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndAcceptAndGetText() {
              try {
                  String alertText = driver.switchTo().alert().getText();
                  driver.switchTo().alert().accept();
                  System.out.println("Accepted alert and got text: " + alertText);
              } catch (Exception e) {
                  System.out.println("Failed to accept alert and get text.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndDismissAndGetText() {
              try {
                  String alertText = driver.switchTo().alert().getText();
                  driver.switchTo().alert().dismiss();
                  System.out.println("Dismissed alert and got text: " + alertText);
              } catch (Exception e) {
                  System.out.println("Failed to dismiss alert and get text.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndAcceptAndSendKeys(String text) {
              try {
                  driver.switchTo().alert().sendKeys(text);
                  driver.switchTo().alert().accept();
                  System.out.println("Accepted alert and sent keys: " + text);
              } catch (Exception e) {
                  System.out.println("Failed to accept alert and send keys.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndDismissAndSendKeys(String text) {
              try {
                  driver.switchTo().alert().sendKeys(text);
                  driver.switchTo().alert().dismiss();
                  System.out.println("Dismissed alert and sent keys: " + text);
              } catch (Exception e) {
                  System.out.println("Failed to dismiss alert and send keys.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndAcceptAndGetTextAndSendKeys(String text) {
              try {
                  String alertText = driver.switchTo().alert().getText();
                  driver.switchTo().alert().sendKeys(text);
                  driver.switchTo().alert().accept();
                  System.out.println("Accepted alert, got text: " + alertText + ", and sent keys: " + text);
              } catch (Exception e) {
                  System.out.println("Failed to accept alert, get text, and send keys.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndDismissAndGetTextAndSendKeys(String text) {
              try {
                  String alertText = driver.switchTo().alert().getText();
                  driver.switchTo().alert().sendKeys(text);
                  driver.switchTo().alert().dismiss();
                  System.out.println("Dismissed alert, got text: " + alertText + ", and sent keys: " + text);
              } catch (Exception e) {
                  System.out.println("Failed to dismiss alert, get text, and send keys.");
                  e.printStackTrace();
              }
          }
          public void switchToAlertAndAcceptAndGetTextAndDismiss() {
              try {
                  String alertText = driver.switchTo().alert().getText();
                  driver.switchTo().alert().accept();
                  System.out.println("Accepted alert, got text: " + alertText + ", and dismissed.");
              } catch (Exception e) {
                  System.out.println("Failed to accept alert, get text, and dismiss.");
                  e.printStackTrace();
              }
          }
         public void switchToAlertAndDismissAndGetTextAndAccept() {
             try {
                 String alertText = driver.switchTo().alert().getText();
                 driver.switchTo().alert().dismiss();
                 System.out.println("Dismissed alert, got text: " + alertText + ", and accepted.");
             } catch (Exception e) {
                 System.out.println("Failed to dismiss alert, get text, and accept.");
                 e.printStackTrace();
             }
         }



       public void scrollToElement(By by){

           JavascriptExecutor js = (JavascriptExecutor) driver;
              try {
                  applyBorder(by, "green");
                waitforElementToBeVisible(by);
                js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
                System.out.println("Scrolled to element: " + by.toString());
              } catch (Exception e) {
                applyBorder(by, "red");
                System.out.println("Failed to scroll to element: " + by.toString());
                e.printStackTrace();
              }
       }
       public void scrollToBottom() {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               applyBorder(By.tagName("body"), "green");
               js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
               logger.info("Scrolled to bottom of the page.");
           } catch (Exception e) {
                applyBorder(By.tagName("body"), "red");
               logger.error("Failed to scroll to bottom of the page.");
               e.printStackTrace();
           }
       }
       public void scrollToTop() {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
                applyBorder(By.tagName("body"), "green");
               js.executeScript("window.scrollTo(0, 0);");
               logger.info("Scrolled to top of the page.");
           } catch (Exception e) {
                applyBorder(By.tagName("body"), "red");
               logger.error("Failed to scroll to top of the page.");
               e.printStackTrace();
           }
       }
       public void scrollToElementAndClick(By by) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               driver.findElement(by).click();
               logger.info("Scrolled to element and clicked: " + by.toString());
           } catch (Exception e) {
               logger.error("Failed to scroll to element and click: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndEnterText(By by, String text) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               driver.findElement(by).clear(); // Clear the field before sending keys
               driver.findElement(by).sendKeys(text);
               logger.info("Scrolled to element and entered text: " + by.toString());
           } catch (Exception e) {
               logger.error("Failed to scroll to element and enter text: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndGetText(By by) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               String text = driver.findElement(by).getText();
               logger.info("Scrolled to element and got text: " + text);
           } catch (Exception e) {
               logger.error("Failed to scroll to element and get text: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndCompareText(By by) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               String actualText = driver.findElement(by).getText();
               String expectedText = "OrangeHRM";
               if (actualText.equals(expectedText)) {
                   logger.info("Scrolled to element and text matches: " + actualText);
               } else {
                   logger.info("Scrolled to element and text does not match. Expected: " + expectedText + ", Actual: " + actualText);
               }
           } catch (Exception e) {
               logger.error("Failed to scroll to element and compare text: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndCheckDisplayed(By by) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               boolean isDisplayed = driver.findElement(by).isDisplayed();
               if (isDisplayed) {
                   logger.info("Scrolled to element and it is displayed: " + by.toString());
               } else {
                   logger.info("Scrolled to element but it is not displayed: " + by.toString());
               }
           } catch (Exception e) {
               logger.error("Failed to scroll to element and check if displayed: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndClickAndEnterText(By by, String text) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               driver.findElement(by).click();
               driver.findElement(by).clear(); // Clear the field before sending keys
               driver.findElement(by).sendKeys(text);
               logger.info("Scrolled to element, clicked, and entered text: " + by.toString());
           } catch (Exception e) {
               logger.error("Failed to scroll to element, click, and enter text: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndClickAndGetText(By by) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               driver.findElement(by).click();
               String text = driver.findElement(by).getText();
               logger.info("Scrolled to element, clicked, and got text: " + text);
           } catch (Exception e) {
               logger.error("Failed to scroll to element, click, and get text: " + by.toString());
               e.printStackTrace();
           }
       }
       public void scrollToElementAndClickAndCompareText(By by) {
           JavascriptExecutor js = (JavascriptExecutor) driver;
           try {
               waitforElementToBeVisible(by);
               js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
               driver.findElement(by).click();
               String actualText = driver.findElement(by).getText();
               String expectedText = "OrangeHRM";
               if (actualText.equals(expectedText)) {
                   logger.info("Scrolled to element, clicked, and text matches: " + actualText);
               } else {
                   logger.info("Scrolled to element, clicked, and text does not match. Expected: " + expectedText + ", Actual: " + actualText);
               }
           } catch (Exception e) {
               logger.error("Failed to scroll to element, click, and compare text: " + by.toString());
               e.printStackTrace();
           }
       }
       public void waitForPageLoad(int timeoutInSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            logger.info("Page loaded successfully.");
        } catch (Exception e) {
            logger.error("Page did not load within the specified timeout: " + timeoutInSeconds + " seconds.");
            e.printStackTrace();
        }
       }

    private void waitforElementToBeClickable(By by) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("Element not clickable: " + by.toString());
            e.printStackTrace();
        }
    }
    private void waitforElementToBeVisible(By by) {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.error("Element not visible: " + by.toString());
            e.printStackTrace();
        }
    }


    //Utility method to border an element
    public void applyBorder(By by,String color){
        try {
            WebElement element = driver.findElement(by);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid " + color + "';", element);
            logger.info("Applied the border with color: " +color+ " to element: " +by.toString() );
        } catch (Exception e) {
            logger.error("Failed to apply border to element: " + by.toString());
            e.printStackTrace();
        }
    }

}
