package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage{

    private static final Logger logger = LogManager.getLogger(CartPage.class);

    @FindBy(id = "sc-subtotal-label-activecart")
    private WebElement subtotalLabel;

    @FindBy(id = "sc-subtotal-amount-activecart")
    private WebElement subtotalAmount;

    @FindBy(xpath = "//div[@data-name='Active Items']")
    private WebElement cartItems;

    @FindBy(id = "sc-buy-box-ptc-button")
    private WebElement checkout;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getSubtotalLabel() {
        return subtotalLabel.getText().replace("Subtotal (", "")
                .replace(" item):", "")
                .replace(" items):", "");
    }

    public String getSubtotalAmount() {
        return subtotalAmount.getText().replace("$", "").trim();
    }

    public void clickDeleteButton(WebDriver driver) {
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector("input[value='Delete']"));

        if (deleteButtons.isEmpty()) {
            logger.info("No items to delete.");
            return;
        }

        for (WebElement deleteButton : deleteButtons) {
            try {
                deleteButton.click();
                driver.navigate().refresh();
                clickDeleteButton(driver);
            } catch (StaleElementReferenceException e) {
                System.out.println("Element is stale. Retrying...");
                clickDeleteButton(driver);
            }
        }
    }

    public void clickCheckoutButton() {
        checkout.click();
    }
}
