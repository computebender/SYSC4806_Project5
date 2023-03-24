package ca.carleton.AmazinBookStore.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertEquals;

public class LoginSteps {

    @Given("I am on the login page")
    public void goToLoginPage() {
//        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
//        driver = new ChromeDriver();
//        driver.get("https://example.com/login");
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
    }

    @Then("I should see an error message on the login page")
    public void verifyErrorMessage() {
//        WebElement errorMessage = driver.findElement(By.id("error-message"));
//        String actualMessage = errorMessage.getText();
//        assertEquals("Invalid username or password. Please try again.", actualMessage);
//        driver.quit();
    }
}