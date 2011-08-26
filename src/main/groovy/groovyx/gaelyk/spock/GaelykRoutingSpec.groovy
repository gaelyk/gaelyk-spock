package groovyx.gaelyk.spock

class GaelykRoutingSpec extends spock.lang.Specification {
	
	def routesUnderSpec
	
	def routing = { scriptFile ->
		routesUnderSpec = new RoutesUnderSpec("${scriptFile}")
		this.metaClass."${scriptFile.tokenize('.').first()}" = routesUnderSpec
	}
	
	def get = { verb(it, "get") }
	def post = { verb(it, "post") }
	def put = { verb(it, "put") }
	def delete = { verb(it, "delete") }
	def all = { verb(it, "all") }
	def email = { verb(it, "email") }
	def jabber = { sig="jabber", mapping -> verb(mapping, sig) }
	
	def presence = "presence"
	def chat = "chat"
	def subscribe = "subscribe"
	
	def verb = { mapping, signature ->
		if(routesUnderSpec.methods."$mapping" != signature){
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
	
