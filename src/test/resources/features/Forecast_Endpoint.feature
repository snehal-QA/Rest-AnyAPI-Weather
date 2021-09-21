Feature: Forecast endpoint

  @component_test
  Scenario Outline: User gets Current weather details using endpoint <endpoint> and <city>
    Given user sends request using endpoint "<endpoint>"
    And user sends required parameters as below
      | query  |
      | <city> |
    #And user sends optional parameters as below    >>These parameters needs a subscription plan upgrade; however underlying code to make it work is prepared
    #| units   | language   |
    #| <units> | <language> |
    And user hits APIendpoint "<endpoint>" with parameters
    Then Response status code is 200
    And Response "location.name" has value "<city>"

    Examples: 
      | endpoint | city      | units | language |
      | forecast | Mumbai    |       | mr       |
      | forecast | Amsterdam | f     | nl       |
      | forecast | Paris     | m     |          |
      | forecast | Venice    | s     | it       |
      | current  | Mumbai    |       | mr       |
      | current  | Amsterdam | f     | nl       |
 
  @component_test
  Scenario Outline: User gets error message when required parameter is not provided
    Given user hits APIendpoint "forecast" with missing required parameters "<requiredparameter>"
    Then Response "error.code" has value "<error.code>"
    And Response "error.type" has value "<error.type>"
    And Response "error.info" has value "<error.info>"

    Examples: 
      | endpoint | requiredparameter | error.code | error.type         | error.info                                                                             |
      | forecast | access_key        |        101 | missing_access_key | You have not supplied an API Access Key. [Required format: access_key=YOUR_ACCESS_KEY] |
      | forecast | query             |        601 | missing_query      | Please specify a valid location identifier using the query parameter.                  |
      | current  | access_key        |        101 | missing_access_key | You have not supplied an API Access Key. [Required format: access_key=YOUR_ACCESS_KEY] |
      | current  | query             |        601 | missing_query      | Please specify a valid location identifier using the query parameter.                  |

  @smoke
  Scenario Outline: User gets error response when authorization key is incorrect
    Given user hits APIendpoint "<endpoint>" with incorrect value for parameter "access_key"
    Then Response "error.code" has value "101"
    And Response "error.type" has value "invalid_access_key"
    And Response "error.info" has value "You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]"

    Examples: 
      | endpoint   |
      | forecast   |
      | current    |
      | historical |
