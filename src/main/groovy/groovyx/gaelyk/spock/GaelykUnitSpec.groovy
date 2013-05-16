package groovyx.gaelyk.spock

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

class GaelykUnitSpec extends GaelykSpec {

	def groovletInstance
	def sout, out, response

	def setup() {
		sout = Mock(ServletOutputStream)
		out = Mock(PrintWriter)
		response = Mock(HttpServletResponse)
	}

	String getGroovletsDir() {
		'src/main/webapp/WEB-INF/groovy'
	}

	GroovletUnderSpec getGin() {
		groovletInstance
	}

	def groovlet = { it, dir = groovletsDir ->
		groovletInstance = new GroovletUnderSpec("$it", dir)

		['sout', 'out', 'response', 'datastore', 'memcache', 'mail', 'urlFetch', 'images', 'users', 'user', 'defaultQueue', 'queues', 'xmpp',
				'blobstore', 'files', 'oauth', 'channel', 'capabilities', 'namespace', 'localMode', 'app', 'backends', 'lifecycle'
		].each { groovletInstance."$it" = this."$it" }
		this.metaClass."${it.tokenize('.').first().tokenize('/').last()}" = groovletInstance
	}

}
	
