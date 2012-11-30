package groovyx.gaelyk.spock

import groovyx.gaelyk.routes.Route

class RouteUnderSpec {
	boolean matches = false
	Map variables
	String testedPath
	String namespace
    String destination

	@Delegate
	Route route

	RouteUnderSpec() {
		this.route = new Route('', '', null)
	}

	RouteUnderSpec(Route route, Map resolvedRouteProperties) {
		this.route = route
		resolvedRouteProperties.each { variableName, value -> this[variableName] = value }
	}

	String getNamespace() {
		return namespace
	}

	void setNamespace(String namespace) {
		this.namespace = namespace
	}
    
    String getDestination() {
        return destination
    }

    void setDestination(String destination) {
        this.destination = destination
    }

	boolean asBoolean() {
		matches
	}

	String toString() {
		"${matches ? '' : 'not '}matching route for $testedPath"
	}
}
