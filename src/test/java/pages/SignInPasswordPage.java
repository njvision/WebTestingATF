package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPasswordPage extends BasePage{

    @FindBy(id = "ap_password")
    private WebElement passwordField;

    @FindBy(id = "signInSubmit")
    private WebElement signInButton;

    public SignInPasswordPage(WebDriver driver) {
        super(driver);
    }

    public void enterPassword(String username) {
        passwordField.sendKeys(username);
    }

    public void clickLoginButton() {
        signInButton.click();
    }
}
