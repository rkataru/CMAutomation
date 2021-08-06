package apiSteps;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import net.minidev.json.JSONArray;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class RESTStepsDefinations {

    private APIStepData apiStepData;

    public RESTStepsDefinations(APIStepData apiStepsData){
        this.apiStepData= apiStepsData;
    }
    private static String BASE_URL;
    private static String API_KEY;
    private static String API_CURRENCYMAP= "/v1/cryptocurrency/map";
    private static String API_CURRENCY_CONVERSION="/v1/tools/price-conversion";
    private static String API_CURRENCY_INFO="/v1/cryptocurrency/info";
    private static String etherumLogo="https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png";
    private static  String ethereumTechDoc= "https://github.com/ethereum/wiki/wiki/White-Paper";
    private static String ethereumSymbol="ETH", ethereumDateAdded="2015-08-07T00:00:00.000Z",mineableTag="mineable";
    private static int[] tenCurrencyIds= {1,2,3,4,5,6,7,8,9,10};
    private static Response response;
    private static String bitCoinId,tetherId,ethereumId;
//
//    private static final String API_GET_CURRENCY_ID="/cryptocurrency/map";
//    private static final String API_PRICE_CONVERSION="/tools/price-conversion";

    static {
        String currentDir = System.getProperty("user.dir");
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(currentDir + "/src/main/resources/config.properties");
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        API_KEY = prop.getProperty("api_key");
        BASE_URL= prop.getProperty("api_end_point");
    }

    @Given(":Retrieve IDs")
    public  void retrieveId() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("X-CMC_PRO_API_KEY", API_KEY);
        response = request.get(BASE_URL + API_CURRENCYMAP);

        JSONArray arr =  JsonPath.parse(response.asString()).read("$.data[?( @.name == 'Bitcoin')].id");
        bitCoinId= arr.get(0).toString(); System.out.println("Bitcoin Id is " +bitCoinId);
        arr= JsonPath.parse(response.asString()).read("$.data[?( @.name == 'Ethereum')].id");
        ethereumId= arr.get(0).toString(); System.out.println("Ethereum Id is " +ethereumId);
        arr= JsonPath.parse(response.asString()).read("$.data[?( @.name == 'Tether')].id");
        tetherId= arr.get(0).toString(); System.out.println("Tether Id is " +tetherId);

    }

    @Given(":Convert to bolivian Boliviano")
    public void convertCurrencies() {

        String bolivian_id= "2832";
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("X-CMC_PRO_API_KEY", API_KEY);

        response = request.get(BASE_URL+ API_CURRENCY_CONVERSION+"?amount=1&convert_id="+bolivian_id+"&id="+bitCoinId );
        Double bitCoinConverted=  JsonPath.parse(response.asString()).read("$.data.quote."+bolivian_id+".price");
        System.out.println("bitCoin value after conversion is " +bitCoinConverted);


        RequestSpecification request2 = RestAssured.given();
        request2.header("X-CMC_PRO_API_KEY", API_KEY);
        response = request2.get(BASE_URL+ API_CURRENCY_CONVERSION+"?amount=1&convert_id="+bolivian_id+"&id="+ethereumId );
        Double ethereumConverted=  JsonPath.parse(response.asString()).read("$.data.quote."+bolivian_id+".price");
        System.out.println("Ethereum value after conversion is " +ethereumConverted);


        RequestSpecification request3 = RestAssured.given();
        request3.header("X-CMC_PRO_API_KEY", API_KEY);
        response = request3.get(BASE_URL+ API_CURRENCY_CONVERSION+"?amount=1&convert_id="+bolivian_id+"&id="+tetherId );
        Double tetherConverted=  JsonPath.parse(response.asString()).read("$.data.quote."+bolivian_id+".price");
        System.out.println("Tether value after conversion is " +tetherConverted);
    }


    @Then(":Validate currency info")
    public void validateCurrencyInfo() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("X-CMC_PRO_API_KEY", API_KEY);

        //Make a Get request
        response = request.get(BASE_URL+ API_CURRENCY_INFO+ "?id="+ethereumId);

        //Validate url in ethereum info
        JSONArray arr= JsonPath.parse(response.asString()).read("$.data..[?(@.name == 'Ethereum')].logo");
        String logo= arr.get(0).toString();
        Assert.assertEquals(etherumLogo,logo);

        //Validate ethereum tech doc
        arr= JsonPath.parse(response.asString()).read(  "$.data..[?(@.name == 'Ethereum')].urls.technical_doc[0]");
        String techDoc= arr.get(0).toString();
        Assert.assertEquals(ethereumTechDoc,techDoc);

        //Validate ethereum symbol
        arr= JsonPath.parse(response.asString()).read(  "$.data..[?(@.name == 'Ethereum')].symbol");
        String symbol= arr.get(0).toString();
        Assert.assertEquals(ethereumSymbol,symbol);

        //Validate ethereum date added
        arr= JsonPath.parse(response.asString()).read(  "$.data..[?(@.name == 'Ethereum')].date_added");
        String dateAdded= arr.get(0).toString();
        Assert.assertEquals(ethereumDateAdded,dateAdded);

        //Validate ethereum platform added
        arr= JsonPath.parse(response.asString()).read(  "$.data..[?(@.name == 'Ethereum')].platform");
        Assert.assertNull(null,arr.get(0));

        //Validate ethereum tags has 'mineable'
        arr= JsonPath.parse(response.asString()).read(  "$.data..[?(@.name == 'Ethereum')].tags[0]");
        String tag= arr.get(0).toString();
        Assert.assertEquals(mineableTag,tag);

    }


    @Given(":Fetch first ten currencies information")
    public void fetchFirstTenCurrenciesInfo() {

        RestAssured.baseURI = BASE_URL;
        apiStepData.request = RestAssured.given();
        apiStepData.request.header("X-CMC_PRO_API_KEY", API_KEY);

        //Make a Get request
        String formattedCurrencyIds= Arrays.toString(tenCurrencyIds).replace(" ","").replace("[", "").replace("]","");
        apiStepData.response = apiStepData.request.get(BASE_URL + API_CURRENCY_INFO + "?id="+formattedCurrencyIds);

    }


    @Given(":Verify currencies have mineable tag")
    public void verifyCurrenciesTag() {
        String mineable= "Mineable";
        //Validate for first 10 currencies 'mineable' tag is present
        for (int id : tenCurrencyIds){
            JSONArray arr= JsonPath.parse(apiStepData.response.asString()).read("$.data..[?(@.id == '"+id+"')].tag-names[0]");
            String firstTag= arr.get(0).toString();
            Assert.assertEquals(mineable,firstTag);

        }


    }
    @And(":Verify first 10 currencies names are present in response")
    public void verifyCurrencyNamesArePresent() {

        //Validate for first 10 currencies names are present in response
        for (int id : tenCurrencyIds){
            JSONArray arr= JsonPath.parse(apiStepData.response.asString()).read("$.data..[?(@.id == '"+id+"')].name");
            String name= arr.get(0).toString();
            Assert.assertNotNull(name);
            System.out.println(name);

        }

    }


}
