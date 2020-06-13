Feature: I want to test the database connectivity

  Scenario: Getting database response
    Given a sqlite connection
    When I ask to connect to the database path "jdbc:sqlite:post.db"
    Then it should connect to the database