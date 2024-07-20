package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    @Given("item with the cheapest price is in the basket")
    public void productsAreAddedToTheBasket(DataTable items) {
        List<String> products = items.asList(String.class);
        for (String product : products) {
            homePage.searchProduct(product);
            float minPrice = homePage.getItemWithLessPrice();
            minPriceOfItems.put(product, minPrice);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", homePage.cart);
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.id("nav-cart-count-container"), String.valueOf(minPriceOfItems.size() - 1))));
        }
        wait.until(ExpectedConditions.elementToBeClickable(homePage.cart));
    }

    @Given("cart is cleared")
    public void cartIsCleared() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[value='Delete']")));
        cartPage.clickDeleteButton(driver);
        logger.info("Cart is cleared");
    }

    @When("user enters into shopping cart")
    public void userEntersIntoShoppingCart() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-action-type='DISMISS']")));
            homePage.clickDismissButton();
            logger.info("Dismissed button is clicked");
        } catch (TimeoutException e) {
            logger.info("Dismissed button is not visible, no action taken.");
        }
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("a-changeover")));
            homePage.clickCartButton();
            logger.info("Cart button is clicked");
        } catch (org.openqa.selenium.NoSuchElementException | TimeoutException e) {
            logger.info("Cart button is not visible, no action taken.");
            throw new NoSuchElementException("Cart button is not visible");
        }
    }

    @When("unauthorised user trys to checkout")
    public void unauthorisedUserTrysToCheckout() {
        wait.until(ExpectedConditions.visibilityOf(cartPage.checkout));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("desktop-ptc-button-celWidget")));
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

    @Then("total sum is {string} calculated")
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
        cartIsCleared();
    }

    @Then("user is redirected to the registration page")
    public void userIsRedirectedToTheRegistrationPage() {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://www.amazon.com/gp/cart/view.html?ref_=nav_cart")));
        assertThat(registrationPage.getTitlePage(), equalTo("Create account"));
    }
}
