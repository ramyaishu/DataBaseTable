Feature: DataBase Table
  Scenario: Verify DataBase Table
    Given User navigate to given url
    When User select dropdown
    Then User selects data from DropDown
    And User needs to verify data with DataBase

    When User select department from city
    Then User takes data from database
    And User needs to verify with the database

    When User select employee with  3 largest salary
    Then User takes data from database
    And USRE NEEDS TO VERIfy with database


