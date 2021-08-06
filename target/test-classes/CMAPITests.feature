#1. Retrieve the ID of bitcoin (BTC), usd tether (USDT), and Ethereum (ETH), using the
#/cryptocurrency/map call.
#2. Once you have retrieved the IDs of these currencies, convert them to Bolivian Boliviano,
#using the /tools/price-conversion call.

#
#1. Retrieve the first 10 currencies from the cryptocurrency/info call (ID 1, 2, 3 … 10).
#2. Check which currencies have the mineable tag associated with them.
#3. Verify that the correct cryptocurrencies have been printed out.


Feature: CM API Tests

  Scenario: Retrieve and convert BTC,USDT and ETH IDs to Bolivian Boliviano
    Given :Retrieve IDs
    Then :Convert to bolivian Boliviano

  Scenario: Verify Ethereum information
    Then :Validate currency info

  Scenario: Retrieve first ten currencies information and validate them
    Given :Fetch first ten currencies information
    Then :Verify currencies have mineable tag
    And :Verify first 10 currencies names are present in response