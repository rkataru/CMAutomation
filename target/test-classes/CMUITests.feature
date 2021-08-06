Feature: CM UI Tests

@browser
Scenario: All 100 results are displayed
  Given :User opens base url
  Then :Select row dropdown value
  Then : 100 results are displayed

 @browser
 Scenario: records displayed as per the filter
   Given :User opens base url
   Then :select market cap and price filters
   Then :Verify the filtered currencies are shown