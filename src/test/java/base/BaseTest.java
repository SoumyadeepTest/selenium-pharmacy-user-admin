package base;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @SuppressWarnings("null")
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless", "screen"})

    public void setDriver(@Optional("chrome") String browser, @Optional("false") boolean headless,
            @Optional("DESKTOP") String screen) {
        logger.info("Creating WebDriver");
        BrowserConfig config = BrowserConfig.builder()
                .browser(browser)
                .headless(headless)
                .screen(ScreenSize.valueOf(screen))
                .build();

        driver.set(DriverFactory.create(config));

        logger.info("Actual window size: " + getDriver().manage().window().getSize());
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

    }

    String userUrl = "https://clarivanceone.witkid.in/";
    String adminUrl = "https://aparna-three.witkid.in/";

    @SuppressWarnings("null")
    public boolean enterAdminApplication(WebDriver driver) {
        try {
            driver.get(adminUrl);
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot open admin application page", e);
            return false;
        }
    }

    @SuppressWarnings("null")
    public boolean enterUserApp(WebDriver driver) {
        try {
            driver.get(userUrl);
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot open user application page", e);
            return false;
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

                logger.info("Closing driver");
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

            logger.info("Screenshot successfully attached to Allure: " + testName);

        } catch (Exception e) {

            logger.log(Level.WARNING, "Screenshot failed for test: " + testName, e);
        }
    }

}
