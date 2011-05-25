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

	def "the datastore is present in the spec fixture"(){
		given: "this spec with its fixture"
		
		expect: "to access the datastore instance in the parent object."
		datastore != null
		datastore instanceof DatastoreService
	}
	
	def "the datastore is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the datastore in the binding"
		local.datastore != null
		local.datastore instanceof com.google.appengine.api.datastore.DatastoreService
	}
	
	def "the datastore is used from within the groovlet"(){
		given: "the initialised groovlet is invoked and data is persisted"
		local.get()
		
		when: "the datastore is queried for data"
		def query = new Query("person")
		query.addFilter("firstname", Query.FilterOperator.EQUAL, "Marco")
		def preparedQuery = datastore.prepare(query)
		def entities = preparedQuery.asList( withLimit(1) )
		
		then: "the persisted data is found in the datastore"
		def person = entities[0]
		person.firstname == 'Marco'
		person.lastname == 'Vermeulen'
		person.age == 40
	}
	
	def "the memcache service is present in the groovlet binding"(){
		given: "the initialised groovlet"
		
		expect: "the memcache in the binding"
		local.memcache != null
		local.memcache instanceof com.google.appengine.api.memcache.MemcacheService
	}
	
	def "the memcache service is used from within the groovlet"(){
		given: "the initialised groovlet is invoked and data is cached"
		local.get()
		
		expect: "the data to be present in the cache"
		memcache.contains 'person'
		def person = memcache.get('person')
		person.firstname == 'Marco'
		person.lastname == 'Vermeulen'
		person.age == 40
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
