package groovyx.gaelyk.spock

class UserServiceSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'userServiceGroovlet.groovy'
	}
	
	def "the user service is present in the spec"() {
		expect: "the user service is present"
		users != null
		users instanceof com.google.appengine.api.users.UserService
	}
		
	def "the user service is accessible from the groovlet"() {
		when:
		userServiceGroovlet.get()
		
		then:
		! userServiceGroovlet.request.userLoggedIn
	}
}