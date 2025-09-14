package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.Parameters;
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
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
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
    @Parameters("browser")
    public synchronized void setup(String browser) {
        launchBrowser(browser);
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

    /*
     * Initialize the WebDriver based on browser defined in config.properties file
     */
    private synchronized void launchBrowser(String browser) {

        //String browser = prop.getProperty("browser");

        boolean seleniumGrid = Boolean.parseBoolean(prop.getProperty("seleniumGrid"));
        String gridURL = prop.getProperty("gridURL");

        if (seleniumGrid) {
            try {
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                   // options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                    options.addArguments("--headless"); // Run Chrome in headless mode
                    options.addArguments("--disable-gpu"); // Disable GPU for headless mode
                    //options.addArguments("--window-size=1920,1080"); // Set window size
                    options.addArguments("--disable-notifications"); // Disable browser notifications
                    options.addArguments("--no-sandbox"); // Required for some CI environments like Jenkins
                    options.addArguments("--disable-dev-shm-usage"); // Resolve issues in resource-limited environments
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                } else if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                   // options.addArguments("-headless");
                    options.addArguments("--headless"); // Run Firefox in headless mode
                    options.addArguments("--disable-gpu"); // Disable GPU rendering (useful for headless mode)
                    options.addArguments("--width=1920"); // Set browser width
                    options.addArguments("--height=1080"); // Set browser height
                    options.addArguments("--disable-notifications"); // Disable browser notifications
                    options.addArguments("--no-sandbox"); // Needed for CI/CD environments
                    options.addArguments("--disable-dev-shm-usage"); // Prevent crashes in low-resource environments
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                } else if (browser.equalsIgnoreCase("edge")) {
                    EdgeOptions options = new EdgeOptions();
                 //   options.addArguments("--headless=new", "--disable-gpu","--no-sandbox","--disable-dev-shm-usage");
                    options.addArguments("--headless"); // Run Edge in headless mode
                    options.addArguments("--disable-gpu"); // Disable GPU acceleration
                    options.addArguments("--window-size=1920,1080"); // Set window size
                    options.addArguments("--disable-notifications"); // Disable pop-up notifications
                    options.addArguments("--no-sandbox"); // Needed for CI/CD
                    options.addArguments("--disable-dev-shm-usage"); // Prevent resource-limited crashes
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                } else {
                    throw new IllegalArgumentException("Browser Not Supported: " + browser);
                }
                logger.info("RemoteWebDriver instance created for Grid in headless mode");
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL", e);
            }
        } else {

            if (browser.equalsIgnoreCase("chrome")) {

                // Create ChromeOptions
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless"); // Run Chrome in headless mode
                options.addArguments("--disable-gpu"); // Disable GPU for headless mode
                //options.addArguments("--window-size=1920,1080"); // Set window size
                options.addArguments("--disable-notifications"); // Disable browser notifications
                options.addArguments("--no-sandbox"); // Required for some CI environments like Jenkins
                options.addArguments("--disable-dev-shm-usage"); // Resolve issues in resource-limited environments

                // driver = new ChromeDriver();
                driver.set(new ChromeDriver(options)); // New Changes as per Thread
                ExtentManager.registerDriver(getDriver());
                logger.info("ChromeDriver Instance is created.");
            } else if (browser.equalsIgnoreCase("firefox")) {

                // Create FirefoxOptions
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless"); // Run Firefox in headless mode
                options.addArguments("--disable-gpu"); // Disable GPU rendering (useful for headless mode)
                options.addArguments("--width=1920"); // Set browser width
                options.addArguments("--height=1080"); // Set browser height
                options.addArguments("--disable-notifications"); // Disable browser notifications
                options.addArguments("--no-sandbox"); // Needed for CI/CD environments
                options.addArguments("--disable-dev-shm-usage"); // Prevent crashes in low-resource environments

                // driver = new FirefoxDriver();
                driver.set(new FirefoxDriver(options)); // New Changes as per Thread
                ExtentManager.registerDriver(getDriver());
                logger.info("FirefoxDriver Instance is created.");
            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless"); // Run Edge in headless mode
                options.addArguments("--disable-gpu"); // Disable GPU acceleration
                options.addArguments("--window-size=1920,1080"); // Set window size
                options.addArguments("--disable-notifications"); // Disable pop-up notifications
                options.addArguments("--no-sandbox"); // Needed for CI/CD
                options.addArguments("--disable-dev-shm-usage"); // Prevent resource-limited crashes

                // driver = new EdgeDriver();
                driver.set(new EdgeDriver(options)); // New Changes as per Thread
                ExtentManager.registerDriver(getDriver());
                logger.info("EdgeDriver Instance is created.");
            } else {
                throw new IllegalArgumentException("Browser Not Supported:" + browser);
            }
        }
    }


    private void configureBrowser() {
        int implicitWait = Integer.parseInt(prop.getProperty("implicit_wait"));
        boolean seleniumGrid = Boolean.parseBoolean(System.getProperty("seleniumGrid", prop.getProperty("seleniumGrid")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        getDriver().manage().window().maximize();
        getDriver().manage().window().setSize(new Dimension(1920, 1080));
        getDriver().manage().deleteAllCookies();
        //int implicitWait = Integer.parseInt(prop.getProperty("implicit_wait"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
       getDriver().get(prop.getProperty("url_base"));
       /* if (seleniumGrid) {
            getDriver().get(prop.getProperty("url_grid"));
        } else {
            getDriver().get(prop.getProperty("url_base"));
        }*/
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

    public static WebDriver getDriver() {

        if (driver.get() == null) {
            logger.info("WebDriver is not initialized. Please call setup() method first.");
            throw new IllegalStateException("WebDriver is not initialized. Please call setup() method first.");
        }
        return driver.get();
    }

    public static ActionDriver getActionDriver() {

        if (actionDriver.get() == null) {
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
    public void staticWait(int seconds) {
        //  LockSupport.parkNanos(Duration.ofSeconds(seconds).toNanos());
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));

    }
}


