Feature: Selling a Pet

  Scenario: User can inspect the available pets
    When the user fetches the "available" information
    Then there are "available" pets
