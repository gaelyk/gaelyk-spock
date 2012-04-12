package groovyx.gaelyk.spock

class GroovletUnderSpec {
	def gse
	def binding = new Binding()
	def log = new GroovletMockLogger(level:'info')
	def scriptName
	
	def forward = ''
	def redirect = ''
	def logging = ''
		
	GroovletUnderSpec(scriptName, String scriptDir = 'war/WEB-INF/groovy'){
		gse = new GroovyScriptEngine("$scriptDir")
		if(!scriptName){
			throw new IllegalStateException('The scriptName was not defined in setup()')
		}
		if(! new File("$scriptDir/$scriptName").exists()){
			throw new IllegalArgumentException("$scriptName not found. No such file in $scriptDir?")
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
	
	void put(){
		run()
	}
	
	void delete(){
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
