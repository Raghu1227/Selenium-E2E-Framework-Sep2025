package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;


public class BaseClass {


    protected static Properties prop;
   // protected static WebDriver driver;

    //private static ActionDriver actionDriver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
    protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

    public SoftAssert getSoftAssert() {
        return softAssert.get();
    }

    @BeforeSuite
    public void loadConfig() throws FileNotFoundException {
        prop = new Properties();
        FileInputStream fis=new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
        try {
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Configuration properties loaded successfully.");

        // Initialize ExtentReports
      //  ExtentManager.getReporter(); // This has been implemented in TestListener.java


    }

    @BeforeMethod
    public synchronized void setup() {
        launchBrowser();
        configureBrowser();
        staticWait(2);
        logger.info("Web driver initiated and Browser launched and configured successfully.");

        logger.trace("This is Trace message");
        logger.debug("This is Debug message");
        logger.error("This is Error message");
        logger.fatal("This is Debug message");
        logger.warn("This is Error message");

        //Initialize ActionDriver once the driver is set up

       /* if (actionDriver == null) {
            actionDriver = new ActionDriver(driver);
            logger.info("ActionDriver instance is created."+Thread.currentThread().getId());
        }*/

        actionDriver.set(new ActionDriver(getDriver()));
       // logger.info("ActionDriver instance is created successfully." + Thread.currentThread().getId());
        System.out.println("ActionDriver instance is created successfully." + Thread.currentThread().getId());



    }

    private synchronized void launchBrowser() {
        String browserName = prop.getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--headless=new"); // Run in headless mode
            options.addArguments("--disable-gpu"); // Disable GPU acceleration
            //options.addArguments("--window-size=1920,1080"); // Set window size
            //options.addArguments("--incognito"); // Incognito mode
            options.addArguments("--disable-extensions"); // Disable extensions
            options.addArguments("--no-sandbox"); // Bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            // Initialize Chrome driver
            //driver = new ChromeDriver();
            driver.set(new ChromeDriver(options));
            logger.info("Chrome browser launched successfully.");
            ExtentManager.registerDriver(getDriver());
        } else if (browserName.equalsIgnoreCase("firefox")) {
            // Initialize Firefox driver
            //driver = new FirefoxDriver();
            driver.set(new FirefoxDriver());
            logger.info("Firefox browser launched successfully.");
            ExtentManager.registerDriver(getDriver());
        }else if (browserName.equalsIgnoreCase("edge")) {
            // Initialize Firefox driver
            //driver = new EdgeDriver();
            driver.set(new EdgeDriver());
            logger.info("Edge browser launched successfully.");
            ExtentManager.registerDriver(getDriver());
        }  else {
            throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
    }

    private void configureBrowser() {
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        int implicitWait = Integer.parseInt(prop.getProperty("implicit_wait"));
       getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        getDriver().get(prop.getProperty("url"));
       // System.out.println(driver.getTitle());

    }

    @AfterMethod
    public synchronized void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        logger.info("Web driver instance closed successfully.");
       // driver = null; // Clear the driver reference
        driver.remove();
        logger.info("Browser closed successfully.");
        //actionDriver = null; // Clear the ActionDriver reference
        actionDriver.remove();
        //ExtentManager.endTest(); // This has been implemented in TestListener.java
    }

    /*public  WebDriver getDriver() {
        return driver;
    }*/

    public static WebDriver getDriver() {

        if(driver.get() == null) {
            logger.info("WebDriver is not initialized. Please call setup() method first.");
            throw new IllegalStateException("WebDriver is not initialized. Please call setup() method first.");
        }
        return driver.get();
    }
    public static ActionDriver getActionDriver() {

        if(actionDriver.get() == null) {
            logger.info("ActionDriver is not initialized. Please call setup() method first.");
            throw new IllegalStateException("ActionDriver is not initialized. Please call setup() method first.");
        }
        return actionDriver.get();
    }
    public void setDriver(ThreadLocal<WebDriver> driver) {

        this.driver = driver;
    }
    public static Properties getProp() {
        return prop;
    }

    public static void setProp(Properties prop) {
        BaseClass.prop = prop;
    }

    //Static wait method for pausing execution
    public void staticWait(int seconds){
      //  LockSupport.parkNanos(Duration.ofSeconds(seconds).toNanos());
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));

    }
}


