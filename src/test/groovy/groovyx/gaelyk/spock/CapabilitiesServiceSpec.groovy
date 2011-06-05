package groovyx.gaelyk.spock

import com.google.appengine.api.capabilities.*

class CapabilitiesServiceSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'capabilitiesServiceGroovlet.groovy'
	}
	
	def "the capabilites service is present in the groovlet binding"(){
		expect:
		capabilitiesServiceGroovlet.capabilities != null
		capabilitiesServiceGroovlet.capabilities instanceof com.google.appengine.api.capabilities.CapabilitiesService
	}
	
	def "the capabilities are determined from within the groovlet"(){
		given:
		def capabilityState = Mock(CapabilityState)
	
		when:
		capabilitiesServiceGroovlet.get()
		
		then:
		1 * capabilities.getStatus(Capability.BLOBSTORE) >> capabilityState 
	}
}
