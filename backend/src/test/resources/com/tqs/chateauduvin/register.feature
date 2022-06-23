Feature: Use Login system
  Scenario: Navigate to the website and login on the platform
    When I navigate to "http://deti-tqs-14.ua.pt:3002/"
    And I click on the Login button
    Then I should see the "LOGIN" page
    And I should fill the Username field with "martim12"
    And I should fill the Password field with "randompass123"
    And I should click on the Submit button
    Then I should be redirected to the "CHATEAU DU VIN" page