package groovyx.gaelyk.spock

import groovyx.gaelyk.routes.HttpMethod
import groovyx.gaelyk.routes.Route
import spock.util.mop.Use

import javax.servlet.http.HttpServletRequest

import static groovyx.gaelyk.routes.HttpMethod.*

class GaelykRoutingSpec extends GaelykSpec {

	def routesUnderSpec

	def routing = { scriptFile ->
		routesUnderSpec = new RoutesUnderSpec("${scriptFile}", new Binding(
				['datastore', 'memcache', 'mail', 'urlFetch', 'images', 'users', 'user', 'defaultQueue', 'queues', 'xmpp',
						'blobstore', 'files', 'oauth', 'channel', 'capabilities', 'namespace', 'localMode', 'app', 'backends',
						'lifecycle'
				].inject([:]) { map, variableName ->  map << new MapEntry(variableName, this[variableName]) }
		))
		this.metaClass."${scriptFile.tokenize('.').first()}" = routesUnderSpec
	}

	RouteUnderSpec get(String path) {
		routeForMethod(path, GET)
	}

	RouteUnderSpec get(HttpServletRequest request) {
		routeForMethod(request, GET)
	}

	RouteUnderSpec post(String path) {
		routeForMethod(path, POST)
	}

	RouteUnderSpec post(HttpServletRequest request) {
		routeForMethod(request, POST)
	}

	RouteUnderSpec put(String path) {
		routeForMethod(path, PUT)
	}

	RouteUnderSpec put(HttpServletRequest request) {
		routeForMethod(request, PUT)
	}

	RouteUnderSpec delete(String path) {
		routeForMethod(path, DELETE)
	}

	RouteUnderSpec delete(HttpServletRequest request) {
		routeForMethod(request, DELETE)
	}

	RouteUnderSpec all(String path) {
		routeForMethod(path)
	}

	RouteUnderSpec all(HttpServletRequest request) {
		routeForMethod(request)
	}

	RouteUnderSpec getEmailRoute() {
		post('/_ah/mail/*')
	}

	RouteUnderSpec getJabberRoute(String type = 'chat', String value = '') {
		def path
		switch (type) {
			case subscription:
				path = "/_ah/xmpp/subscription/$value/"
				break
			case presence:
				path = "/_ah/xmpp/presence/$value/"
				break
			case chat:
				path = '/_ah/xmpp/message/chat/'
				break
			default:
				throw new IllegalArgumentException("Unknown type '$type'")
		}
		post(path)
	}

	String getSubscription() {
		'subscription'
	}

	String getChat() {
		'chat'
	}

	String getPresence() {
		'presence'
	}

	RouteUnderSpec routeForMethod(HttpServletRequest request, HttpMethod method = null) {
		def allowedMethods = [ALL] + (method ?: [])

		def result = routesUnderSpec.routes.findResult(new RouteUnderSpec()) { Route route ->
			if (route.method in allowedMethods) {
				def resolvedRouteProperties = route.forUri(request)
				if (resolvedRouteProperties.matches) {
					def routeUnderSpec = new RouteUnderSpec(route, resolvedRouteProperties)
					routeUnderSpec
				} else {
					null
				}
			}
		}
		result.testedPath = request.requestURI
		result
	}

	RouteUnderSpec routeForMethod(String path, HttpMethod method = null) {
		def request = Mock(HttpServletRequest) {
			getRequestURI() >> path
		}

		routeForMethod(request, method)
	}
}
