package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartImplementation extends BaseImplementation {

    private static final Logger logger = LogManager.getLogger(CartImplementation.class);

    public CartImplementation() {
        super();
    }

    @Given("user access home page")
    public void accessHomePage() {
        driver.get("https://amazon.com/");
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
        homePage.clickSignInButton();
        signInEmailPage.enterUsername(email);
        signInEmailPage.clickContinueButton();
        signInPasswordPage.enterPassword(password);
        signInPasswordPage.clickLoginButton();
        String actualUserName = homePage.getUserName();
        assertThat(actualUserName, containsString("Bob"));
    }

    @Given("item {string} with less price is added to the basket")
    public void productsAreAddedToTheBasket(String item) {
        homePage.searchProduct(item);
        float minPrice = homePage.getItemWithLessPrice();
        minPriceOfItems.put(item, minPrice);
    }

    @When("user enters into shopping cart")
    public void userEntersIntoShoppingCart() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", homePage.getCart());
        homePage.clickCartButton();
    }

    @When("unauthorised user trys to checkout")
    public void unauthorisedUserTrysToCheckout() {
        cartPage.clickCheckoutButton();
    }

    @Then("cart has {string} items")
    public void checkTotalItemsInTheCart(String items) {
        if (minPriceOfItems.size() == Integer.parseInt(items)) {
            driver.navigate().refresh();
            driver.navigate().refresh();
            assertThat(cartPage.getSubtotalLabel(), equalTo(items));
        } else {
            logger.info("Number of items is not equal to " + minPriceOfItems.keySet());
            throw new AssertionError("Number of items is not equal to " + minPriceOfItems.keySet());
        }
    }

    @Then("total sum calculated is {string} calculated")
    public void checkTotalSumCalculated(String state) {
        float totalSumExpected = minPriceOfItems.values().stream().reduce(0f, Float::sum);

        if (state.equals("correctly")) {
            assertThat(cartPage.getSubtotalAmount(), equalTo(String.valueOf(totalSumExpected)));
        } else if (state.equals("wrong")) {
            assertThat(cartPage.getSubtotalAmount(), not(String.valueOf(totalSumExpected)));
        } else {
            logger.info("Is not correct state given in the step");
            throw new IllegalArgumentException("Incorrect state");
        }
        minPriceOfItems.clear();
    }

    @Then("user is redirected to the registration page")
    public void userIsRedirectedToTheRegistrationPage() {
        assertThat(registrationPage.getTitlePage(), equalTo("Create account"));
    }
}
