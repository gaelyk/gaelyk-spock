package groovyx.gaelyk.spock

import com.google.appengine.api.datastore.*
import com.google.appengine.tools.development.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

class DataStoreServiceSpec extends GaelykUnitSpec {

	def setup(){
		groovlet 'dataStoreGroovlet.groovy'
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
		dataStoreGroovlet.datastore != null
		dataStoreGroovlet.datastore instanceof com.google.appengine.api.datastore.DatastoreService
	}
	
	def "the datastore is used from within the groovlet"(){
		given: "the initialised groovlet is invoked and data is persisted"
		dataStoreGroovlet.get()
		
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
}