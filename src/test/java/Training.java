import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$x;


public class Training {
    private static final String serverUrl = "jdbc:mysql://fortvision.cluster-ro-c1pinvi6ti7x.eu-west-1.rds.amazonaws.com:3306/";
    private static final String user = "fortvision_admin";
    private static final String password = "aYLm3Z2am16jjKSqq501HahnBjKd0h";
    private static final String query = "SELECT * FROM dashboard.account_details ad";
    private static final SelenideElement searchInput = $x("//dev['']");
    private static final SelenideElement emailField = $x("//input[@id='email']");
    private static final SelenideElement passwordField = $x("//input[@id='password']");
    private static final SelenideElement logInButton = $x("//button[contains(@class, 'button-blue')]");

    @BeforeTest
    public void start(){
        Browser.getBrowser().get("https://my.fortvision.com/");
    }

    @Test
    public void test() {
        emailField.click();
        emailField.setValue("yauheni.k@fortvision.com");
        passwordField.click();
        passwordField.setValue("Yauheni7347156!");
        logInButton.click();


    }

    @AfterTest
    public void closeDriver() {
        Browser.closeDriver();
    }
}
