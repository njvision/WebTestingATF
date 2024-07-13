package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage{

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

    public void clickDeleteButton() {
        List<WebElement> items = cartItems.findElements(By.xpath("//span[@data-feature-id='delete']"));
        items.forEach(WebElement::click);
    }

    public void clickCheckoutButton() {
        checkout.click();
    }
}
