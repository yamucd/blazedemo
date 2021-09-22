package tests;

import builder.BookingDetailsBuilder;
import model.BookingDetailsModel;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ConfirmationPage;
import pages.HomePage;
import pages.PurchasePage;
import pages.ReservePage;
import utilities.ConfigReader;
import utilities.CsvReader;
import utilities.HelperUtil;
import utilities.LogUtil;
import java.util.List;
import java.io.File;


public class BookAFlightTest extends BaseTest {

    private static Logger _log = LoggerFactory.getLogger(BookAFlightTest.class);
    private String testCaseName = this.getClass().getName().trim();

    private CsvReader readCsv = new CsvReader();
    private String baseUrl = ConfigReader.get("baseURL");

    public HomePage homePage;
    public ReservePage reservePage;
    public PurchasePage purchasePage;
    public ConfirmationPage confirmationPage;
    public HelperUtil helperUtil;

    @BeforeClass
    public void setup(){
        LogUtil.info("End to End test for Booking a flight");
        LogUtil.startTestCase(testCaseName);
        homePage = PageFactory.initElements(driver, HomePage.class);
        reservePage = PageFactory.initElements(driver, ReservePage.class);
        purchasePage = PageFactory.initElements(driver, PurchasePage.class);
        confirmationPage = PageFactory.initElements(driver, ConfirmationPage.class);
        helperUtil = new HelperUtil(driver);
    }


    @DataProvider(name= "bookFlight")
    public Object[][] bookFlightsData(){
        Object[][] rawData;
        List<String[]> bookFlights = readCsv.parseFile(new File(ConfigReader.get("BookingDetailsCSV"))
                .getAbsolutePath());
        rawData = new Object[bookFlights.size()][1];

        for(int i = 0; i< bookFlights.size(); i++){
            for(String[] bookFlight : bookFlights){
                rawData[i++][0] = new BookingDetailsBuilder(bookFlight);
            }
        }
        return rawData;
    }

    @Test(description = "Search for flights", dataProvider = "bookFlight")
    public void searchForFlights(BookingDetailsBuilder data){
        helperUtil.navigatePage(baseUrl);
        homePage.selectDepartureCity(data.getBookingDetails().getDeparture());
        homePage.selectDesitinationCity(data.getBookingDetails().getDestination());
        homePage.findFlights();
    }

    @Test(description = "Choose Low cost airline", dependsOnMethods ="searchForFlights", dataProvider = "bookFlight")
    public void chooseLowestPricedAirline(BookingDetailsBuilder data){
        reservePage.verifyReservePageDisplayed();
        reservePage.flyLowCostAirLine();
    }


    @Test(description = "Enter details and purchase a ticket", dependsOnMethods ="chooseLowestPricedAirline",
            dataProvider = "bookFlight")
    public void purchaseFlightTicket(BookingDetailsBuilder data){
        purchasePage.verifyPurchasePageDisplayed();
        purchasePage.enterFlyerName(data.getBookingDetails().getName());
        purchasePage.enterFlyerAddress(data.getBookingDetails().getAddress());
        purchasePage.enterFlyerCity(data.getBookingDetails().getCity());
        purchasePage.enterFlyerState(data.getBookingDetails().getState());
        purchasePage.enterFlyerZipCode(data.getBookingDetails().getZipCode());
        purchasePage.enterFlyerCardNumber(data.getBookingDetails().getCardNumber());
        purchasePage.enterFlyerCardMonth(data.getBookingDetails().getMonth());
        purchasePage.enterFlyerCardYear(data.getBookingDetails().getYear());
        purchasePage.enterFlyerCardName(data.getBookingDetails().getCardName());
        purchasePage.clickPurchaseFlights();
    }


    @Test(description = "Confirmation page displayed after purchase", dependsOnMethods ="purchaseFlightTicket")
    public void bookingConfirmed(){
        confirmationPage.verifyConfirmationPageDisplayed();
    }

    @AfterTest(description = "After test info")
    public void afterTest() {
        LogUtil.endTestCase(testCaseName);
        driver.quit();
    }

}
