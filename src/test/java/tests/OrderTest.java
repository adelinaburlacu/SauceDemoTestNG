package tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.*;

public class OrderTest extends BaseTest {

    @Test(description = "Verify that adding elements to cart work as expected.", groups = {"with_login"})
    public void testAddToCartFunctionality() {
        WebElement shoppingCart = driver.findElement(By.cssSelector("a.shopping_cart_link"));

        List<WebElement> inventoryItems = driver.findElements(By.cssSelector(".inventory_item"));
        WebElement firstItem = inventoryItems.get(0);
        String firstItemTitle = firstItem.findElement(By.cssSelector(".inventory_item_name")).getText();
        String firstItemPrice = firstItem.findElement(By.className("inventory_item_price")).getText();
        WebElement firtsItemButton = firstItem.findElement(By.cssSelector(".btn_inventory"));

        assertEquals(firtsItemButton.getText().toLowerCase(), "add to cart",
                "Add to cart button text is incorrect before adding an item to cart.");
        assertFalse(isElementPresent(By.cssSelector("span.shopping_cart_badge")),
                "Shopping cart bagde is present before adding elements to cart.");
        firtsItemButton.click();
        assertEquals(firstItem.findElement(By.cssSelector(".btn_inventory")).getText().toLowerCase(), "remove",
                "Add to cart button text is incorrect before adding an item to cart.");
        assertTrue(isElementPresent(By.cssSelector("span.shopping_cart_badge")),
                "Shopping cart bagde is not present before adding elements to cart.");
        assertEquals(driver.findElement(By.cssSelector("span.shopping_cart_badge")).getText(), "1",
                "Number of elements on shopping cart badge is incorrect");

        shoppingCart.click();
        assertTrue(driver.getCurrentUrl().contains("cart.html"), "User is not on cart page after navigating to it.");
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(cartItems.size(), 1, "Number of items in cart is incorrect.");
        String cartItemName = cartItems.get(0).findElement(By.cssSelector(".inventory_item_name")).getText();
        assertEquals(cartItemName, firstItemTitle, "Wrong item added to cart.");
        String cartItemPrice = cartItems.get(0).findElement(By.className("inventory_item_price")).getText();
        assertEquals(cartItemPrice, firstItemPrice, "Wrong item added to cart.");

        assertTrue(isElementPresent(cartItems.get(0), By.className("cart_button")), "Remove button on cart is present.");
        assertTrue(isElementPresent(By.id("continue-shopping")), "Continue shopping button is not present.");
        assertTrue(isElementPresent(By.id("checkout")), "Checkout button is not present");

    }


}
