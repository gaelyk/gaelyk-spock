package groovyx.gaelyk.spock

class FileServiceSpec extends GaelykUnitSpec {
	
	def setup() {
		groovlet 'fileServiceGroovlet.groovy'
	}
	
	def "the file service is present in the groovlet binding"(){
		expect:
		fileServiceGroovlet.files != null
		fileServiceGroovlet.files instanceof com.google.appengine.api.files.FileService
	}
	
	def "the file service is accessed from within the groovlet"(){
		given:
		def text = 'some content'
		def name = 'hello.txt'
		fileServiceGroovlet.params.text = text
		fileServiceGroovlet.params.name = name
		
		when:
		fileServiceGroovlet.get()
		
		then:
		fileServiceGroovlet.logging.contains 'writable'
	}
}
