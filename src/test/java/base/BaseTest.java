package base;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.qameta.allure.Allure;
import listeners.TestListener;

@Listeners(TestListener.class)
// sdfdsf
public class BaseTest {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @SuppressWarnings("null")
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "headless", "screen" })

    public void setDriver(@Optional("chrome") String browser, @Optional("false") boolean headless,
            @Optional("DESKTOP") String screen) {
        System.out.println("Creating driver from BaseTest3");
        // System.out.println(
        // "Browser = " + browser +
        // ", Headless = " + headless +
        // ", Screen = " + screen);
        BrowserConfig config = BrowserConfig.builder()
                .browser(browser)
                .headless(headless)
                .screen(ScreenSize.valueOf(screen))
                .build();

        driver.set(DriverFactory.create(config));

        System.out.println("Actual Window Size: "
                + getDriver().manage().window().getSize());
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

    }

    String landingUrl = "https://aparna-three.witkid.in/";
    String adminUrl = "https://clarivanceone.witkid.in/";

    @SuppressWarnings("null")
    public void enterApplication(WebDriver driver) {
        driver.get(landingUrl);
        // Wait for page to fully load
        try {
            Thread.sleep(2000); // Allow React/Angular to initialize
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @SuppressWarnings("null")
    public void enterAdminApplication(WebDriver driver) {
        driver.get(adminUrl);
        // Wait for page to fully load
        try {
            Thread.sleep(2000); // Allow React/Angular to initialize
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        WebDriver currentDriver = getDriver();

        try {

            if (currentDriver != null) {

                if (result.getStatus() == ITestResult.SUCCESS) {

                    takeScreenshot(
                            currentDriver,
                            "SUCCESS - " + result.getName());

                } else if (result.getStatus() == ITestResult.FAILURE) {

                    takeScreenshot(
                            currentDriver,
                            "FAILURE - " + result.getName());

                    Allure.addAttachment(
                            "Failure Page Source",
                            "text/html",
                            currentDriver.getPageSource(),
                            ".html");

                } else if (result.getStatus() == ITestResult.SKIP) {

                    takeScreenshot(
                            currentDriver,
                            "SKIPPED - " + result.getName());
                }
            }

        } finally {

            if (currentDriver != null) {

                System.out.println("Closing driver...");
                currentDriver.quit();
            }

            driver.remove();
        }
    }

    private void takeScreenshot(
            WebDriver driver,
            String testName) {

        try {

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(
                            OutputType.BYTES);

            String status;

            // You can pass result instead if you want
            status = "Screenshot - ";

            Allure.addAttachment(
                    status + testName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png");

            System.out.println(
                    "Screenshot successfully attached to Allure: "
                            + testName);

        } catch (Exception e) {

            System.out.println(
                    "Screenshot FAILED: "
                            + e.getMessage());
        }
    }

}
