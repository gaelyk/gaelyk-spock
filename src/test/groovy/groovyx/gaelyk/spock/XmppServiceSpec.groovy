package groovyx.gaelyk.spock

class XmppServiceSpec extends GaelykUnitSpec {
	
	def setup() {
		groovlet 'xmppGroovlet.groovy'
		
	}
	
	def "the xmpp service is present in the groovlet binding"() {
		expect:
		xmppGroovlet.xmpp == xmpp
		xmppGroovlet.xmpp instanceof com.google.appengine.api.xmpp.XMPPService
	}
	
	def "the xmpp service successfully sends a message"() {
		when:
		xmppGroovlet.get()
		
		then:
		xmppGroovlet.request.success == true
	}
}
