package uiSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import com.cm.pages.HomePage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class HomePageStepDefinitions extends BaseSteps{

    HomePage homePage = new HomePage(driver);

    @Given(":User opens base url")
    public void user_opens_base_url()
    {
        String currentDir = System.getProperty("user.dir");
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(currentDir + "/src/main/resources/config.properties");
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String base_url = prop.getProperty("base_url");
        super.driver.get(base_url);
    }

    @Then(":Select row dropdown value")
    public void select_row_dropdown_value() {
        //homePage.select_rows_dropdown_value("100");
    }

    @Then(": {int} results are displayed")
    public void results_are_displayed(int value ) {
        int actualEntries= homePage.getAllEntries();
        Assert.assertEquals(100,actualEntries);
    }

    @Then(":select market cap and price filters")
    public void select_market_cap_and_price_filters() {
        homePage.clickFiltersButton();
    }
    @Then(":Verify the filtered currencies are shown")
    public void verifyFilteredCurrenciesAreDisplayed() {
        int actualEntries = homePage.getAllEntries();
        if (actualEntries == 0) Assert.fail("No results are shown after filters");
    }

}
