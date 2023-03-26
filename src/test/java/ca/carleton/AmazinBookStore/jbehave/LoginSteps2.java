package ca.carleton.AmazinBookStore.jbehave;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootApplication
public class LoginSteps2 extends Steps {

    private WebDriver driver;
    private ConfigurableApplicationContext context;
    private LocalStorage local;
    private String user_email;
    private String user_password;

    @BeforeScenario
    public void beforeScenario() {
        // start the Spring Boot application
        context = SpringApplication.run(AmazinBookStoreApplication.class);
        System.out.println("LoginSteps2 setup execution...");
        System.setProperty("webdriver.chrome.driver","C://Users//Alex//Desktop//chromedriver_win32//chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        user_email = "test@gmail.com";
        // 9 | type | id=register-email | test@gmail.com
        driver.findElement(By.id("register-email")).sendKeys(user_email);
        user_password = "Test123";
        // 10 | type | id=register-password | Test123
        driver.findElement(By.id("register-password")).sendKeys(user_password);
        // 11 | click | css=.btn:nth-child(5) |
        driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
        // 12 | click | linkText=Login |
        driver.findElement(By.linkText("Login")).click();
        // 13 | click | id=login-email |
        driver.findElement(By.id("login-email")).click();
    }

    @Given("I am on the login page")
    public void givenTheUserIsOnTheLoginPage() {
        System.out.println("LoginSteps2 - I am on the login page");
        // 1 | open | / |  |
        driver.get("http://localhost:8080/");
        // 2 | setWindowSize | 1920x1040 |  |
        driver.manage().window().setSize(new Dimension(1920, 1040));
        // 3 | click | id=loginButton |  |
        driver.findElement(By.id("loginButton")).click();
    }
    @When("I enter valid credentials")
    public void enterValidCredentials() {
        System.out.println("Scenario 1 - I enter valid credentials");
        String username = "john.doe@example.com";
        String pass = "password1";
        // 4 | click | id=login-email |  |
        driver.findElement(By.id("login-email")).click();
        // 5 | type | id=login-email | test@gmail.com |
        driver.findElement(By.id("login-email")).sendKeys(username); //temp

        // 6 | type | id=login-password | Test123 |
        driver.findElement(By.id("login-password")).sendKeys(pass);

        //Verify credentials are valid
//        assertEquals(user_email,username);
//        assertEquals(user_password,pass);
    }

    @When("I enter invalid credentials")
    public void enterInvalidCredentials() {
        System.out.println("Scenario 2 - I enter invalid credentials");
        String username = "incorrect@gmail.ca";
        String pass = "fake_password";
        // 4 | click | id=login-email |  |
        driver.findElement(By.id("login-email")).click();
        // 5 | type | id=login-email | test@gmail.com |
        driver.findElement(By.id("login-email")).sendKeys(username);
        // 6 | type | id=login-password | Test123 |
        driver.findElement(By.id("login-password")).sendKeys(pass);

        //Make sure credentials are invalid
        assertNotEquals(user_email,username);
        assertNotEquals(user_password,pass);
    }

    @When("click the login button")
    public void clickLoginButton() {
        System.out.println("LoginSteps2 - click login button...");
        // 7 | click | css=.btn:nth-child(3) |  |
        driver.findElement(By.id("btn-login")).click();
    }

    @Then("I should be redirected to the home page")
    public void thenTheUserIsLoggedIn() {
        System.out.println("Scenario 1: Redirected to home page...");
        // Check that the user is redirected to the home page
        By element = By.id("home");
        List<WebElement> elements = driver.findElements(element);
        assertFalse(elements.isEmpty());
    }
    @Then("I should see a welcome message")
    public void verifyWelcomeMessage() {
        System.out.println("Scenario 1: Welcome message...");
        By element = By.id("home");
        List<WebElement> elements = driver.findElements(element);
        assertFalse(elements.isEmpty());
    }

    @Then("I should see an error message on the login page")
    public void verifyErrorMessage() {
        System.out.println("Scenario 2: Error message ...");
        By element = By.id("home");
        List<WebElement> elements = driver.findElements(element);
        assertTrue(elements.isEmpty());
    }

    @When("I click add to cart on a book")
    public void clickAddToCart() {
        System.out.println("LoginSteps2 - Add book to shopping cart...");
        // 4 | click | id=add-cart-btn |
        driver.findElement(By.id("add-cart-btn")).click();
    }

    @When("I navigate to the shopping cart")
    public void clickShoppingCart() {
        System.out.println("LoginSteps2 - I click shopping cart page...");
        // 5 | click | linkText=Shopping Cart |
        driver.findElement(By.linkText("Shopping Cart")).click();

        int value =1;
        assertTrue(driver.findElement(By.id("shopping-cart-table")).isDisplayed());
    }
    @Then("I should see a book added in the shopping cart")
    public void verifyShoppingCart() {
        System.out.println("LoginSteps2 - I verify item in shopping cart...");
        //Verify contents of item in shopping cart
        // 7 | click | css=td:nth-child(1) | Find the title of the book
        driver.findElement(By.cssSelector("td:nth-child(1)")).click();
        System.out.println(driver.findElement(By.cssSelector("td:nth-child(1)")).getText());
        assertEquals("Murder on the Orient Express",driver.findElement(By.cssSelector("td:nth-child(1)")).getText());
    }
    @AfterScenario
    public void afterScenario() {
        // stop the Spring Boot application
        System.out.println("Ending test...");
        driver.manage().deleteAllCookies();
        local = ((WebStorage) driver).getLocalStorage();
        local.clear();
        driver.quit();
        context.stop();
    }

}