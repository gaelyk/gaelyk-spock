package groovyx.gaelyk.spock

class OAuthServiceSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'oauthServiceGroovlet.groovy'
	}
	
	def "the oauth service is present in the groovlet binding"(){
		expect:
		oauthServiceGroovlet.oauth != null
		oauthServiceGroovlet.oauth instanceof com.google.appengine.api.oauth.OAuthService
	}
	
	def "the oauth service is accessed from within the groovlet"(){
		given:
		def consumerKey = 'abcdef'
	
		when:
		oauthServiceGroovlet.get()
		
		then:
		1 * oauth.getOAuthConsumerKey() >> consumerKey
		oauthServiceGroovlet.request.consumerKey == consumerKey
	}
}
