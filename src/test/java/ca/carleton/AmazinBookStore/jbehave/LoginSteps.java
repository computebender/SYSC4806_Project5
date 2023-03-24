package ca.carleton.AmazinBookStore.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
@SpringBootApplication
public class LoginSteps {


    @Given("I am on the login page")
    public void goToLoginPage() {
        System.out.println("Going to login page?");
    }

    @When("I enter valid credentials")
    public void enterValidCredentials() {
//        WebElement usernameField = driver.findElement(By.id("username"));
//        usernameField.sendKeys("myusername");
//        WebElement passwordField = driver.findElement(By.id("password"));
//        passwordField.sendKeys("mypassword");
    }

    @When("click the login button")
    public void clickLoginButton() {
//        WebElement loginButton = driver.findElement(By.id("login-button"));
//        loginButton.click();
    }

    @Then("I should be redirected to the home page")
    public void verifyHomePage() {
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.urlToBe("https://example.com/home"));
//        String actualTitle = driver.getTitle();
//        assertEquals("Home Page Title", actualTitle);
    }

    @Then("I should see a welcome message")
    public void verifyWelcomeMessage() {
//        WebElement welcomeMessage = driver.findElement(By.id("welcome-message"));
//        String actualMessage = welcomeMessage.getText();
//        assertEquals("Welcome, myusername!", actualMessage);
//        driver.quit();
        System.out.println("Welcome msg");
    }

    @Then("I should see an error message on the login page")
    public void verifyErrorMessage() {
//        WebElement errorMessage = driver.findElement(By.id("error-message"));
//        String actualMessage = errorMessage.getText();
//        assertEquals("Invalid username or password. Please try again.", actualMessage);
//        driver.quit();
    }
}