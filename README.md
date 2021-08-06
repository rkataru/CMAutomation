## CoinMarket Automation (UI and API) using Java Cucumber Selenium ##

This automation framework is to run automation (UI and API) tests against https://coinmarketcap.com/

Cucumber feature files location: ./src/test/resources


### Steps to clone and setup

```console
$ git clone https://github...
$ mvn clean install
```

## Running tests ##

Note: Update API Key and browser details at ./src/test/main/resources/config.properties before running tests

```console
$ mvn surefire-report:report
```

Find test reports at ./target/site/surefire-report.html