package controller;

import org.openqa.selenium.WebDriver;

import pages.LoginPage;
import pageutils.GlobalUtils;

public class LoginController extends GlobalUtils {

    public LoginPage loginPage;

    public LoginController(WebDriver driver) {
        super(driver);
        loginPage = new LoginPage(driver);
    }

}
