package groovyx.gaelyk.spock

import com.google.appengine.api.utils.SystemProperty

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
	
	def "the app variable is found in the groovlet binding"(){
		expect:
		extraBindingsGroovlet.app
		extraBindingsGroovlet.app.env.name == SystemProperty.Environment.Value.Development
		extraBindingsGroovlet.app.env.version == "0.1"
		extraBindingsGroovlet.app.gaelyk.version == "1.1"
		extraBindingsGroovlet.app.id == "1234"
		extraBindingsGroovlet.app.version == "1.0"
	}
	
}
