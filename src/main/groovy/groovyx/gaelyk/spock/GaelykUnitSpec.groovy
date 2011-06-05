package groovyx.gaelyk.spock

import com.google.appengine.api.NamespaceManager
import com.google.appengine.api.blobstore.*
import com.google.appengine.api.channel.*
import com.google.appengine.api.datastore.*
import com.google.appengine.api.files.*
import com.google.appengine.api.mail.*
import com.google.appengine.api.memcache.*
import com.google.appengine.api.oauth.*
import com.google.appengine.api.urlfetch.*
import com.google.appengine.api.users.*
import com.google.appengine.api.taskqueue.*
import com.google.appengine.api.xmpp.*
import com.google.appengine.tools.development.testing.*
import groovyx.gaelyk.*
import javax.servlet.ServletOutputStream

class GaelykUnitSpec extends spock.lang.Specification {
	
	def groovletInstance
	def helper
	def sout
	def datastore, memcache, mail, urlFetch, images, users, user
	def defaultQueue, queues, xmpp, blobstore, files, oauth, channel
	def namespace
	
	def setup(){
		helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalMailServiceTestConfig(),
			new LocalImagesServiceTestConfig(),
			new LocalUserServiceTestConfig(),
			new LocalTaskQueueTestConfig(),
			new LocalXMPPServiceTestConfig(),
			new LocalBlobstoreServiceTestConfig(),
			new LocalFileServiceTestConfig()
		)
		helper.setUp()
		
		Object.mixin GaelykCategory
		
		sout = Mock(ServletOutputStream)
		
		datastore = DatastoreServiceFactory.datastoreService
		memcache = MemcacheServiceFactory.memcacheService
		mail = MailServiceFactory.mailService
		urlFetch = Mock(URLFetchService)
		images = ImagesServiceWrapper.instance
		users = UserServiceFactory.userService
		user = users.currentUser
		defaultQueue = QueueFactory.defaultQueue
		queues = new QueueAccessor()
		xmpp = XMPPServiceFactory.XMPPService
		blobstore = BlobstoreServiceFactory.blobstoreService
		files = FileServiceFactory.fileService
		oauth = Mock(OAuthService)
		channel = Mock(ChannelService)
		namespace = NamespaceManager
	}
	
	def teardown(){
		helper.tearDown()
	}
		
	def groovlet = {
		groovletInstance = new GroovletUnderSpec("$it")
		groovletInstance.sout = sout
		groovletInstance.datastore = datastore
		groovletInstance.memcache = memcache
		groovletInstance.mail = mail
		groovletInstance.urlFetch = urlFetch
		groovletInstance.images = images
		groovletInstance.users = users
		groovletInstance.user = user
		groovletInstance.defaultQueue = defaultQueue
		groovletInstance.queues = queues
		groovletInstance.xmpp = xmpp
		groovletInstance.blobstore = blobstore
		groovletInstance.files = files
		groovletInstance.oauth = oauth
		groovletInstance.channel = channel
		groovletInstance.namespace = namespace
		this.metaClass."${it.tokenize('.').first()}" = groovletInstance
	}
		
}
	
