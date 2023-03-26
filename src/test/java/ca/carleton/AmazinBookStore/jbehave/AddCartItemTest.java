// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class AddCartItemTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void addCartItem() {
    // Test name: Add_CartItem
    // Step # | name | target | value
    // 1 | open | / | 
    driver.get("http://localhost:8080/");
    // 2 | setWindowSize | 1552x832 | 
    driver.manage().window().setSize(new Dimension(1552, 832));
    // 3 | click | linkText=View Listing | 
    driver.findElement(By.linkText("View Listing")).click();
    // 4 | click | id=add-cart-btn | 
    driver.findElement(By.id("add-cart-btn")).click();
    // 5 | click | linkText=Shopping Cart | 
    driver.findElement(By.linkText("Shopping Cart")).click();
    // 6 | click | css=.btn:nth-child(2) | 
    driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
    // 7 | click | css=td:nth-child(1) | 
    driver.findElement(By.cssSelector("td:nth-child(1)")).click();
  }
}
