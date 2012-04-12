package groovyx.gaelyk.spock

import javax.servlet.ServletOutputStream

class ImagesServiceSpec extends GaelykUnitSpec {
	
	OutputStream outputStream = Mock()
	ServletOutputStream sos = Mock()
	
	def setup(){
		groovlet 'imagesGroovlet.groovy'
		imagesGroovlet.sout = sos
	}
	
	def "the images service is present in the groovlet binding"(){
		expect: "the mail service in the binding"
		imagesGroovlet.images != null
		imagesGroovlet.images instanceof com.google.appengine.api.images.ImagesService
	}
	
	def "apply a transform on an image"(){
		given:
		imagesGroovlet.params.path = 'src/main/webapp/WEB-INF/images/image.png'
		
		when:
		imagesGroovlet.get()
		
		then:
		1 * imagesGroovlet.sout.write(_)
	}
	
	
}