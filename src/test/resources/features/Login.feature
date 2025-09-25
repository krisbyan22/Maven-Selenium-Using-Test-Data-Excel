Feature: Login Functionality

  Scenario Outline: Successful login
    Given User login with testDataID "<testDataID>"
    And User navigates to portal from testDataID "<testDataID>"
    Then User should see Nexchief title
    

    Examples:
      | testDataID  |
      | TDG000001   |


