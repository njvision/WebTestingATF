package steps;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BasicSteps {

    private static final Logger logger = LogManager.getLogger(BasicSteps.class);

    @Given("something is given")
    public void something_is_given() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\driver\\chromedriver.exe");
//        driver = new ChromeDriver(options);
//        driver.get("https://www.google.com");
        Hooks.driver.get("https://www.google.com");
        logger.info("navigate to main page");
    }
    @When("something is hapend")
    public void something_is_hapend() {
//        driver.findElement(new By.ById("W0wltc")).click();
        Hooks.driver.findElement(new By.ById("W0wltc")).click();
    }
    @Then("something is checked")
    public void something_is_checked() {
        logger.info("check title on the page");
//        Assert.assertEquals("Google", driver.getTitle());
        Assert.assertEquals("Google", Hooks.driver.getTitle());
    }
}
