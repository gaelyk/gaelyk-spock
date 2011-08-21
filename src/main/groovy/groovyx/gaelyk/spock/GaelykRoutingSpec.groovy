package groovyx.gaelyk.spock

class GaelykRoutingSpec extends spock.lang.Specification {
	
	def routesUnderSpec
	
	def routing = { scriptFile ->
		routesUnderSpec = new RoutesUnderSpec("${scriptFile}")
		this.metaClass."${scriptFile.tokenize('.').first()}" = routesUnderSpec
	}
	
	def get = { method(it, "get") }
	def post = { method(it, "post") }
	
	def method = { mapping, method ->
		if(routesUnderSpec.methods["$mapping"] != method){
			throw new InvalidMappingException("No such mapping found: $mapping")
		} else {
			return true
		}
	}
	
	def key, value
	
	def to = { directive ->
		value."$key" == directive
	}
	
	def redirect = { mapping ->
		key = 'redirect'
		value = routesUnderSpec.mappings."$mapping"
		if(!value) throw new InvalidMappingException("No such mapping found: $mapping")
		this
	}

	def forward = { mapping ->
		key = 'forward'
		value = routesUnderSpec.mappings."$mapping"
		if(!value) throw new InvalidMappingException("No such mapping found: $mapping")
		this
	}
	
}
	
