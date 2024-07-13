package steps;

import hooks.Hooks;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.SignInEmailPage;
import pages.SignInPasswordPage;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SignInSteps {

    private WebDriver driver;
    private HomePage homePage;
    private SignInEmailPage signInEmailPage;
    private SignInPasswordPage signInPasswordPage;

    public SignInSteps() {
        this.driver = Hooks.driver;
        this.homePage = new HomePage(driver);
        this.signInEmailPage = new SignInEmailPage(driver);
        this.signInPasswordPage = new SignInPasswordPage(driver);
    }

    @Given("user is logged in with valid credentials")
    public void logInWithValidCredentials(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String email = "";
        String password = "";
        for (Map<String, String> row : data) {
            email = row.get("email");
            password = row.get("password");
        }
        driver.get("http://amazon.com/");
        homePage.clickSignInButton();
        signInEmailPage.enterUsername(email);
        signInEmailPage.clickContinueButton();
        signInPasswordPage.enterPassword(password);
        signInPasswordPage.clickLoginButton();
        String actualUserName = homePage.getUserName();
        assertThat(actualUserName, containsString("Bob"));
    }
}
