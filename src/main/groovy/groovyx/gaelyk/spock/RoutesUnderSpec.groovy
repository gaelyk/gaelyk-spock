package groovyx.gaelyk.spock

class RoutesUnderSpec {
	
	def gse = new GroovyScriptEngine("war/WEB-INF")
	def binding = new Binding()
	def log = new GroovletMockLogger(level:'info')
	def scriptName
	def logging = ''
	def mappings = [:]
	
	RoutesUnderSpec(scriptName){
		if(!scriptName){
			throw new IllegalStateException('The scriptName was not defined in setup()')
		}
		if(! new File("war/WEB-INF/$scriptName").exists()){
			throw new IllegalArgumentException("$scriptName not found. No such file in war/WEB-INF?")
		}
		this.scriptName = scriptName
		bindVariables()
	}
	
	def bindVariables = {
		binding.setVariable 'get', get
		binding.setVariable 'log', log
	}
	
	def get = { directive, mapping ->
		mappings.put mapping, directive
	}
	
	def mapped = {
		mappings."$it" ?: false
	}
	
	def run(){
		gse.run scriptName, binding
		logging = this.log.buffer
		println logging
		return true
	}
	
	def propertyMissing(String name, value){
		log.config "Adding $name:$value to the $scriptName binding."
		binding.setVariable name, value
	}
	
	def propertyMissing(String name){
		binding.getVariable name
	}
		
}
