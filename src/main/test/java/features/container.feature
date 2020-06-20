Feature: I want to test post container structure

  @FromDB
  Scenario: I want to get all the posts
    Given a post in db
      | name      | sort | creationDate               | lastModifiedDate           |
      | uygarPost | DONE | 2020-01-01T00:00:00.000000 | 2020-01-02T00:00:00.000000 |
    When it asks to get all the posts
    Then it should print the posts