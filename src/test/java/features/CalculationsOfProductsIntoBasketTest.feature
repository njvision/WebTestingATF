Feature: Basket calculations of cheapest products

  Background: Access home page
    Given user access home page

  Scenario: Check results of added products into the basket by login user
    Given user is logged in with valid credentials
      | email                            | password |
      | jeltobriuh.programming@gmail.com | Fisher77 |
    And item "Snickers" with less price is added to the basket
    And item "Skittles" with less price is added to the basket
    When user enters into shopping cart
    Then cart has "2" items
    And total sum calculated is "correctly" calculated
