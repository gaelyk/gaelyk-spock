package groovyx.gaelyk.spock

class ExtraBindingsSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'extraBindingsGroovlet.groovy'
	}
	
	def "the NamespaceManager is present in the groovlet binding"(){
		expect:
		extraBindingsGroovlet.namespace != null
		extraBindingsGroovlet.namespace == com.google.appengine.api.NamespaceManager
	}
	
	def "the localMode variable is found in the groovlet binding"(){
		expect:
		extraBindingsGroovlet.localMode == true
	}
	
}
