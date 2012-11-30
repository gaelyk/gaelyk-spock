package groovyx.gaelyk.spock

import com.google.appengine.tools.development.testing.LocalCapabilitiesServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest

import static com.google.appengine.api.capabilities.Capability.DATASTORE
import static com.google.appengine.api.capabilities.Capability.DATASTORE_WRITE
import static com.google.appengine.api.capabilities.CapabilityStatus.DISABLED
import static com.google.appengine.api.capabilities.CapabilityStatus.ENABLED
import static groovyx.gaelyk.routes.RedirectionType.FORWARD
import static groovyx.gaelyk.routes.RedirectionType.REDIRECT

class RoutesSpec extends GaelykRoutingSpec {

	def setup() {
		routing 'routes.groovy'
	}

	def "a get method may be routed"() {
		expect:
		get('/about')
	}

	def "an incorrectly routed method will not match"() {
		expect:
		!get('/aboutz')
	}


	def "a post method may be routed"() {
		expect:
		post('/other')
	}

	def "a put method may be routed"() {
		expect:
		put('/my-put')
	}

	def "a delete method may be routed"() {
		expect:
		delete('/my-delete')
	}

	def "all methods may be routed"() {
		expect:
		all('/my-all')
	}

	def "a post method incorrectly routed will not match"() {
		expect:
		!post('/otherz')
	}    
    LocalServiceTestHelper createLocalTestHelper(){
        return new LocalServiceTestHelper()
    }

	def "a route can be ignored"() {
		expect:
		with all('/_ah/'), {
			matches && ignore
		}
	}

	def "a redirect of a mapping may be configured"() {
		expect:
		with get('/about'), {
			matches
			destination == "/blog/2008/10/20/welcome-to-my-blog"
			redirectionType == REDIRECT
		}
	}

	def "a forward of a mapping may be configured"() {
		expect:
		with post('/other'), {
			matches
			destination == "/blog/2008/10/20/welcome-to-my-other-blog"
			redirectionType == FORWARD
		}
	}

	def "forward of a mapping to a groovlet may be configured"() {
		expect:
		with post('/new-article'), {
			matches
			destination == '/post.groovy'
			redirectionType == FORWARD
		}
	}

	def "email routing rule forwards to groovlet"() {
		expect:
		with emailRoute, {
			matches
			destination == '/receiveEmail.groovy'
		}
	}

	def "jabber routing rule forwards to groovlet"() {
		expect:
		with jabberRoute, {
			matches
			destination == '/chatJabber.groovy'
		}
	}

	def "jabber routing rules for user presence, subscribe and chat"() {
		expect:
		with getJabberRoute(presence, 'someUser'), {
			matches
			destination == '/presenceJabber.groovy?value=someUser'
			variables.value == 'someUser'
		}

		with getJabberRoute(subscription, 'someUser'), {
			matches
			destination == '/subscribeJabber.groovy?value=someUser'
			variables.value == 'someUser'
		}

		with getJabberRoute(chat), {
			matches
			destination == '/chatJabber.groovy'
		}
	}

	def "single star wildcards are handled"() {
		expect:
		with all('/withWildcard/wildcard'), {
			matches
			destination == '/defaultSingleStar.groovy'
		}
	}

	def "double star wildcards are handled"() {
		expect:
		with all('/withWildcard/'), {
			matches
			destination == '/defaultDoubleStar.groovy'
		}
	}

	def "path variables are extracted from routes"() {
		expect:
		with get('/article/2009/11/27/groovy-17-RC-1-released'), {
			matches
			destination == '/article.groovy?year=2009&month=11&day=27&title=groovy-17-RC-1-released'
			with variables, {
				year == '2009'
				month == '11'
				day == '27'
				title == 'groovy-17-RC-1-released'
			}
		}
	}

	def 'routes are validated'() {
		expect:
		!get('/number/xyz')
		get('/number/123')
	}

	@Unroll
	def 'routes with validators accessing request can be tested - when attribute has #scenario expected value'() {
		when:
		def request = Mock(HttpServletRequest) {
			getRequestURI() >> '/validate/request'
			getAttribute('registered') >> registered
		}

		then:
		['get', 'post', 'put', 'delete', 'all'].each { method ->
			assert "$method"(request).matches == matches
		}

		where:
		registered | matches
		true       | true
		false      | false

		scenario = registered ? 'an' : 'a not'
	}

	@Unroll
	def 'capability-aware route is picked up when #capabilityName is #status.name()'() {
		given:
		def capabilitiesConfig = new LocalCapabilitiesServiceTestConfig().setCapabilityStatus(capability, status)
		helper = new LocalServiceTestHelper(capabilitiesConfig).setUp()

		expect:
		with get('/update'), {
			matches
			redirectionType == FORWARD
			destination == expectedDestination
		}

		where:
		expectedDestination | capability      | status
		'/update.groovy'    | DATASTORE       | ENABLED
		'/maintenance.gtpl' | DATASTORE       | DISABLED
		'/readonly.gtpl'    | DATASTORE_WRITE | DISABLED

		capabilityName = (capability == DATASTORE_WRITE ? 'datastore write' : 'datastore')
	}

	@Unroll
	def 'routes can be cached for #cache seconds'() {
		expect:
		with get(path), {
			matches
			cacheExpiration == cache
		}

		where:
		path        | cache
		'/about'    | 0
		'/my-all'   | 30.seconds
		'/number/1' | 2.hours
	}

	def 'routes can be namespaced'() {
		expect:
		with post('/update/gaelyk'), {
			matches
			destination == '/namespacedUpdate.groovy'
			namespace == 'namespace-gaelyk'
		}
	}
}
