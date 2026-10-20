package pageutils;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GlobalUtils {

    private static final Logger logger = Logger.getLogger(GlobalUtils.class.getName());

    @SuppressWarnings("null")

    public WebDriver driver;

    public GlobalUtils(WebDriver driver) {
        this.driver = driver;
    }

    public boolean scrollIntoElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element);
            return isActuallyDisplayed(element);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to scroll element into view", e);
            return false;
        }
    }

    public boolean explicitlyCheckVisibility(By locator) {
        try {
            // First check if element exists
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));

            // Then wait for visibility
            WebElement myDynamicElement = (new WebDriverWait(driver, Duration.ofSeconds(30)))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            if (myDynamicElement.isDisplayed()) {
                return true;
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not visible with locator: " + locator, e);
            return false;
        }
        logger.warning("Element not found with locator: " + locator);
        return false;

    }

    public boolean explicitlyCheckVisibility(WebElement element) {
        try {
            WebElement myDynamicElement = (new WebDriverWait(driver, Duration.ofSeconds(30)))
                    .until(ExpectedConditions.visibilityOf(element));
            if (myDynamicElement.isDisplayed()) {
                return true;
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to verify element visibility", e);
            return false;
        }
        logger.warning("Element not found");
        return false;

    }

    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementDisplayed(WebElement ele) {
        if (ele == null) {
            return false;
        }
        return ele.isDisplayed();
    }

    public boolean isActuallyDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return isActuallyDisplayed(element);

        } catch (NoSuchElementException | StaleElementReferenceException e) {

            return false;
        }
    }

    public boolean isActuallyDisplayed(WebElement element) {
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(
                    """
                            const e = arguments[0];
                            const rect = e.getBoundingClientRect();
                            const style = window.getComputedStyle(e);

                            return rect.width > 0
                                    && rect.height > 0
                                    && style.display !== 'none'
                                    && style.visibility !== 'hidden'
                                    && style.opacity !== '0';
                            """,
                    element);

            return Boolean.TRUE.equals(result);

        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean waitForClickable(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not clickable: " + locator, e);
            return false;
        }
    }

    public boolean waitForClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not clickable", e);
            return false;
        }
    }

    public void jsClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public boolean safeClick(WebElement element) {
        try {
            scrollIntoElement(element);
            waitForClickable(element);
            element.click();
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Regular click failed, using JavaScript click", e);
            jsClick(element);
            return false;
        }
    }

    public String getText(WebElement element) {
        String text = element.getText().trim();
        if (text.isEmpty()) {
            text = (String) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].textContent;", element);
            text = text != null ? text.trim() : "";
        }
        return text;
    }

    public boolean isTextVisible(String text) {
        try {
            By locator = By.xpath("//*[contains(text(), '" + text + "')]");
            return explicitlyCheckVisibility(locator);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForInvisibility(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element still visible or wait failed for locator: " + locator, e);
            return false;
        }
    }

    public boolean waitForInvisibility(By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element still visible or wait failed for locator: " + locator, e);
            return false;
        }
    }

    public void waitForPageReadiness() {
        try {
            // Wait for sign-in button to be visible (indicates login UI is ready)
            // Wait for page to fully load using JavaScript
            waitForPageLoad();
            logger.info("Page loading completed; waiting for sign-in button to be visible");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Error waiting for page readiness", e);
        }
    }

    public void waitForPageLoad() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(05))
                    .until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));

            // Additional wait for any async operations (cart sync, user data load)
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
                    .until(webDriver -> {
                        try {
                            // Check if jQuery is loaded and there are no active Ajax requests
                            Boolean jQueryDefined = (Boolean) ((org.openqa.selenium.JavascriptExecutor) webDriver)
                                    .executeScript("return typeof jQuery != 'undefined'");
                            if (jQueryDefined) {
                                Long ajaxActive = (Long) ((org.openqa.selenium.JavascriptExecutor) webDriver)
                                        .executeScript("return jQuery.active");
                                return ajaxActive == 0;
                            }
                            return true;
                        } catch (Exception e) {
                            return true; // If jQuery not available, proceed
                        }
                    });
        } catch (Exception e) {
            logger.log(Level.WARNING, "Page load wait completed with timeout; proceeding", e);
        }
    }
}
