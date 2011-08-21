package groovyx.gaelyk.spock

class RoutesUnderSpec {
	
	def gse = new GroovyScriptEngine("war/WEB-INF")
	def binding = new Binding()
	def log = new GroovletMockLogger(level:'info')
	def scriptName
	def logging = ''
	def mappings = [:]
	def methods = [:]
	
	RoutesUnderSpec(scriptName){
		if(!scriptName){
			scriptName = 'routes.groovy'
		}
		if(! new File("war/WEB-INF/$scriptName").exists()){
			throw new IllegalArgumentException("$scriptName not found. No such file in war/WEB-INF?")
		}
		this.scriptName = scriptName
		bindVariables()
		run()
	}
	
	def bindVariables = {
		binding.setVariable 'get', get
		binding.setVariable 'post', post
		binding.setVariable 'log', log
	}
	
	def run(){
		gse.run scriptName, binding
		logging = this.log.buffer
		println logging
		return true
	}
	
	def get = { directive, mapping ->
		mappings.put mapping, directive
		methods.put mapping, "get"
	}

	def post = { directive, mapping ->
		mappings.put mapping, directive
		methods.put mapping, "post"
	}
	
	def mapped = {
		mappings."$it" ?: false
	}
	
	def redirect = {
		println it
	}
	
	
	def propertyMissing(String name, value){
		log.config "Adding $name:$value to the $scriptName binding."
	}
	
	def propertyMissing(String name){
	}
		
}
