package Login;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
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

    private static ExtentReports extent;
    private static ExtentTest test;


    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS)); // 10 seconds wait

    @BeforeAll
    public static void setup() {
        ExtentSparkReporter spark = new ExtentSparkReporter("src/test/java/Spark.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
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
        test = extent.createTest("Login Successful Test");
        String actualToastMessage = performLogin("shreyans.s@cyraacs.com", "942sS$E^1£=C");
        String expectedToastMessage = "Login Successful";
        try {
            Assertions.assertEquals(expectedToastMessage.toLowerCase(), actualToastMessage.toLowerCase(), "Toast message did not match");
            test.pass("Successful Login Done");
        } catch (Exception e) {
            test.fail("Successful Login Failed");

        }
        driver.quit();
    }

    @Test
    public void LoginTest_InvalidCredential() {
        test = extent.createTest("Login Invalid Credential Test");
        String actualToastMessage = performLogin("shreyans.s@cyraacs.com", "randomPassword");
        String expectedToastMessage = "Invalid Credentials";
        try {
            Assertions.assertEquals(expectedToastMessage.toLowerCase(), actualToastMessage.toLowerCase(), "Toast message did not match");
            test.pass("Login with invalid credentials passed");
        } catch (Exception e) {
            test.fail("Login with invalid credentials failed");
        }
        driver.quit();
    }

    public void createIssueFrameworkDetails() {
        test = extent.createTest("Issue Framework Creation");
        // Go to the create issue framework page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/issue-framework");

        try {
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
            test.pass("Issue Rating created Successfully");
        } catch (Exception e) {
            test.fail("Issue Rating creation failed with message: " + e.getMessage());
            return;
        }
        try {
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
            WebElement toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
            System.out.println(toastMessage.getText());
            Assertions.assertEquals("Issue Type Created Successfully".toLowerCase(), toastMessage.getText().toLowerCase());
            wait.until(ExpectedConditions.invisibilityOf(toastMessage));
            test.pass("Issue Type Creation Passed");
        } catch (Exception e) {
            test.fail("Issue Type Creation failed with the message: " + e.getMessage());
        }
    }

    public void deleteIssueFrameworkDetails() {
        // Go to the create issue framework page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/issue-framework");
        //Delete Issue Rating
        WebElement deleteIssueButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[4]/div[1]/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/button"));
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

        //Type of Issue
        WebElement dropdownElement = driver.findElement(By.id("react-select-2-input"));
        dropdownElement.click();
        WebElement firstElement = driver.findElement(By.id("react-select-2-option-0"));
        firstElement.click();

        //Rating
        dropdownElement = driver.findElement(By.id("react-select-3-input"));
        dropdownElement.click();
        firstElement = driver.findElement(By.id("react-select-3-option-0"));
        firstElement.click();

        //Project
//        dropdownElement = driver.findElement(By.id("react-select-4-input"));
//        dropdownElement.click();
//        firstElement = driver.findElement(By.id("react-select-4-option-0"));
//        firstElement.click();

        //Issue Owner
        dropdownElement = driver.findElement(By.id("react-select-5-input"));
        dropdownElement.click();
        firstElement = driver.findElement(By.id("react-select-5-option-0"));
        firstElement.click();

        //Issue Approver
        dropdownElement = driver.findElement(By.id("react-select-6-input"));
        dropdownElement.click();
        firstElement = driver.findElement(By.id("react-select-6-option-1"));
        firstElement.click();

        // Issue Reference
//        dropdownElement = driver.findElement(By.id("react-select-7-input"));
//        dropdownElement.click();
//        firstElement = driver.findElement(By.id("react-select-7-option-0"));
//        firstElement.click();

        WebElement recommendationField = driver.findElement(By.id("recommendation"));
        recommendationField.sendKeys("Could be improved");

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(.,'Submit')]"));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        /*
         * Conditions:
         * Issue Owner and Approver Can Not Be Same
         * Issue Created Successfully
         * */

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        WebElement toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
        System.out.println(toastMessage.getText());
        Assertions.assertEquals("Issue Created Successfully".trim().toLowerCase(), toastMessage.getText().trim().toLowerCase());
        wait.until(ExpectedConditions.invisibilityOf(toastMessage));
    }

    public void deleteIssue() {
        //Issues page
        driver.get("https://autotestorg.comp-dev.cyraacs.net/issues/autotestorg");
        try {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("icon-delete")));
        WebElement deleteButton = driver.findElement(By.className("icon-delete"));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("modal-dialog")));
        WebElement yesButton = driver.findElement(By.xpath("//button[contains(.,'Yes')]"));
        wait.until(ExpectedConditions.elementToBeClickable(yesButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        WebElement toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body > div:nth-child(2)"));
        System.out.println(toastMessage.getText());
        Assertions.assertEquals("Issue Deleted Successfully".trim().toLowerCase(), toastMessage.getText().trim().toLowerCase());
        wait.until(ExpectedConditions.invisibilityOf(toastMessage));
        test.pass("Issue Deletion Passed");
        } catch (Exception e) {
            test.fail("Issue Deletion Failed With Message: " + e.getMessage());
        }
    }

    @Test
    public void CreateIssueDetails() {
        test = extent.createTest("Create");
        String actualToastMessage = performLogin("shreyans.s@cyraacs.com", "942sS$E^1£=C");
        String expectedToastMessage = "Login Successful";
        Assertions.assertEquals(expectedToastMessage.toLowerCase(), actualToastMessage.toLowerCase(), "Login Unsuccessful");
        createIssueFrameworkDetails();
        createIssue();
        deleteIssue();
        deleteIssueFrameworkDetails();
        //TODO: Delete here
        driver.quit();
    }

    @AfterAll
    public static void tearDown() {
        extent.flush();
    }


}
