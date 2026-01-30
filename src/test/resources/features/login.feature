@LinkedIn @Login
Feature: LinkedIn Login
  As a user
  I want to login to LinkedIn
  So that I can access my LinkedIn account

  Background:
    Given I am on LinkedIn login page

  @Smoke @ValidLogin
  Scenario: Successful login with valid credentials
    When I enter username "test@example.com"
    And I enter password "validPassword123"
    And I click on Sign in button
    Then I should be logged in successfully

  @Smoke @ValidLogin
  Scenario: Successful login using login method
    When I login with username "test@example.com" and password "validPassword123"
    Then I should be logged in successfully

  @InvalidLogin @ErrorHandling
  Scenario: Failed login with invalid username
    When I enter username "invalid@example.com"
    And I enter password "password123"
    And I click on Sign in button
    Then I should see error message
    And I should still be on login page

  @InvalidLogin @ErrorHandling
  Scenario: Failed login with invalid password
    When I enter username "test@example.com"
    And I enter password "wrongPassword"
    And I click on Sign in button
    Then I should see error message
    And I should still be on login page

  @InvalidLogin @ErrorHandling
  Scenario: Failed login with empty username
    When I enter username ""
    And I enter password "password123"
    And I click on Sign in button
    Then I should see error message
    And I should still be on login page

  @InvalidLogin @ErrorHandling
  Scenario: Failed login with empty password
    When I enter username "test@example.com"
    And I enter password ""
    And I click on Sign in button
    Then I should see error message
    And I should still be on login page

  @InvalidLogin @ErrorHandling
  Scenario Outline: Login validation tests
    When I enter username "<username>"
    And I enter password "<password>"
    And I click on Sign in button
    Then I should see error message containing "<error>"
    And I should still be on login page
    
    Examples:
      | username              | password      | error                    |
      | invalid@test.com     | test123       | incorrect                |
      | test@example.com     | wrongpass     | incorrect                |
      |                      | password123   | required                 |
      | test@example.com     |               | required                 |
