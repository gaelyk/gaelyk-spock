package groovyx.gaelyk.spock

class OtherDirSpec extends GaelykUnitSpec {

	def groovletsDir = 'war/WEB-INF/groovy/pkg'
	
	def setup(){
		groovlet 'inPkg.groovy'
	}
	
	def "Name of groovlet is inPkg"(){
		expect:
		inPkg in GroovletUnderSpec
	}
	
}
