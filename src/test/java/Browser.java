import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {

    private static WebDriver driver;

    // private Browser() {}

    public static WebDriver getBrowser() {
        if (driver == null) {

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            WebDriverRunner.setWebDriver(driver);
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
