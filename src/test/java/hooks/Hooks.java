package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    public static WebDriver driver;

    @Before(value = "@CalculationsCheck")
    public void setUpChromeDriver() throws IOException, InterruptedException {
        chooseWebDrive("chromedriver");
    }

    @Before(value = "@RedirectionCheck")
    public void setUpFirefoxDriver() throws IOException, InterruptedException {
        chooseWebDrive("geckodriver");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            logger.info("Deleting all cookies");
            driver.manage().deleteAllCookies();

            logger.info("Closing WebDriver");
            driver.quit();
        }
    }

    private void chooseWebDrive(String webDriver) throws IOException, InterruptedException {
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

        logger.info(webDriver + " is initialized");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://amazon.com/");

        if (isCaptchaPresent()) {
            solveCaptcha();
        }
    }

    private boolean isCaptchaPresent() {
        try {
            driver.findElement(By.id("captchacharacters"));
            logger.info("The captcha is present");
            return true;
        } catch (Exception e) {
            logger.info("The captcha isn't present");
            return false;
        }
    }

    private void solveCaptcha() throws IOException, InterruptedException {
        WebElement captchaImageElement = driver.findElement(By.tagName("img"));

        File captchaImage = captchaImageElement.getScreenshotAs(OutputType.FILE);

        File captchaImageFile = new File("src/test/resources/captcha/captcha.png");
        FileHandler.copy(captchaImage, captchaImageFile);

        String captchaText = extractTextFromImage(captchaImageFile);

        WebElement captchaInputField = driver.findElement(By.id("captchacharacters"));
        captchaInputField.sendKeys(captchaText);

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

//        Thread.sleep(5000);
    }

    private String extractTextFromImage(File imageFile) {
        Tesseract tesseract = new Tesseract();

        tesseract.setDatapath("src/test/resources/tessdata");

        try {
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            logger.error("Error during OCR processing", e);
            return "";
        }
    }
}
