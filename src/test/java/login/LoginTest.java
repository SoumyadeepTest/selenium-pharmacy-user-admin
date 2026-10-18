package login;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
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
        boolean isHeaderDisplayed = loginController.isHeaderSectionDisplayed();
        logger.info(() -> "Is header section displayed: " + isHeaderDisplayed);
    }

}
