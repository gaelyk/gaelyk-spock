package groovyx.gaelyk.spock

class BasicUseCasesCovered extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'basicGroovlet.groovy'
		basicGroovlet.params = [param1:'one', param2:'two']
	}
	
	def "params are present"(){
		given: "a groovlet with params set"
		
		expect: "them to be present in the binding of the script"
		basicGroovlet.binding.params.param1 == 'one'
		basicGroovlet.binding.params.param2 == 'two'
		
		basicGroovlet.params.param1 == 'one'
		basicGroovlet.params.param2 == 'two'
	}
	
	def "request is present and params added"(){
		given: "a groovlet that places params into the request is invoked"
		basicGroovlet.get()
		
		expect: "all params to be present in request"
		basicGroovlet.request.param1 == 'one'
		basicGroovlet.request.param2 == 'two'
		basicGroovlet.request.param3 == 'three'
	}
	
	def "logger is present and dumps to console"(){
		given: "a groovlet with log statements is invoked"
		basicGroovlet.get()
		
		expect: "logging on the console"
		basicGroovlet.logging.contains 'severe log'
		basicGroovlet.logging.contains 'warning log'
		basicGroovlet.logging.contains 'info log'
		! basicGroovlet.logging.contains('config log')
		! basicGroovlet.logging.contains('fine log')
		! basicGroovlet.logging.contains('fine log')
		! basicGroovlet.logging.contains('finer log')
		! basicGroovlet.logging.contains('finest log')
	
	}
	
	def "forward with appropriate value is called from the script"(){
		given: "a groovlet with appropriate response forwarding is invoked"
		basicGroovlet.get()
		
		expect: "to see the forward value set in the binding of the groovlet"
		basicGroovlet.forward == '/WEB-INF/pages/some.gtpl'
	}
	
	def "redirect with appropriate value is called from the script"(){
		given: "a groovlet with appropriate redirect is invoked"
		basicGroovlet.params.redirectme = 'true'
		basicGroovlet.get()
		
		expect: "to see the redirect value set in the binding of the groovlet"
		basicGroovlet.redirect == 'some.groovy'
	}
	
	def "out is available and writeable"(){
		when:
		basicGroovlet.get()
		
		then:
		1 * out.write('hello')
	}
	
	def "response is present and accessible"(){
		when:
		basicGroovlet.get()
		
		then:
		1 * response.setHeader('HEADER', 'TEST')
	}
	
}
