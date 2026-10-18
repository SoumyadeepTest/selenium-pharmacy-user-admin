package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    // for header section
    @FindBy(css = "header[data-name='header']")
    private WebElement headerSectionElement;

    @FindBy(xpath = "//img[@class='object-contain w-full h-full pointer-events-none']")
    private WebElement logoElement;

    @FindBy(css = "button[class='text-gray-600 hover:text-(--primary) transition-colors relative cursor-pointer']")
    private WebElement wishlistIconElement;

    @FindBy(css = "div[class='hidden lg:flex items-center gap-6'] a[class='text-gray-600 hover:text-(--primary) transition-colors relative']")
    private WebElement cartIconElement;

    @FindBy(xpath = "//a[@href='/login']")
    private WebElement loginButtonElement;

    // for login page 
    @FindBy(css = ".h-16.object-contain")
    private WebElement loginPageLogoElement;

    @FindBy(xpath = "//h1[contains(.,'Welcome Back!')]")
    private WebElement welcomeBackHeaderElement;

    @FindBy(xpath = "//p[contains(.,'Login with your mobile number')]")
    private WebElement loginWithMobileNumberTextElement;

    @FindBy(css = "input[placeholder='Enter 10-digit mobile number']")
    private WebElement mobileNumberInputElement;

    @FindBy(xpath = "//button[contains(.,'Send OTP')]")
    private WebElement sendOtpButtonElement;

    // enter otp page
    @FindBy(xpath = " //span[@class='font-bold text-slate-900 text-base sm:text-lg tracking-tight whitespace-nowrap']")
    private WebElement mobileNumberTextElement;

    @FindBy(xpath = "//input[@placeholder='Enter 6-digit OTP']")
    private WebElement otpInputElement;

    @FindBy(xpath = "//button[contains(.,'Resend OTP')]")
    private WebElement resendOtpButtonElement;

    @FindBy(xpath = "//button[contains(.,'Verify & Login')]")
    private WebElement verifyAndLoginButtonElement;

    // for header section
    public WebElement getHeaderSectionElement() {
        return headerSectionElement;
    }

    public WebElement getLogoElement() {
        return logoElement;
    }

    public WebElement getWishlistIconElement() {
        return wishlistIconElement;
    }

    public WebElement getCartIconElement() {
        return cartIconElement;
    }

    public WebElement getLoginButtonElement() {
        return loginButtonElement;
    }

    // for login page
    public WebElement getLoginPageLogoElement() {
        return loginPageLogoElement;
    }

    public WebElement getWelcomeBackHeaderElement() {
        return welcomeBackHeaderElement;
    }

    public WebElement getLoginWithMobileNumberTextElement() {
        return loginWithMobileNumberTextElement;
    }

    public WebElement getMobileNumberInputElement() {
        return mobileNumberInputElement;
    }

    public WebElement getSendOtpButtonElement() {
        return sendOtpButtonElement;
    }

    // for enter otp page
    public WebElement getMobileNumberTextElement() {
        return mobileNumberTextElement;
    }

    public WebElement getOtpInputElement() {
        return otpInputElement;
    }

    public WebElement getResendOtpButtonElement() {
        return resendOtpButtonElement;
    }

    public WebElement getVerifyAndLoginButtonElement() {
        return verifyAndLoginButtonElement;
    }
}
