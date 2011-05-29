package groovyx.gaelyk.spock

class QueueSpec extends GaelykUnitSpec {
	
	def setup() {
		groovlet 'queuesGroovlet.groovy'
	}
	
	def "default queue is available in spec"(){
		expect:
		defaultQueue != null
		defaultQueue instanceof com.google.appengine.api.taskqueue.Queue
	}
	
	def "queue accessor is available in spec"(){
		expect:
		queues != null
		queues instanceof groovyx.gaelyk.QueueAccessor
	}
	
	def "default queue is accessed from withing groovlet"(){
		when:
		queuesGroovlet.get()
		
		then:
		queuesGroovlet.request.queueName == 'default'
	}
	
	def "queue accessor is accessed from within groovlet"(){
		when:
		queuesGroovlet.get()
		
		then:
		queuesGroovlet.request.someQueue == 'default'
		queuesGroovlet.request.task == 'task1'
	}
}
