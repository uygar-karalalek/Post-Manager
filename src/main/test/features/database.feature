Feature: I want to test the database connectivity

  Scenario: Getting database response
    Given a sqlite connection
    When I ask the list of posts
    Then It should return the list