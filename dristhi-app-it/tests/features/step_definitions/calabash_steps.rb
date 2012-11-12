require 'calabash-android/calabash_steps'

Then /^show the html source code$/ do
  puts performAction('dump_body_html')
  # puts performAction('dump_html')
end

Then /^I press the button with id "([^\"]*)"$/ do | css|
  performAction('click_by_selector',css)
end

Then /^I get URL$/ do 
	#puts performAction('get_url')
	puts performAction('query','xpath',"//div[@id='highRiskContainer']",)
end


When /^I select first normal risk PNC$/ do
	@pnc_name = get_property("div#normalRiskContainer a.client-list-item ul > li.big-text","innerText").strip
	@hus_name = get_property("div#normalRiskContainer a.client-list-item ul > li:nth-child(2)","innerText").strip
	@village = get_property("div#normalRiskContainer a.client-list-item > div.pull-text-completely-right","innerText").strip
	performAction('click_by_selector',"div#normalRiskContainer a.client-list-item")
end


Then /^the PNC details should match with selection$/ do
	current_pnc=get_property("div#content ul > li.big-text","innerText").strip
	current_hus=get_property("div#content ul > li:nth-child(2)","innerText").strip
	current_village=get_property("div#content ul > li:nth-child(3)","innerText").strip
	assert @pnc_name,current_pnc 
	assert @hus_name,current_hus
	assert @village,current_village
end
