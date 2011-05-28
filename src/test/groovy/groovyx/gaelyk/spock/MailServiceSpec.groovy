package groovyx.gaelyk.spock

class MailServiceSpec extends GaelykUnitSpec {

	def setup(){
		groovlet 'mailServiceTest.groovy'
	}
	
	def "the mail service is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the mail service in the binding"
		mailServiceTest.mail != null
		mailServiceTest.mail instanceof com.google.appengine.api.mail.MailService
	}
	
	//TODO: write specification that asserts mail was sent
	
}
