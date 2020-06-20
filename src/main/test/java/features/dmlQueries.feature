Feature: Modify database

  Background: At each scenario
    Given a sqlite connection

  Scenario: Insert data to database
    When it asks to insert a post
      | id | name | sort | creationDate               | lastModifiedDate           |
      | 1  | test | NONE | 2020-01-01T00:00:00.000000 | 2020-01-02T00:00:00.000000 |
    And I ask data of column "*" of table "post"
    Then it should insert the data

  Scenario: Update a post
    When it asks to update a post
      | id | name       |
      | 1  | testUpdate |
    And I ask data of column "*" of table "post"
    Then it updates the post with name "testUpdate"

  Scenario: Delete a post
    When it asks to delete the post with name "testUpdate"
    Then it deletes the post