Feature: Select opperations

  Scenario: Getting data from tables
    Given a sqlite connection
    When I ask data of column "name" of table "post"
    Then it should return the data