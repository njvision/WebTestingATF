package steps;

import hooks.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.HomePage;
import pages.RegistrationPage;
import pages.SignInEmailPage;
import pages.SignInPasswordPage;

import java.time.Duration;
import java.util.HashMap;

public class BaseImplementation {
    protected WebDriverWait wait;

    protected WebDriver driver;
    protected HomePage homePage;
    protected SignInEmailPage signInEmailPage;
    protected SignInPasswordPage signInPasswordPage;
    protected CartPage cartPage;
    protected RegistrationPage registrationPage;
    protected HashMap<String, Float> minPriceOfItems = new HashMap<>();

    public BaseImplementation() {
        this.driver = Hooks.driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.homePage = new HomePage(driver);
        this.signInEmailPage = new SignInEmailPage(driver);
        this.signInPasswordPage = new SignInPasswordPage(driver);
        this.cartPage = Hooks.cartPage;
        this.registrationPage = new RegistrationPage(driver);
    }
}
