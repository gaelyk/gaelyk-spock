package groovyx.gaelyk.spock

class RoutesUnderSpec {
	
	def gse
	def binding = new Binding()
	def log = new GroovletMockLogger(level:'info')
	def scriptName
	def logging = ''
	def mappings = [:]
	def methods = [:]
	
	RoutesUnderSpec(scriptName, webAppDir = 'src/main/webapp/'){
        gse = new GroovyScriptEngine(webAppDir +  "WEB-INF")
		if(!scriptName){
			scriptName = 'routes.groovy'
		}
		if(! new File(webAppDir + "WEB-INF/$scriptName").exists()){
			throw new IllegalArgumentException("$scriptName not found. No such file in ${webAppDir}WEB-INF?")
		}
		this.scriptName = scriptName
		bindVariables()
		run()
	}
	
	def bindVariables = {
		binding.setVariable 'get', get
		binding.setVariable 'post', post
		binding.setVariable 'delete', delete
		binding.setVariable 'put', put
		binding.setVariable 'all', all
		binding.setVariable 'log', log
		binding.setVariable 'email', email
		binding.setVariable 'jabber', jabber
		binding.setVariable 'chat', chat
		binding.setVariable 'subscribe', subscribe
		binding.setVariable 'presence', presence
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
	
	def put = { directive, mapping ->
		mappings.put mapping, directive
		methods.put mapping, "put"
	}
	
	def delete = { directive, mapping ->
		mappings.put mapping, directive
		methods.put mapping, "delete"
	}
	
	def all = { directive, mapping ->
		mappings.put mapping, directive
		methods.put mapping, "all"
	}
	
	def email = { destination ->
		methods.put destination.to, "email"
	}
	
	def jabber = { destination, command="jabber" ->
		methods.put destination.to, command
	}
	
	def chat = 'chat'
	def subscribe = 'subscribe'
	def presence = 'presence'
	
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
