package login;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import controller.LoginController;

public class LoginTest extends BaseTest {

    private static final Logger logger = Logger.getLogger(LoginTest.class.getName());

    @Test
    public void testLogin() {
        logger.info("Login test");
        WebDriver driver = getDriver();
        enterUserApp(driver);
        LoginController loginController = new LoginController(driver);
        Assert.assertTrue(loginController.isHeaderSectionDisplayed());
        Assert.assertTrue(loginController.gotoLoginPage());
        Assert.assertTrue(loginController.isLoginPageDisplayed());
        Assert.assertTrue(loginController.validateLoginPageTexts());
        Assert.assertTrue(loginController.enterMobileNumber("9111111111"));
        Assert.assertTrue(loginController.isEnterOtpPageDisplayed());
        Assert.assertTrue(loginController.enterOtp("123456"));
    }

}
