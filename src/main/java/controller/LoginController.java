package controller;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

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
            return explicitlyCheckVisibility(loginPage.getHeaderSectionElement());
        } catch (Exception e) {
            logger.warning(() -> "Header section element is not displayed: " + e.getMessage());
            return false;
        }
    }

}
