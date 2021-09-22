package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import utilities.ConfigReader;
import utilities.ConfigWriter;



public class BaseTest {

    public static WebDriver driver;

    private static Logger _log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    void Config()
    {
        ConfigWriter.setPropertyValue("logger.file", "webLogger", ConfigReader.get("config.path"));
    }


    @Parameters({"browser"})
    @BeforeClass
    public void launchBrowser(String browserName){
        try{
            if(browserName.equals("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }else if(browserName.equals("firefox")){
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }

            driver.manage().window().setPosition(new Point(0,0));
            Dimension d = new Dimension(1300,900);
            driver.manage().window().setSize(d);
        }catch (WebDriverException e){
            System.out.println(e.getMessage());
        }

    }

    @AfterClass
    public void tearDownBrowser(){
        driver.quit();
    }
}
