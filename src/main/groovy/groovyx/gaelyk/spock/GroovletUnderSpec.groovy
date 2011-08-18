package groovyx.gaelyk.spock

class GroovletUnderSpec {
	
	def gse = new GroovyScriptEngine("war/WEB-INF/groovy")
	def binding = new Binding()
	def log = new GroovletMockLogger(level:'info')
	def scriptName
	
	def forward = ''
	def redirect = ''
	def logging = ''
		
	GroovletUnderSpec(scriptName){
		if(!scriptName){
			throw new IllegalStateException('The scriptName was not defined in setup()')
		}
		if(! new File("war/WEB-INF/groovy/$scriptName").exists()){
			throw new IllegalArgumentException("$scriptName not found. No such file in war/WEB-INF/groovy ?")
		}
		this.scriptName = scriptName
		bindVariables()
	}
	
	def bindVariables = {
		binding.setVariable 'params', [:]
		binding.setVariable 'headers', [:]
		binding.setVariable 'request', [:]
		binding.setVariable 'forward', { forward = it }
		binding.setVariable 'redirect', { redirect = it }
		binding.setVariable 'log', log
	}
	
	void get(){
		run()
	}
	
	void post(){
		run()
	}
	
	void run(){
		gse.run scriptName, binding
		logging = this.log.buffer
		println logging
	}
	
	def propertyMissing(String name, value){
		log.config "Adding $name:$value to the $scriptName binding."
		binding.setVariable name, value
	}
	
	def propertyMissing(String name){
		binding.getVariable name
	}
		
}
