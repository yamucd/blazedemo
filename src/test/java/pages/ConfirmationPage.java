package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.HelperUtil;
import utilities.LogUtil;
import utilities.WaitUtil;


public class ConfirmationPage extends BasePage {

    private WaitUtil waitUtil;
    private int explicitTimeOut;

    public ConfirmationPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        waitUtil = new WaitUtil(driver);
        explicitTimeOut = waitUtil.getExplicitTimeout();
    }

    @FindBy(xpath = "/descendant::h1")
    WebElement confirmationPageTitle;


    public void verifyConfirmationPageDisplayed(){

        waitUtil.isElementVisible(confirmationPageTitle, explicitTimeOut);
        confirmationPageTitle.isDisplayed();
        LogUtil.info("Confirmation page is displayed!!");
    }
}
