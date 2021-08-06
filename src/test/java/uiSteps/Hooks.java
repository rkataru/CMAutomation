package uiSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseSteps {

    @Before("@browser")
    public void initialization(){
        init();
    }

    @After("@browser")
    public void teardown(){
        closeBrowser();
    }

}

