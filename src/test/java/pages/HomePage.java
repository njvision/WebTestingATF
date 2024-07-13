package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(id = "nav-link-accountList")
    private WebElement signInOrHelloButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickSignInButton() {
        signInOrHelloButton.click();
    }

    public String getUserName() {
        return signInOrHelloButton.getText();
    }
}
