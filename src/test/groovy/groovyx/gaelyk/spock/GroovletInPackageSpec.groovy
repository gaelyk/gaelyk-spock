package groovyx.gaelyk.spock

class GroovletInPackageSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'pkg/inPkg.groovy'
	}
	
	def "Name of groovlet is inPkg"(){
		expect:
		inPkg in GroovletUnderSpec
	}

}
