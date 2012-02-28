package pkg

import groovyx.gaelyk.spock.ConventionalGaelykUnitSpec;
import groovyx.gaelyk.spock.GaelykUnitSpec;
import groovyx.gaelyk.spock.GroovletUnderSpec;


class InPkgSpec extends ConventionalGaelykUnitSpec {
	
	def "Name of groovlet is inPkg"(){
		expect:
		inPkg in GroovletUnderSpec
		
		when:
		inPkg.get()
		
		then:
		1 * response.setStatus(9999)
	}

}
