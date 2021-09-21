#Feature: History Endpoint

#Note: This endpoint is not supported by the testplan I purchased for API
  #@component_test
  #Scenario Outline: User gets historical weather for <city> for the provided <historical_date>
    #Given user sends request using endpoint "historical"
    #And user sends required parameters as below
      #| query  | historical_date  |
      #| <city> | <historicaldate> |
    #And user sends optional parameters as below
      #| hourly   | interval   | units   | language   | callback   |
      #| <hourly> | <interval> | <units> | <language> | <callback> |
    #And user hits APIendpoint "forecast" with parameters
    #Then Response status code is 200
#
    #Examples: 
      #| city      | historicaldate | hourly | interval | units | language |
      #| Amsterdam | 2015-01-21     |        |          |       |          |
