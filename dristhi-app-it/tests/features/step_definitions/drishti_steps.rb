When /^I enter username as "([^\"]*)"$/ do |username|
    steps %Q{When I enter "#{username}" into input field number 1}
end

When /^I enter password as "([^\"]*)"$/ do |password|
    steps %Q{When I enter "#{password}" into input field number 2}
end

When /^I wait for home page$/ do
    steps %Q{And I wait for dialog to close
        Then I wait for the "HomeActivity" screen to appear
        And I wait for dialog to close}
end

And /^I select PNC from the list$/ do
    performAction('click_by_selector',"div#pncMenuOption")
end

And /^I select ANC from the list$/ do
    performAction('click_by_selector',"div#ancMenuOption")
end


And /^wait for the page to load$/ do
    steps %Q{And I wait for progress
        And I wait for dialog to close}
end

When /^I press sidepanel$/ do
    performAction('click_by_selector',"div#mainpanel img.sidepanel-icon.affected-by-sidepanel")
end

And /^the page name should be "([^\"]*)"$/ do |name|
    assert name,get_property("div#mainpanel ul.nav.pull-left","innerText").strip
end

And /^the side panel should be displayed$/ do
    steps %Q{Then I wait for a second}
    ln = get_property("div#sidepanel","style.length").to_i
    if  ln <= 0
        raise "Side panel is not displayed"
    end
end
And /^I filter village as "([^\"]*)"$/ do |village|
    performAction('click_by_selector',"a[data-village='#{village}']")
end

And /^I press add ANC button$/ do
    performAction('click_by_selector',"li#plusButton")
end


And /^I press workplan$/ do
    performAction('click_by_selector',"div#workplanButton")
end

And /^"([^\"]*)" tab should be active$/ do |tab|
   index = get_tab_index(tab)
   class_name = get_property("div#content div.tabbable ul li:nth-child(#{index})","className").strip
   unless class_name.include?("active")
     raise "#{tab} is not active"   
   end 
end

When /^I press "([^\"]*)" tab from workflow$/ do |tab|
    index = get_tab_index(tab)
    performAction("click_by_selector","div#content div.tabbable ul li:nth-child(2) a")
end

And /^I check the first task from overdue$/ do
    performAction("click_by_selector","div#overdue img.todo-box")
end  

Then /^verify the first task is checked$/ do 
   class_name = get_property("div#overdue img","className").strip
   unless class_name.include?('checked')
       raise "The task is not checked"
   end
end

def get_tab_index(tab)
     index=0
    case tab.downcase
    when "overdue"
       index=1
    when "todo" , "to do"
        index=2
    else
        index=3
    end
    index
end

