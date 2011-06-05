package groovyx.gaelyk.spock

import com.google.appengine.api.channel.ChannelMessage
import javax.servlet.http.HttpServletRequest

class ChannelServiceSpec extends GaelykUnitSpec {
	
	def setup(){
		groovlet 'channelServiceGroovlet.groovy'
	}
	
	def "the channel service is available in the groovlet binding"(){
		expect:
		channelServiceGroovlet.channel != null
		channelServiceGroovlet.channel instanceof com.google.appengine.api.channel.ChannelService
	}
	
	def "the channel service is invoked from within a groovlet"(){
		given:
		def clientId = '1234'
		def message = 'hello'
		
		def channelMessage = new ChannelMessage(clientId, message)
		
		def servletRequest = Mock(HttpServletRequest)
		channelServiceGroovlet.request = servletRequest
		
		channelServiceGroovlet.params.clientId = clientId
		channelServiceGroovlet.params.message = message
		
		when:
		channelServiceGroovlet.get()
		
		then:
		1 * channel.createChannel(clientId)
		1 * channel.parseMessage(servletRequest) >> channelMessage
		1 * channel.sendMessage(channelMessage)
	}
}
