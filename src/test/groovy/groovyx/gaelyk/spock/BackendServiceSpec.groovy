package groovyx.gaelyk.spock

class BackendServiceSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'backendServiceGroovlet.groovy'
	}
	
	def "the backend service is present in the groovlet binding"(){
		expect:
		backendServiceGroovlet.backends == backends
		backendServiceGroovlet.backends instanceof com.google.appengine.api.backends.BackendService
	}
	
	def "the backend service is invoked from within the groovlet"(){
		given:
		def currentBackend = 'the backend'
		backendServiceGroovlet.params.currentBackend = currentBackend
	
		when:
		backendServiceGroovlet.get()
		
		then:
		1 * backends.getCurrentBackend() >> currentBackend
	}
	
	
}
