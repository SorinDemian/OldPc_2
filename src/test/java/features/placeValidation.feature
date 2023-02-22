Feature: Validating Place API's

  @AddPlace
  Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
    Given Add Place payload with "<name>", "<language>", "<address>"
    When User calls "addPlaceAPI" with "POST" http request
    Then The API call is success with status code 200
    And "status" response body is "OK"
    And "scope" response body is "APP"
    And Verify place_id created maps to "<name>" using "getPlaceAPI"

    Examples:
    | name         | language | address             |
    | AAhouse      | English  | World cross center  |
    | BBhouse      | Spanish  | Sea cross center    |

  @DeletePlace
  Scenario: Verify id Deleta Place functionality is working
    Given DeletePlace Payload
    When User calls "deletePlaceAPI" with "POST" http request
    Then The API call is success with status code 200
    And "status" response body is "OK"
