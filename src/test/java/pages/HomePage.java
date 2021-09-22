package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.HelperUtil;
import utilities.WaitUtil;


public class HomePage extends BasePage {

    private HelperUtil helperUtil;
    private WaitUtil waitUtil;
    private int explicitTimeOut;

    @FindBy(xpath = "//div[@class='jumbotron']//h1[contains(text(),'Welcome')]")
    WebElement welcomeTitle;

    @FindBy(name = "fromPort")
    WebElement departureDropDown;

    @FindBy(name = "toPort")
    WebElement destinationDropDown;

    @FindBy(xpath = "//input[@value='Find Flights']")
    WebElement findFlightsButton;

    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        waitUtil = new WaitUtil(driver);
        helperUtil = new HelperUtil(driver);
        explicitTimeOut = waitUtil.getExplicitTimeout();
    }


    public void selectDepartureCity(String value){
        helperUtil.selectByValue(departureDropDown, value);
    }

    public void selectDesitinationCity(String value){
        helperUtil.selectByValue(destinationDropDown, value);
    }

    public void findFlights(){
        waitUtil.isElementVisible(findFlightsButton, explicitTimeOut);
        findFlightsButton.click();
    }


}
