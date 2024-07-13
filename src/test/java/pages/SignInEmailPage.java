package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInEmailPage extends BasePage{

    @FindBy(id = "ap_email")
    private WebElement usernameField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(className = "a-box")
    private WebElement title;

    public SignInEmailPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

}
