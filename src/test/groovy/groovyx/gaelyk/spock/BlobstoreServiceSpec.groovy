package groovyx.gaelyk.spock

class BlobstoreServiceSpec extends GaelykUnitSpec {
	
	def setup() {
		groovlet 'blobstoreGroovlet.groovy'
	}
	
	def "the blobstore is present in the groovlet binding"(){
		expect:
		blobstoreGroovlet.blobstore
		blobstoreGroovlet.blobstore instanceof com.google.appengine.api.blobstore.BlobstoreService
	}
	
	def "the blobstore service is invoked from within the groovlet"(){
		when:
		blobstoreGroovlet.get()
		
		then:
		blobstoreGroovlet.request.success == true
	}
	
}
