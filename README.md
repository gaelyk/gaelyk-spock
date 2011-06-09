# Gaelyk Spock Helpers

## Overview
Gaelyk is a lightweight Groovy toolkit for Google App Engine Java. Spock is a testing and specification framework for Java and Groovy applications.

The gaelyk-spock project seeks to bring Spock to the world of Gaelyk. It allows for the writing of Specifications within the context of a Gaelyk template project.

This may be achieved by one of the following:

## Build with Gradle
TODO: integrate with gradle-gae-plugin.
<https://github.com/bmuschko/gradle-gae-plugin>

## Build with AntBuilder
A build.groovy file can be found in the contrib folder of this project. This file should be copied over into the root of the template project overwriting the existing build.groovy. All dependencies should also be placed in the lib folder along with the jar file found at:
<http://hashcode.co:9001/job/gaelyk-spock/lastSuccessfulBuild/artifact/build/libs/gaelyk-spock-0.1.jar>

Specs should be placed in the `test/groovy` folder of the project.

Not that this is a temporary work around until the Gradle build has been put in place.

## Getting Started

To write Spock Specs against an existing groovlet, you would need to create a new Specification that extends `groovyx.gaelyk.spock.GaelykUnitSpec`.

All classes extending this class have a simple DSL that declares the groovlet in the setup method using the line `groovlet 'xxxxx.groovy'`. This will automatically look for the `xxxxx.groovy` groovlet under `war/WEB-INF/groovy` of your template project. From this point on the `xxxxx` groovlet is now available as an instance variable in this Spec. You can now call methods on it such as xxxxx.get(). All other services and bindings are also available to this Spec implicitly.

Consider the follwing code example that sets up the dataStoreGroovlet as fixture, then invokes and finally queries the datastore service (which is a GAE Local Service) for side effects of the groovlet invocation.

    class DataStoreServiceSpec extends GaelykUnitSpec {
    
    def setup(){
        groovlet 'dataStoreGroovlet.groovy'
    }
    
    def "the datastore is used from within the groovlet"(){
        given: "the initialised groovlet is invoked and data is persisted"
        dataStoreGroovlet.get()
        
        when: "the datastore is queried for data"
        def query = new Query("person")
        query.addFilter("firstname", Query.FilterOperator.EQUAL, "Marco")
        def preparedQuery = datastore.prepare(query)
        def entities = preparedQuery.asList( withLimit(1) )
        
        then: "the persisted data is found in the datastore"
        def person = entities[0]
        person.firstname == 'Marco'
        person.lastname == 'Vermeulen'
    }
    

## Example Code

Comprehensive examples of how to use GaelykUnitSpec with groovlets can be found in the `src/test/groovy` folder of this project. The groovlets under specification can be found under `war/WEB-INF/groovy`.

These examples cover all the possible services and bindings that groovlets running under Gaelyk have to offer. Stubbed Local Services provided by the Google App Engine SDK have been used where possible, with Spock Mocks covering all exceptional cases (`sout`, `oauth`, `channel`, `urlFetch`, `capabilities` and `backends`).

## Source Code
This can be found at:
    <https://github.com/marcoVermeulen/gaelyk-spock>
With Continuous Integration at:
	<http://hashcode.co:9001/job/gaelyk-spock/>	
	
