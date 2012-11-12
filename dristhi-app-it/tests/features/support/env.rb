require 'calabash-android/cucumber'

def get_property(selector,propertyName)
	js = "return document.querySelector(\"" + selector+ "\")." + propertyName
	result = performAction('execute_javascript',js)
	if result["success"]
		return result["message"]
	else
		raise "Error while get property"
	end
end

def assert(expected,actual)
	unless expected == actual
		raise "Assertion failed, expected :#{expected} actual:#{actual}" 
	end
end
