package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{
    @FindBy(className = "a-spacing-small")
    private WebElement title;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public String getTitlePage() {
        return title.getText();
    }
}
