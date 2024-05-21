Feature: RestAPI

    Background: Create Auth Token
      Given User Authenticates the system

  Scenario: End To End Booking API TEST
    Given User tests POST method
    When User verifies GetBookingIds method works properly
    Given User tests GET method
    When User verifies Update Booking PUT method works correctly
    When User verifies Partial Update Booking method works correctly
    Then User verifies Delete Booking method works correctly
