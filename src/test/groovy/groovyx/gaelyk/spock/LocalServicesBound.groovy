package groovyx.gaelyk.spock

import com.google.appengine.api.datastore.*
import com.google.appengine.tools.development.*
import com.google.appengine.api.urlfetch.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

class LocalServicesBound extends GaelykUnitSpec {

	def url = 'http://gaelyk.appspot.com'

	def setup(){
		groovlet 'local.groovy'
		local.params.url = url
	}
	
	def "the mail service is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the mail service in the binding"
		local.mail != null
		local.mail instanceof com.google.appengine.api.mail.MailService
	}
	
	//TODO: write specification that asserts mail was sent
	
	def "the url fetch service is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the urlfetch service is in the binding"
		local.urlFetch != null
		local.urlFetch instanceof com.google.appengine.api.urlfetch.URLFetchService
	}
	
	def "the url fetch service is used from within the groovlet"(){
		given: "the initialised groovlet composed with a urlFetch service"
		def response = Mock(HTTPResponse)
		
		when: "the groovlet is invoked"
		local.get()
		
		then: "the url service should return a valid response"
		urlFetch.fetch(new URL(url)) >> response
		local.request.result == response
	}
}
