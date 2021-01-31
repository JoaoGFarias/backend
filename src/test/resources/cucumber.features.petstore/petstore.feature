Feature: Selling a Pet

  Scenario: User can inspect the available pets
    When the user fetches the "available" pets
    Then there are "available" pets

  Scenario Outline: User can add a pet to the store
    When the user adds a new pet with "<name>" name, "<category>", and "<status>" status
    Then the pet with "<name>" name, "<category>", and "<status>" is returned
    Examples:
    | name  | category | status    |
    | Ralf  | Dog      | available |
