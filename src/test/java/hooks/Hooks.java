package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.CartPage;

import java.util.concurrent.TimeUnit;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    public static WebDriver driver;
    public static CartPage cartPage;

    @Before(value = "@CalculationsCheck")
    public void setUpChromeDriver() {
        chooseWebDrive("chromedriver");
    }

    @Before(value = "@RedirectionCheck")
    public void setUpFirefoxDriver() {
        chooseWebDrive("geckodriver");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
        }
    }

    private void chooseWebDrive(String webDriver) {
        String os = System.getProperty("os.name").toLowerCase();
        String driverPath = "";

        if (webDriver.equals("chromedriver")) {
            if (os.contains("win")) {
                driverPath = "src/test/resources/drivers/chrome/windows/chromedriver.exe";
            } else if (os.contains("mac")) {
                driverPath = "src/test/resources/drivers/chrome/mac/chromedriver";
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                driverPath = "src/test/resources/drivers/linux/chromedriver";
            }
            System.setProperty("webdriver.chrome.driver", driverPath);
            driver = new ChromeDriver();
        }
        if (webDriver.equals("geckodriver")) {
            if (os.contains("win")) {
                driverPath = "src/test/resources/drivers/mozilla/windows/geckodriver.exe";
            } else if (os.contains("mac")) {
                driverPath = "src/test/resources/drivers/mozilla/mac/geckodriver";
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                driverPath = "src/test/resources/drivers/mozilla/linux/geckodriver";
            }
            System.setProperty("webdriver.geckodriver.driver", driverPath);
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        cartPage = new CartPage(driver);
    }
}
