package ca.carleton.AmazinBookStore.jbehave;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class LoginUserValidTest {
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
  public void loginuservalid() {
    // Test name: login_user_valid
    // Step # | name | target | value
    // 1 | open | / | 
    driver.get("http://localhost:8080/");
    // 2 | setWindowSize | 1936x1056 | 
    driver.manage().window().setSize(new Dimension(1936, 1056));
    // 3 | click | id=loginButton | 
    driver.findElement(By.id("loginButton")).click();
    // 4 | click | id=login-email | 
    driver.findElement(By.id("login-email")).click();
    // 5 | type | id=login-email | test@
    driver.findElement(By.id("login-email")).sendKeys("test@");
    // 6 | click | id=login-email | 
    driver.findElement(By.id("login-email")).click();
    // 7 | type | id=login-email | test@gmail.com
    driver.findElement(By.id("login-email")).sendKeys("test@gmail.com");
    // 8 | type | id=login-password | Test123
    driver.findElement(By.id("login-password")).sendKeys("Test123");
    // 9 | click | css=.btn:nth-child(3) | 
    driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
    // 10 | click | css=html | 
    driver.findElement(By.cssSelector("html")).click();
    // 11 | click | css=html | 
    driver.findElement(By.cssSelector("html")).click();
  }
}
