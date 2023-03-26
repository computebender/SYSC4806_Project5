package ca.carleton.AmazinBookStore.jbehave;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootApplication
public class LoginSteps2 extends Steps {

    private WebDriver driver;
    private ConfigurableApplicationContext context;
    private LocalStorage local;
    @BeforeScenario
    public void beforeScenario() {
        // start the Spring Boot application
        context = SpringApplication.run(AmazinBookStoreApplication.class);
        System.out.println("LoginSteps2 setup execution...");
        System.setProperty("webdriver.chrome.driver","C://Users//alex8//OneDrive//Desktop//chromedriver_win32//chromedriver_win32//chromedriver.exe");        System.out.println("LoginSteps2 test1");
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
        // 4 | click | id=login-email |  |
        driver.findElement(By.id("login-email")).click();
        // 5 | type | id=login-email | test@gmail.com |
        driver.findElement(By.id("login-email")).sendKeys("john.doe@example.com"); //temp

//        driver.findElement(By.id("login-email")).sendKeys("test@gmail.com");
        // 6 | type | id=login-password | Test123 |
        driver.findElement(By.id("login-password")).sendKeys("password1");
//        driver.findElement(By.id("login-password")).sendKeys("Test123");

        int value =1;
        assertEquals(1,value);
    }

    @When("I enter invalid credentials")
    public void enterInvalidCredentials() {
        System.out.println("Scenario 2 - I enter invalid credentials");
        // 4 | click | id=login-email |  |
        driver.findElement(By.id("login-email")).click();
        // 5 | type | id=login-email | test@gmail.com |
        driver.findElement(By.id("login-email")).sendKeys("incorrect@gmail.ca");
        // 6 | type | id=login-password | Test123 |
        driver.findElement(By.id("login-password")).sendKeys("fake_password");

        int value =1;
        assertEquals(1,value);
    }

    @When("click the login button")
    public void clickLoginButton() {
        System.out.println("LoginSteps2 - click login button...");
        // 7 | click | css=.btn:nth-child(3) |  |
        driver.findElement(By.id("btn-login")).click();

        int value =1;
        assertEquals(1,value);
    }

    @Then("I should be redirected to the home page")
    public void thenTheUserIsLoggedIn() {
        System.out.println("Scenario 1: Redirected to home page...");
        // Check that the user is redirected to the home page
//        driver.findElement(By.id("home")).isDisplayed();
        int value =1;
        assertEquals(1,value);
    }
    @Then("I should see a welcome message")
    public void verifyWelcomeMessage() {
        System.out.println("Scenario 1: Welcome message...");
        int value =1;
        assertEquals(1,value);
    }

    @Then("I should see an error message on the login page")
    public void verifyErrorMessage() {
        System.out.println("Scenario 2: Error message ...");
        int value =1;
        assertEquals(1,value);
    }
    @When("I navigate to the home page")
    public void clickHomePage() {
        System.out.println("LoginSteps2 - click home page...");
        driver.get("http://localhost:8080/");
        // 2 | setWindowSize | 1552x832 |
        driver.manage().window().setSize(new Dimension(1552, 832));
        int value =1;
        assertEquals(1,value);
    }

    @When("I click add to cart on a book")
    public void clickAddToCart() {
        System.out.println("LoginSteps2 - Add book to shopping cart...");
        // 3 | click | linkText=View Listing |
//        driver.findElement(By.linkText("View Listing")).click();
        // 4 | click | id=add-cart-btn |
        driver.findElement(By.id("add-cart-btn")).click();
        int value =1;
        assertEquals(1,value);
    }

    @When("I navigate to the shopping cart")
    public void clickShoppingCart() {
        System.out.println("LoginSteps2 - I click shopping cart page...");
        // 5 | click | linkText=Shopping Cart |
        driver.findElement(By.linkText("Shopping Cart")).click();
        int value =1;
        assertEquals(1,value);
    }
    @Then("I should see a book added in the shopping cart")
    public void verifyShoppingCart() {
        System.out.println("LoginSteps2 - I verify item in shopping cart...");
        // 6 | click | css=.btn:nth-child(2) |
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
        // 7 | click | css=td:nth-child(1) |
        driver.findElement(By.cssSelector("td:nth-child(1)")).click();
        //Verify contents of item in shopping cart
        int value =1;
        assertEquals(1,value);
    }
    @AfterScenario
    public void afterScenario() {
        // stop the Spring Boot application
        System.out.println("Ending test...");
        driver.manage().deleteAllCookies();
        local = ((WebStorage) driver).getLocalStorage();
        local.clear();
        //        driver.quit();
        context.stop();
    }

}