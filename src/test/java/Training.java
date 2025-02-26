import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;


public class Training {


    private static final SelenideElement searchInput = $x("//dev['']");
    private static final SelenideElement emailField = $x("//input[@id='email']");
    private static final SelenideElement passwordField = $x("//input[@id='password']");
    private static final SelenideElement logInButton = $x("//button[contains(@class, 'button-blue')]");
    private static final SelenideElement onSitePage = $x("//div[contains(@class, 'menu-label') and contains(text(), 'On-Site')]");
    private static final SelenideElement createProjectButton = $x("//div[@class='filter-panel']//button");
    private static final SelenideElement createButton = $x("//button[contains(@class, 'button-blue')]");
    private static final SelenideElement basePageTemplate = $x("//div[contains(@class, 'templates-grid')]//div[contains(text(), ' Basic page template ')]");
    private static final SelenideElement elementEditor = $x("//div[contains(@class, 'element-icon')][1]");
    private static final SelenideElement designButton = $x("//div[contains(@class, 'templates-grid-element')][1]//button[contains(@class, 'button blue')]");
    private static final SelenideElement editorField = $x("//div[contains(@class, \'containers-container\')]");
    private static final SelenideElement loaderSpinner = $x("//div[@class='spinner']");
    private static final SelenideElement nextButton = $x("//button[contains(@class, 'next-button')]");
    private static final SelenideElement nextButtonOnPreview = $x("//button[contains(@class, 'button-blue')]");
    private static final SelenideElement lowBar = $x("//div[contains(@class, 'form-submit-buttons')]");
    private static final SelenideElement nextButtons = $x("//button[contains(@class, 'next-button')]");
    private static final SelenideElement getNextButton = $x("//button[contains(@class, 'next-button')]");
    private static final SelenideElement lunchButton = $x("//button[contains(@class, 'next-button')]");
    private static final SelenideElement campaignName = $x("//input[contains(@formcontrolname, 'campaignName')]");
    private static final SelenideElement campaignList = $x("//div[contains(@class, 'table-body-row-wrapper ng-star-inserted')]/megadash-table-row[contains(@class, 'table-body-row table-row ng-star-inserted')][1]//div[contains(@class, 'name-column')]");
    private static final SelenideElement doneButton = $x("//button[contains(@class, 'megadash-button-blue-round')]");

    @BeforeTest
    public void start(){
        Browser.getBrowser().get("https://my.fortvision.com/");
        Browser.getBrowser().manage().window().maximize();
    }

    @Test
    public void test() {
        emailField.click();
        emailField.setValue("yauheni.k@fortvision.com");
        passwordField.click();
        passwordField.setValue("Yauheni7347156!");
        logInButton.click();
        onSitePage.click();
        createProjectButton.click();
        Selenide.executeJavaScript("arguments[0].setSelectionRange(0, arguments[0].value.length);", campaignName);
        String selectedText = Selenide.executeJavaScript("return arguments[0].value.substring(arguments[0].selectionStart, arguments[0].selectionEnd);", campaignName);
        System.out.println(selectedText);
        createButton.click();
        loaderSpinner.should(Condition.disappear);
        actions().moveToElement(basePageTemplate).perform();
        designButton.should(Condition.appear);
        designButton.click();
        elementEditor.dragAndDropTo(editorField);
        nextButton.click();
        nextButtonOnPreview.shouldNotHave(attribute("disabled"));
        nextButtonOnPreview.click();
        lowBar.scrollTo();
        nextButtons.click();
        getNextButton.click();
        lunchButton.click();
        doneButton.click();
        Browser.getBrowser().navigate().refresh();
        campaignList.click();
        campaignList.doubleClick();
        Selenide.executeJavaScript("arguments[0].setSelectionRange(0, arguments[0].value.length);", campaignList);
        String createdCampaignName = Selenide.executeJavaScript("return arguments[0].value.substring(arguments[0].selectionStart, arguments[0].selectionEnd);", campaignList);
        System.out.println(createdCampaignName);
        Assert.assertEquals(selectedText, createdCampaignName);
    }

    @AfterTest
    public void closeDriver() {
        Browser.closeDriver();
    }
}
