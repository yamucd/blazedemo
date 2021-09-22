package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.HelperUtil;
import utilities.WaitUtil;


public class ReservePage extends BasePage {

    private HelperUtil helperUtil;
    private WaitUtil waitUtil;
    private int explicitTimeOut;



    @FindBy(xpath = "/descendant::h3")
    WebElement reservePageTitle;

    @FindBy(xpath = "/descendant::input[@type='submit'][3]")
    WebElement lowestPrice;

    public ReservePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        waitUtil = new WaitUtil(driver);
        helperUtil = new HelperUtil(driver);
        explicitTimeOut = waitUtil.getExplicitTimeout();
    }

    public void flyLowCostAirLine(){
        waitUtil.isElementVisible(lowestPrice, explicitTimeOut);
        lowestPrice.click();
    }

    public void verifyReservePageDisplayed(){
        waitUtil.isElementVisible(reservePageTitle, explicitTimeOut);
        reservePageTitle.isDisplayed();
    }

}
