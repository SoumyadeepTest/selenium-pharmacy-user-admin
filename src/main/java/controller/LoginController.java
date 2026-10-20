package controller;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pages.LoginPage;
import pageutils.GlobalUtils;

public class LoginController extends GlobalUtils {

    public LoginPage loginPage;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController(WebDriver driver) {
        super(driver);
        loginPage = new LoginPage(driver);
    }

    public boolean isHeaderSectionDisplayed() {
        try {
            WebElement[] headerSectionElements = {loginPage.getHeaderSectionElement(), loginPage.getLogoElement(), loginPage.getWishlistIconElement(), loginPage.getCartIconElement(), loginPage.getLoginButtonElement()};
            for (WebElement element : headerSectionElements) {
                if (!isActuallyDisplayed(element)) {
                    logger.warning(() -> "Header section element is not displayed: " + element);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.warning(() -> "Header section element is not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean gotoLoginPage() {
        try {
            safeClick(loginPage.getLoginButtonElement());
            return true;
        } catch (Exception e) {
            logger.warning(() -> "Failed to navigate to login page: " + e.getMessage());
            return false;
        }
    }

}
