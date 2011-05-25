package groovyx.gaelyk.spock

class BasicUseCasesCovered extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'basic.groovy'
		basic.params = [param1:'one', param2:'two']
	}
	
	def "params are present"(){
		given: "a groovlet with params set"
		
		expect: "them to be present in the binding of the script"
		basic.binding.params.param1 == 'one'
		basic.binding.params.param2 == 'two'
		
		basic.params.param1 == 'one'
		basic.params.param2 == 'two'
	}
	
	def "request is present and params added"(){
		given: "a groovlet that places params into the request is invoked"
		basic.get()
		
		expect: "all params to be present in request"
		basic.request.param1 == 'one'
		basic.request.param2 == 'two'
		basic.request.param3 == 'three'
	}
	
	def "logger is present and dumps to console"(){
		given: "a groovlet with log statements is invoked"
		basic.get()
		
		expect: "logging on the console"
		basic.logging.contains 'severe log'
		basic.logging.contains 'warning log'
		basic.logging.contains 'info log'
		! basic.logging.contains('config log')
		! basic.logging.contains('fine log')
		! basic.logging.contains('fine log')
		! basic.logging.contains('finer log')
		! basic.logging.contains('finest log')
	
	}
	
	def "forward with appropriate value is called from the script"(){
		given: "a groovlet with appropriate response forwarding is invoked"
		basic.get()
		
		expect: "to see the forward value set in the binding of the groovlet"
		basic.forward == '/WEB-INF/pages/some.gtpl'
	}
	
	def "redirect with appropriate value is called from the script"(){
		given: "a groovlet with appropriate redirect is invoked"
		basic.params.redirectme = 'true'
		basic.get()
		
		expect: "to see the redirect value set in the binding of the groovlet"
		basic.redirect == 'some.groovy'
	}
	
}
