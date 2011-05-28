package groovyx.gaelyk.spock

import java.io.OutputStream
import javax.servlet.ServletOutputStream

class ImagesServiceSpec extends GaelykUnitSpec {
	
	OutputStream outputStream = Mock()
	ServletOutputStream sos = Mock()
	
	def setup(){
		groovlet 'imagesTest.groovy'
		imagesTest.sout = sos
	}
	
	def "the images service is present in the groovlet binding"(){
		expect: "the mail service in the binding"
		imagesTest.images != null
		imagesTest.images instanceof com.google.appengine.api.images.ImagesService
	}
	
	def "apply a transform on an image"(){
		given:
		imagesTest.params.path = 'war/WEB-INF/images/image.png'
		
		when:
		imagesTest.get()
		
		then:
		1 * imagesTest.sout.write(_)
	}
	
	
}