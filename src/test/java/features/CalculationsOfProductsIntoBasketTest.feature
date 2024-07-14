Feature: Basket calculations of cheapest products

  Background: Access home page
    Given user access home page

  Scenario: Check results of added products into the basket by login user
    Given user is logged in with valid credentials
      | email                            | password |
      | jeltobriuh.programming@gmail.com | Fisher77 |
    And item with the cheapest price is in the basket
    |Snickers|
    |Skittles|
    When user enters into shopping cart
    Then total sum is "correctly" calculated
