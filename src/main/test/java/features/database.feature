Feature: I want to test the database connectivity

  Scenario: Getting database response
    Given no sql connections
    When I ask to connect to the database
    Then it should connect to the database