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

    public boolean isLoginPageDisplayed() {
        try {
            WebElement[] loginPageElements = {loginPage.getLoginPageLogoElement(), loginPage.getWelcomeBackHeaderElement(), loginPage.getLoginWithMobileNumberTextElement(), loginPage.getMobileNumberInputElement(), loginPage.getSendOtpButtonElement()};
            for (WebElement element : loginPageElements) {
                if (!isActuallyDisplayed(element)) {
                    logger.warning(() -> "Login page element is not displayed: " + element);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.warning(() -> "Login page element is not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean validateLoginPageTexts() {
        try {
            String expectedWelcomeBackText = "Welcome Back!";
            String expectedLoginWithMobileNumberText = "Login with your mobile number";

            String actualWelcomeBackText = loginPage.getWelcomeBackHeaderElement().getText();
            String actualLoginWithMobileNumberText = loginPage.getLoginWithMobileNumberTextElement().getText();

            if (!expectedWelcomeBackText.equals(actualWelcomeBackText)) {
                logger.warning(() -> "Expected Welcome Back text: " + expectedWelcomeBackText + ", but found: " + actualWelcomeBackText);
                return false;
            }

            if (!expectedLoginWithMobileNumberText.equals(actualLoginWithMobileNumberText)) {
                logger.warning(() -> "Expected Login with Mobile Number text: " + expectedLoginWithMobileNumberText + ", but found: " + actualLoginWithMobileNumberText);
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.warning(() -> "Error while validating login page texts: " + e.getMessage());
            return false;
        }
    }

    public boolean enterMobileNumber(String mobileNumber) {
        try {
            loginPage.getMobileNumberInputElement().click();
            loginPage.getMobileNumberInputElement().clear();
            loginPage.getMobileNumberInputElement().sendKeys(mobileNumber);
            Thread.sleep(3000); // Wait for 1 second to ensure the input is registered
            loginPage.getSendOtpButtonElement().click();
            return true;

        } catch (Exception e) {
            logger.warning(() -> "Failed to enter mobile number: " + e.getMessage());
            return false;
        }
    }

    public boolean isEnterOtpPageDisplayed() {
        try {
            WebElement[] enterOtpPageElements = {loginPage.getMobileNumberTextElement(), loginPage.getOtpInputElement(), loginPage.getVerifyAndLoginButtonElement()};
            for (WebElement element : enterOtpPageElements) {
                if (!isActuallyDisplayed(element)) {
                    logger.warning(() -> "Enter OTP page element is not displayed: " + element);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.warning(() -> "Enter OTP page element is not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean enterOtp(String otp) {
        try {
            loginPage.getOtpInputElement().click();
            loginPage.getOtpInputElement().clear();
            loginPage.getOtpInputElement().sendKeys(otp);
            Thread.sleep(1000); // Wait for 1 second to ensure the input is registered
            loginPage.getVerifyAndLoginButtonElement().click();
            return true;
        } catch (Exception e) {
            logger.warning(() -> "Failed to enter OTP: " + e.getMessage());
            return false;
        }
    }

}
