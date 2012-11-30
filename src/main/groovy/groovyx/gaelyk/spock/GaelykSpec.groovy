package groovyx.gaelyk.spock

import com.google.appengine.api.LifecycleManager
import com.google.appengine.api.NamespaceManager
import com.google.appengine.api.backends.BackendService
import com.google.appengine.api.blobstore.BlobstoreServiceFactory
import com.google.appengine.api.capabilities.CapabilitiesService
import com.google.appengine.api.channel.ChannelService
import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.files.FileServiceFactory
import com.google.appengine.api.mail.MailServiceFactory
import com.google.appengine.api.memcache.MemcacheServiceFactory
import com.google.appengine.api.oauth.OAuthService
import com.google.appengine.api.taskqueue.QueueFactory
import com.google.appengine.api.urlfetch.URLFetchService
import com.google.appengine.api.users.UserServiceFactory
import com.google.appengine.api.utils.SystemProperty
import com.google.appengine.api.xmpp.XMPPServiceFactory
import groovyx.gaelyk.ImagesServiceWrapper
import groovyx.gaelyk.QueueAccessor
import spock.lang.Specification
import spock.util.mop.Use
import com.google.appengine.tools.development.testing.*

class GaelykSpec extends Specification {

	LocalServiceTestHelper helper

	def datastore, memcache, mail, urlFetch, images, users, user
	def defaultQueue, queues, xmpp, blobstore, files, oauth, channel
	def namespace, localMode, app, capabilities, backends, lifecycle

	def setup() {
		//system properties to be set
		SystemProperty.environment.set("Development")
		SystemProperty.version.set("0.1")
		SystemProperty.applicationId.set("1234")
		SystemProperty.applicationVersion.set("1.0")

		helper = crateLocalTestHelper()
		customizeHelper(helper)
		helper.setUp()

		oauth = Mock(OAuthService)
		channel = Mock(ChannelService)
		urlFetch = Mock(URLFetchService)
		backends = Mock(BackendService)
		capabilities = Mock(CapabilitiesService)

		datastore = DatastoreServiceFactory.datastoreService
		memcache = MemcacheServiceFactory.memcacheService
		mail = MailServiceFactory.mailService
		images = ImagesServiceWrapper.instance
		users = UserServiceFactory.userService
		user = users.currentUser
		defaultQueue = QueueFactory.defaultQueue
		queues = new QueueAccessor()
		xmpp = XMPPServiceFactory.XMPPService
		blobstore = BlobstoreServiceFactory.blobstoreService
		files = FileServiceFactory.fileService
		lifecycle = LifecycleManager.instance

		namespace = NamespaceManager
		localMode = (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development)

		app = [
				env: [
						name: SystemProperty.environment.value(),
						version: SystemProperty.version.get(),
				],
				gaelyk: [
						version: '1.1'
				],
				id: SystemProperty.applicationId.get(),
				version: SystemProperty.applicationVersion.get()
		]

	}

	def cleanup() {
		helper.tearDown()
	}

	void customizeHelper(LocalServiceTestHelper helper) {}
    
    LocalServiceTestHelper crateLocalTestHelper(){
        new LocalServiceTestHelper(
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
    }
}
