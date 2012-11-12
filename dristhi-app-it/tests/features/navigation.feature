Feature: Navigation

    Background:
        When I enter username as "c"
        And I enter password as "1"
        And I press the "Login to Dristhi" button
        And I wait for home page

    Scenario: View PNC and ANC Details
        When I select PNC from the list
        And wait for the page to load

        Then the page name should be "PNC"
        And I filter village as "munjanahalli"

        When I select first nomal risk PNC
        And wait for the page to load

        Then the PNC details should match with selection

        When I press sidepanel
        Then the side panel should be displayed

        When I select ANC from the list
        And wait for the page to load
        Then the page name should be "ANC"
        # And I press add ANC button
        # And I wait for 15 seconds
        # Then I should see "You are at the start of"


