package ca.carleton.AmazinBookStore.jbehave;

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
public class RegisterUserTest {
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
  public void registerUserTest() {
    // Test name: register_user
    // Step # | name | target | value
    // 1 | open | / | 
    driver.get("http://localhost:8080/");
    // 2 | setWindowSize | 1936x1056 | 
    driver.manage().window().setSize(new Dimension(1936, 1056));
    // 3 | click | id=loginButton | 
    driver.findElement(By.id("loginButton")).click();
    // 4 | click | linkText=Register | 
    driver.findElement(By.linkText("Register")).click();
    // 5 | click | id=register-first-name | 
    driver.findElement(By.id("register-first-name")).click();
    // 6 | type | id=register-first-name | first_name
    driver.findElement(By.id("register-first-name")).sendKeys("first_name");
    // 7 | type | id=register-last-name | last_name
    driver.findElement(By.id("register-last-name")).sendKeys("last_name");
    // 8 | click | id=register-email | 
    driver.findElement(By.id("register-email")).click();
    // 9 | type | id=register-email | test@gmail.com
    driver.findElement(By.id("register-email")).sendKeys("test@gmail.com");
    // 10 | type | id=register-password | Test123
    driver.findElement(By.id("register-password")).sendKeys("Test123");
    // 11 | click | css=.btn:nth-child(5) | 
    driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
    // 12 | click | linkText=Login | 
    driver.findElement(By.linkText("Login")).click();
    // 13 | click | id=login-email | 
    driver.findElement(By.id("login-email")).click();
  }
}
