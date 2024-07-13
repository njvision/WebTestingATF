package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.min;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.BasicSteps;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(BasicSteps.class);

    @FindBy(id = "nav-link-accountList")
    private WebElement signInOrHelloButton;

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchTextField;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@data-component-type ='s-search-results']")
    private WebElement productList;

    @FindBy(id = "nav-cart-count-container")
    private WebElement cart;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickSignInButton() {
        signInOrHelloButton.click();
    }

    public String getUserName() {
        return signInOrHelloButton.getText();
    }

    public void searchProduct(String productName) {
        searchTextField.clear();
        searchTextField.sendKeys(productName);
        searchButton.click();
    }

    public float getItemWithLessPrice() {
        List<WebElement> productsWithAddButton = productList.findElements(By.xpath("//div[@data-csa-c-content-id='s-search-add-to-cart-action']"));

        Map<WebElement, Float> productPriceMap = new HashMap<>();

        for (WebElement button : productsWithAddButton) {
            try {
                WebElement priceElement = button.findElement(By.xpath(".//ancestor::div[@data-asin]//span[@class='a-price']"));
                float price = transformStringToFloat(priceElement.getText());
                productPriceMap.put(button, price);
            } catch (NoSuchElementException e) {
                productPriceMap.put(button, 0f);
                logger.error(e.getMessage());
            }
        }

        Optional<Map.Entry<WebElement, Float>> minPriceEntry = productPriceMap.entrySet().stream()
                .min(Map.Entry.comparingByValue());

        minPriceEntry.ifPresent(entry -> {
            WebElement addToCartButton = entry.getKey().findElement(By.xpath(".//ancestor::div[@data-asin]//button[@class='a-button-text']"));

            addToCartButton.click();
        });
        return minPriceEntry.get().getValue();
    }

    public void clickCartButton() {
        cart.click();
    }

    private float transformStringToFloat(String input) {
        String cleanedInput = input.replace("$", "").replace("\n", ".");

        if (!cleanedInput.isEmpty()) {
            try {
                return Float.parseFloat(cleanedInput);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid format for input string: " + input, e);
            }
        }
        return 0;
    }
}
