package groovyx.gaelyk.spock

class ConventionalGaelykUnitSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet groovletName
	}
	
	def getGroovletName(){
		String baseName = getClass().simpleName - 'GaelykUnitSpec' - 'GaelykSpec' - 'Spec'
		if(!baseName){
			throw new IllegalArgumentException('Cannot determine groovlet name from the name of the class!')
		}
		String name = baseName[0].toLowerCase()
		if(baseName.size() > 1){
			name += baseName[1..-1]
		}
		if(getClass().getPackage() && getClass().getPackage().name){
			name = getClass().getPackage().name.replace('.','/') + '/' + name
		}
		name + '.groovy'
	}

}
