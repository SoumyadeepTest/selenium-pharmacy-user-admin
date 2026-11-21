package login;

import java.io.IOException;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import controller.LoginController;
import utils.ExcelUtils;

public class ExcelLoginTest extends BaseTest {

    private static final Logger logger
            = Logger.getLogger(ExcelLoginTest.class.getName());

    private static final String EXCEL_PATH
            = "src/test/resources/testdata/LoginTestData.xlsx";

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {

        return ExcelUtils.readExcelData(
                EXCEL_PATH,
                "LoginData"
        );
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String mobileNumber, String otp) {

        logger.info("Login test started for mobile: " + mobileNumber);

        WebDriver driver = getDriver();

        enterUserApp(driver);

        LoginController loginController
                = new LoginController(driver);

        Assert.assertTrue(
                loginController.isHeaderSectionDisplayed(),
                "Header section is not displayed"
        );

        Assert.assertTrue(
                loginController.gotoLoginPage(),
                "Unable to navigate to login page"
        );

        Assert.assertTrue(
                loginController.isLoginPageDisplayed(),
                "Login page is not displayed"
        );

        Assert.assertTrue(
                loginController.validateLoginPageTexts(),
                "Login page text validation failed"
        );

        Assert.assertTrue(
                loginController.enterMobileNumber(mobileNumber),
                "Unable to enter mobile number"
        );

        Assert.assertTrue(
                loginController.isEnterOtpPageDisplayed(),
                "OTP page is not displayed"
        );

        Assert.assertTrue(
                loginController.enterOtp(otp),
                "Unable to enter OTP"
        );

        Assert.assertTrue(
                loginController.isLoginSuccessful(),
                "Login failed for mobile: " + mobileNumber
        );

        logger.info("Login test completed for mobile: " + mobileNumber);
    }
}
