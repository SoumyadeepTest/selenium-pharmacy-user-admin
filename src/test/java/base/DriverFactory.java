package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver create(BrowserConfig config) {
        System.out.println("DriverFactory.create() called");

        switch (config.getBrowser().toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                if (config.isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                }

                chromeOptions.addArguments(
                        "--window-size="
                                + config.getScreenSize().getWidth()
                                + ","
                                + config.getScreenSize().getHeight());

                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--remote-allow-origins=*");

                return new ChromeDriver(chromeOptions);

            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (config.isHeadless()) {
                    firefoxOptions.addArguments("-headless");
                }

                return new FirefoxDriver(firefoxOptions);

            case "edge":

                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();

                if (config.isHeadless()) {
                    edgeOptions.addArguments("--headless=new");
                }

                edgeOptions.addArguments(
                        "--window-size="
                                + config.getScreenSize().getWidth()
                                + ","
                                + config.getScreenSize().getHeight());

                return new EdgeDriver(edgeOptions);

            default:
                throw new RuntimeException("Unsupported Browser");
        }
    }
}