package com.cm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;


public class HomePage {

    WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'h7vnx2-2 bFpGkc cmc-table')]/tbody/tr")
    private List<WebElement> entries;

    @FindBy (className = "class=\"sc-16r8icm-0 tu1guj-0 cSTqvK\"")
    private WebElement selectRowsToShow;

    // TOD: Use effective element identifier strategies instead of copied XPath which was due to time constraints
    @FindBy(how= How.XPATH, using = "//*[@id=\"__next\"]/div[1]/div[1]/div[2]/div/div[1]/div[1]/div[2]/div[3]/div[2]/button[1]")
    private WebElement filtersButton;

    String XPATH_Currency = "//p[text()=\"%s\"]";


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void select_rows_dropdown_value(String value) {
        Select select = new Select(selectRowsToShow);
        select.selectByVisibleText(value);
    }

    public int getAllEntries() {
        int value= entries.size();
        return value;
    }

    // TOD: Use effective elements and refactor below method identifier strategies instead of copied XPath which was due to time constraints
    public void clickFiltersButton() {
        filtersButton.click();
        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/div/div[1]/ul/li[5]/button")).click(); //Add Filter
        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div/div/div[2]/div[1]/div[2]/button/div")).click(); // Click Market Cap
        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div/div/div[2]/div/div[3]/div[1]/div[2]/button[2]")).click(); //Choose $1B-$10B
        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div/div/div[2]/div/div[4]/button/div")).click(); //Click Price
        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div/div/div[2]/div/div[4]/div[1]/div[2]/button[3]")).click(); // Choose $101+
        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div/div/div[2]/div/div[4]/div[2]/div/button[1]")).click(); //Apply
    }

    public boolean verifyFilteredCurrenciesAreDisplayed(String currencyList ){
        boolean isCurrencyDisplayed=  driver.findElement(By.xpath(String.format(XPATH_Currency,currencyList))).isDisplayed();
        return isCurrencyDisplayed;

    }

}