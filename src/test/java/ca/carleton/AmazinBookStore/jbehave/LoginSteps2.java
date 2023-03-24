package ca.carleton.AmazinBookStore.jbehave;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LoginSteps2 extends Steps {

    private WebDriver driver;
    private ConfigurableApplicationContext context;

    @BeforeScenario
    public void beforeScenario() {
        // start the Spring Boot application
        context = SpringApplication.run(AmazinBookStoreApplication.class);
        System.out.println("LoginSteps2 setup execution...");
        System.setProperty("webdriver.chrome.driver", "C://Users//Alex//Desktop//chromedriver_win32//chromedriver.exe");
        System.out.println("LoginSteps2 test1");
        WebDriver test = new ChromeDriver();
        System.out.println("LoginSteps2 test1.5");
        try{
            driver = new ChromeDriver();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("LoginSteps2 test2");
        System.out.println("LoginSteps2 setup execution completed.");
    }

    @AfterScenario
    public void afterScenario() {
        // stop the Spring Boot application
        System.out.println("Ending test...");
        context.stop();
    }

    @Given("the user is on the login page")
    public void givenTheUserIsOnTheLoginPage() {
        System.out.println("LoginSteps2 execution...");
        driver.get("http://localhost:8080/login");
    }

    @When("the user enters valid credentials")
    public void whenTheUserEntersValidCredentials() {
        driver.findElement(By.id("username")).sendKeys("myusername");
        driver.findElement(By.id("password")).sendKeys("mypassword");
        driver.findElement(By.tagName("form")).submit();
    }

    @Then("the user is logged in")
    public void thenTheUserIsLoggedIn() {
        // Check that the user is redirected to the home page
        driver.findElement(By.id("home")).isDisplayed();
    }

}