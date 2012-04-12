package groovyx.gaelyk.spock

class OtherDirSpec extends GaelykUnitSpec {

	def groovletsDir = 'src/main/webapp/WEB-INF/groovy/pkg'
	
	def setup(){
		groovlet 'inPkg.groovy'
	}
	
	def "Name of groovlet is inPkg"(){
		expect:
		inPkg in GroovletUnderSpec
	}
	
}
