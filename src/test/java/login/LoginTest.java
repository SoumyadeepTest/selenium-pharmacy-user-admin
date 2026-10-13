package login;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import base.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        System.out.println("Login Test");
        WebDriver driver = getDriver();
        enterApplication(driver);
        // Add your login test steps here
    }

}
