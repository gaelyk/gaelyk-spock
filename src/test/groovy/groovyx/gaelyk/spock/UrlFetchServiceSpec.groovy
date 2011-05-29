package groovyx.gaelyk.spock

import com.google.appengine.api.datastore.*
import com.google.appengine.tools.development.*
import com.google.appengine.api.urlfetch.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

class UrlFetchServiceSpec extends GaelykUnitSpec {

	def url = 'http://gaelyk.appspot.com'

	def setup(){
		groovlet 'urlFetchGroovlet.groovy'
		urlFetchGroovlet.params.url = url
	}
	
	def "the url fetch service is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the urlfetch service is in the binding"
		urlFetchGroovlet.urlFetch != null
		urlFetchGroovlet.urlFetch instanceof com.google.appengine.api.urlfetch.URLFetchService
	}
	
	def "the url fetch service is used from within the groovlet"(){
		given: "the initialised groovlet composed with a urlFetch service"
		def response = Mock(HTTPResponse)
		
		when: "the groovlet is invoked"
		urlFetchGroovlet.get()
		
		then: "the url service should return a valid response"
		urlFetch.fetch(new URL(url)) >> response
		urlFetchGroovlet.request.result == response
	}
}
