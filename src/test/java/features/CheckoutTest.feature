Feature: Redirection to registration at checkout

  Background: Access home page
    Given user access home page

  Scenario: Checkout by not login in user
    Given item "Snickers" with less price is added to the basket
    And item "Skittles" with less price is added to the basket
    And user enters into shopping cart
    When unauthorised user trys to checkout
    Then user is redirected to the registration page
