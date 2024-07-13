Feature: Redirection to registration at checkout

  Scenario: Checkout by not login in user
    And user add to the basket products
      | Snickers |
      | Skittles |
    When user trys to checkout
    Then user is redirected to the registration page
