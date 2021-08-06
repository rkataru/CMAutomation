package uiSteps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class BaseSteps {

   public static  WebDriver driver ;

    public static void init(){
        String currentDir = System.getProperty("user.dir");
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(currentDir + "/src/main/resources/config.properties");
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String browserName = prop.getProperty("browser");
        if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            driver = new ChromeDriver();
        }
        else if(browserName.equals("firefox")){
            System.setProperty("webdriver.gecko.driver", "geckodriver");
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public void closeBrowser(){
        driver.quit();

    }

}

