package groovyx.gaelyk.spock

class RoutesSpec extends GaelykRoutingSpec {
	
	def setup(){
		routing 'routes.groovy'
	}
	
	def "a get method may be routed"(){		
		expect:
		get '/about'
	}
	
	def "an incorrectly routed method will fail"(){
		when:
		get '/aboutz'
		
		then:
		thrown(InvalidMappingException)
	}
	
	
	def "a post method may be routed"(){
		expect:
		post '/other'
	}
	
	def "a put method may be routed"(){
		expect:
		put '/my-put'
	}
	
	def "a delete method may be routed"(){
		expect:
		delete '/my-delete'
	}
	
	def "all methods may be routed"(){
		expect:
		all '/my-all'
	}
	
	def "a post method incorrectly routed will fail"(){
		when:
		post '/otherz'
		
		then:
		thrown(InvalidMappingException)
	}
	
	def "a redirect of a mapping may be configured"(){
		expect:
		redirect '/about' to "/blog/2008/10/20/welcome-to-my-blog"
	}
	
	def "a forward of a mapping may be configured"(){
		expect:
		forward '/other' to "/blog/2008/10/20/welcome-to-my-other-blog"
	}
	
	def "forward of a mapping to a groovlet may be configured"(){
		expect:
		post '/new-article'
		forward '/new-article' to '/post.groovy'
	}
	
	def "email routing rule forwards to groovlet"(){
		expect:
		email '/receiveEmail.groovy'
	}
	
	def "jabber routing rule forwards to groovlet"(){
		expect:
		jabber '/receiveJabber.groovy'
	}
	
	def "jabber routing rules for user presence, subscribe and chat"(){
		expect:
		jabber presence, '/presenceJabber.groovy'
		jabber subscribe, '/subscribeJabber.groovy'
		jabber chat, '/chatJabber.groovy'
	}
}
