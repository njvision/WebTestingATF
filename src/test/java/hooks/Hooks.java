package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Hooks {
    public static WebDriver driver;

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
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
