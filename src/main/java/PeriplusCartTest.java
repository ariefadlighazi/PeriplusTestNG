import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class PeriplusCartTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @Test
    public void testCartFunctionality() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // 1. Navigate to Periplus
        driver.get("https://periplus.com/");

        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.name("email")).sendKeys("ariefadlighazir@gmail.com");
        driver.findElement(By.name("password")).sendKeys("testc4se");
        driver.findElement(By.id("button-login")).click();

        // 3. Find a product
        WebElement search = driver.findElement(By.id("filter_name_desktop"));
        search.sendKeys("Software Testing");
        search.submit();

        // 4. Add to Cart

        // Wait for the preloader to disappear first
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-img a"))).click();
        // Wait for the preloader to disappear first
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-add-to-cart"))).click();
        // 5. Verify the product is in the cart
        // We check if the cart icon text changes from "0" to "1"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-modal-close"))).click();
        WebElement cartTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_total")));
        String cartText = cartTotal.getText();

        Assert.assertTrue(cartText.contains("1"), "Verification Failed: Item not added to cart.");
        System.out.println("Test Passed: Product successfully added to cart.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
