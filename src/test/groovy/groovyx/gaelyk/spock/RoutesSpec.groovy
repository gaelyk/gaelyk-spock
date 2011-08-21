package groovyx.gaelyk.spock

class RoutesSpec extends GaelykRoutingSpec {
	
	def setup(){
		routing 'routes.groovy'
	}
	
	def "a get method may be routed"(){		
		expect:
		get '/about'
	}
	
	def "a get method incorrectly routed will fail"(){
		when:
		get '/aboutz'
		
		then:
		thrown(InvalidMappingException)
	}
	
	
	def "a post method may be routed"(){
		expect:
		post '/other'
	}
	
	def "a post method incorrectly routed will fail"(){
		when:
		post '/otherz'
		
		then:
		thrown(InvalidMappingException)
	}
	
	/**
	def "a redirect of a mapping may be configured"(){
		expect:
		get method '/about'
		redirect '/about' to "/blog/2008/10/20/welcome-to-my-blog"
	}
	
	def "a forward of a mapping may be configured"(){
		expect:
		post method '/other'
		forward '/other' to "/blog/2008/10/20/welcome-to-my-other-blog"
	}
	*/
	
}
