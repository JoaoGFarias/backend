Feature: Selling a Pet


  Scenario: User can inspect the available pets
    When the user fetches the "available" pets
    Then there are "available" pets

  Scenario Outline: User can sell a pet
    When the user adds a new pet with "<name>" name, "<category>", and "<status>" status
    And the user marks the pet as "sold"
    And the user deletes the pet
    Then the pet no longer exists
    Examples:
      | name  | category | status    |
      | Ralf  | Dog      | available |
