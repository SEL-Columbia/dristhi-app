Feature: Workplan
In order to organize my tasks
As an ANM
I should be able to view my workplan

Background:
        When I enter username as "c"
        And I enter password as "1"
        And I press the "Login to Dristhi" button
        And I wait for home page

Scenario: Verify workplan
		When I press workplan
		And wait for the page to load
        Then the page name should be "Workplan"
        And "Overdue" tab should be active

        When I press "Todo" tab from workflow
        Then "Todo" tab should be active
@wip
Scenario: complete an overdue task
		When I press workplan
		And wait for the page to load
		And I check the first task from overdue  
		Then verify the first task is checked
