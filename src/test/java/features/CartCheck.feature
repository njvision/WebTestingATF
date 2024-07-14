Feature: Cart check

  Background: Access home page
    Given user access home page

  Scenario: Check results of added products into the basket by login user
    Given user is logged in with valid credentials
      | email                            | password |
      | jeltobriuh.programming@gmail.com | Fisher77 |
    And item with the cheapest price is in the basket
      | snickers |
      | skittles |
    When user enters into shopping cart
    Then total sum is "correctly" calculated

  Scenario: Checkout by not login in user
    And item with the cheapest price is in the basket
      | snickers |
      | skittles |
    And user enters into shopping cart
    When unauthorised user trys to checkout
    Then user is redirected to the registration page
