package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.CartPage;

import java.util.concurrent.TimeUnit;

public class Hooks {
    public static WebDriver driver;
    public static CartPage cartPage;

    @Before
    public void setUp() {
        String os = System.getProperty("os.name").toLowerCase();
        String driverPath = "";

        if (os.contains("win")) {
            driverPath = "src/test/resources/drivers/windows/chromedriver.exe";
        } else if (os.contains("mac")) {
            driverPath = "src/test/resources/drivers/mac/chromedriver";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            driverPath = "src/test/resources/drivers/linux/chromedriver";
        }

        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        cartPage = new CartPage(driver);
    }

    @After
    public void tearDown() {
        cartPage.clickDeleteButton();
        if (driver != null) {
            driver.quit();
        }
    }
}
