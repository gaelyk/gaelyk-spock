package groovyx.gaelyk.spock

class GaelykRoutingSpec extends spock.lang.Specification {
	
	def routeUnderSpec
	
	def routing = { scriptFile ->
		routeUnderSpec = new RoutesUnderSpec("${scriptFile}")
		this.metaClass."${scriptFile.tokenize('.').first()}" = routeUnderSpec
	}
	
	def mapped = { mapping -> 
		def found = routeUnderSpec.mapped(mapping)
		if(!found) throw new InvalidMappingException("No such mapping found: $mapping")
		true
	}
	
	def key, value
	
	def to = { directive ->
		value."$key" == directive
	}
	
	def redirect = { mapping ->
		key = 'redirect'
		value = routeUnderSpec.mappings."$mapping"
		if(!value) throw new InvalidMappingException("No such mapping found: $mapping")
		this
	}

	def forward = { mapping ->
		key = 'forward'
		value = routeUnderSpec.mappings."$mapping"
		if(!value) throw new InvalidMappingException("No such mapping found: $mapping")
		this
	}	
	
}
	
