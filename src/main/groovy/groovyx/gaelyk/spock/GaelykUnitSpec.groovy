package groovyx.gaelyk.spock

import com.google.appengine.api.datastore.*
import com.google.appengine.api.mail.*
import com.google.appengine.api.memcache.*
import com.google.appengine.api.urlfetch.*
import com.google.appengine.tools.development.testing.*
import groovyx.gaelyk.GaelykCategory

class GaelykUnitSpec extends spock.lang.Specification {
	
	def groovletInstance
	def helper
	def datastore
	def memcache
	def mail
	def urlFetch
	
	def setup(){
		helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalMailServiceTestConfig()
		)
		helper.setUp()
		
		Object.mixin GaelykCategory
		
		datastore = DatastoreServiceFactory.datastoreService
		memcache = MemcacheServiceFactory.memcacheService
		mail = MailServiceFactory.mailService
		urlFetch = Mock(URLFetchService)
	}
	
	def teardown(){
		helper.tearDown()
	}
		
	def groovlet = {
		groovletInstance = new GroovletUnderSpec("$it")
		groovletInstance.datastore = datastore
		groovletInstance.memcache = memcache
		groovletInstance.mail = mail
		groovletInstance.urlFetch = urlFetch
		this.metaClass."${it.tokenize('.').first()}" = groovletInstance
	}
		
}
	
