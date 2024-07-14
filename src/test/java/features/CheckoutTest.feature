Feature: Redirection to registration at checkout

  Background: Access home page
    Given user access home page

  Scenario: Checkout by not login in user
    And item with the cheapest price is in the basket
      |Snickers|
      |Skittles|
    And user enters into shopping cart
    When unauthorised user trys to checkout
    Then user is redirected to the registration page
