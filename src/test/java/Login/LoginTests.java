package Login;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class LoginTests {

    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS)); // 10 seconds wait

    @BeforeAll
    static void setup() {

        WebDriverManager.chromedriver().setup();
    }

    public String performLogin(String username, String password) {
        // Navigate to the login page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/");

        // Find the username and password fields and input the details
        WebElement usernameField = driver.findElement(By.id("email"));
        usernameField.sendKeys(username);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);

        // Find the login button and click it
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        loginButton.click();

        // Wait for the toast notification to appear and then verify its text
        WebElement toastNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast-container--top-right")));
        return toastNotification.getText();
    }

    @Test
    public void LoginTest_SuccessfulLogin() {
        String actualToastMessage = performLogin("shreyans.s@cyraacs.com", "942sS$E^1£=C");
        String expectedToastMessage = "Login Successful";
        Assertions.assertEquals(expectedToastMessage.toLowerCase(), actualToastMessage.toLowerCase(), "Toast message did not match");
        driver.quit();
    }

    @Test
    public void LoginTest_InvalidCredential() {
        String actualToastMessage = performLogin("shreyans.s@cyraacs.com", "randomPassword");
        String expectedToastMessage = "Invalid Credentials";
        Assertions.assertEquals(expectedToastMessage.toLowerCase(), actualToastMessage.toLowerCase(), "Toast message did not match");
        driver.quit();
    }

    public void createIssueFrameworkDetails() {
        // Go to the create issue framework page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/issue-framework");

        // Add the issue rating details
        WebElement addIssueRatingButton = driver.findElement(By.cssSelector(".col-12:nth-child(1) svg"));
        addIssueRatingButton.click();
        WebElement issueRatingField = driver.findElement(By.xpath("//*[@id=\"issueRating\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(issueRatingField)).click();
        issueRatingField.sendKeys("Good");
        WebElement closureDaysCountField = driver.findElement(By.xpath("//*[@id=\"closureDaysCount\"]"));
        closureDaysCountField.sendKeys("5");
        WebElement descriptionField = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        descriptionField.sendKeys("The value is good");
        WebElement colorHexField = driver.findElement(By.id("colorHex"));
        colorHexField.sendKeys("#f21818");
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        //Waiting for the toast message
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        WebElement toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
        System.out.println(toastMessage.getText());
        Assertions.assertEquals("Issue Rating Created Successfully".trim().toLowerCase(), toastMessage.getText().trim().toLowerCase());
        wait.until(ExpectedConditions.invisibilityOf(toastMessage));


        // Add the type of issue details
        WebElement typeOfIssueButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[4]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[1]/button"));
        wait.until(ExpectedConditions.elementToBeClickable(typeOfIssueButton));
        typeOfIssueButton.click();
        WebElement issueTypeField = driver.findElement(By.xpath("//input[@id='issueType']"));
        wait.until(ExpectedConditions.elementToBeClickable(issueTypeField));
        issueTypeField.click();
        issueTypeField.sendKeys("Critical");
        WebElement descriptionField2 = driver.findElement(By.cssSelector("#issueTypeModal #description"));
        wait.until(ExpectedConditions.elementToBeClickable(descriptionField2)).click();
        descriptionField2.sendKeys("It is extremely critical");
        WebElement submitButton2 = driver.findElement(By.xpath("//*[@id=\"issueTypeModal\"]/div/div/div[3]/div/button"));
        submitButton2.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
        System.out.println(toastMessage.getText());
        Assertions.assertEquals("Issue Type Created Successfully".toLowerCase(), toastMessage.getText().toLowerCase());
        wait.until(ExpectedConditions.invisibilityOf(toastMessage));
    }

    public void deleteIssueFrameworkDetails() {
        // Go to the create issue framework page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/issue-framework");
        //Delete Issue Rating
        WebElement deleteIssueButton = driver.findElement(By.cssSelector(".icon-delete path:nth-child(2)"));
        wait.until(ExpectedConditions.elementToBeClickable(deleteIssueButton));
        deleteIssueButton.click();
        WebElement yesButton = driver.findElement(By.xpath("//button[contains(.,'Yes')]"));
        wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        yesButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        WebElement toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
        System.out.println(toastMessage.getText());
        Assertions.assertEquals("Issue Rating Deleted Successfully".toLowerCase(), toastMessage.getText().toLowerCase());
        wait.until(ExpectedConditions.invisibilityOf(toastMessage));

        //Delete Issue Type
        WebElement deleteIssueTypeButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[4]/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[2]/div[2]/button"));
        deleteIssueTypeButton.click();
        yesButton = driver.findElement(By.xpath("//button[contains(.,'Yes')]"));
        wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        yesButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
        System.out.println(toastMessage.getText());
        Assertions.assertEquals("Issue Type Deleted Successfully".toLowerCase(), toastMessage.getText().toLowerCase());
        wait.until(ExpectedConditions.invisibilityOf(toastMessage));
    }

    public void createIssue() {
        // Go to the create issue framework page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/issue-form/autotestorg/new");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Whatever");
        WebElement descriptionField = driver.findElement(By.id("description"));
        descriptionField.sendKeys("Whatever");

        WebElement typeOfIssueDropdown = driver.findElement(By.id("react-select-2-input"));
        typeOfIssueDropdown.click();
        WebElement firstElement = driver.findElement(By.id("react-select-2-option-0"));
        firstElement.click();

    }

    @Test
    public void CreateIssueDetails() {
        String actualToastMessage = performLogin("shreyans.s@cyraacs.com", "942sS$E^1£=C");
        String expectedToastMessage = "Login Successful";
        Assertions.assertEquals(expectedToastMessage.toLowerCase(), actualToastMessage.toLowerCase(), "Login Unsuccessful");
        createIssue();
//        createIssueFrameworkDetails();
//        deleteIssueFrameworkDetails();
        //TODO: Delete here
        driver.quit();
    }



}
