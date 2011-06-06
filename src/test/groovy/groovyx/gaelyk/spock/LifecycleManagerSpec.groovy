package groovyx.gaelyk.spock

class LifecycleManagerSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'lifecycleManagerGroovlet.groovy'
	}
	
	def "the lifecycle manager is present in the groovlet binding"(){
		expect:
		lifecycleManagerGroovlet.lifecycle == lifecycle
		lifecycleManagerGroovlet.lifecycle instanceof com.google.appengine.api.LifecycleManager
	}
	
	def "the lifecycle manager is utilised from within the groovlet"(){
		when:
		lifecycleManagerGroovlet.get()
		
		then:
		lifecycleManagerGroovlet.request.shutdown == false
	}	
}
