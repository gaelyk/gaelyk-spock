package groovyx.gaelyk.spock

class MailServiceSpec extends GaelykUnitSpec {

	def setup(){
		groovlet 'mailServiceGroovlet.groovy'
	}
	
	def "the mail service is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the mail service in the binding"
		mailServiceGroovlet.mail != null
		mailServiceGroovlet.mail instanceof com.google.appengine.api.mail.MailService
	}
	
	//TODO: write specification that asserts mail was sent
	
}
