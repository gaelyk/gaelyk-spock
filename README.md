# Gaelyk Spock Helpers

## Overview
Gaelyk is a lightweight Groovy toolkit for Google App Engine Java. Spock is a testing and specification framework for Java and Groovy applications.

The gaelyk-spock project seeks to bring Spock to the world of Gaelyk. It allows for the writing of Specifications within the context of a Gaelyk template project.

This may be achieved by one of the following:

## Getting Started

To write Spock Specs against an existing groovlet, you would need to create a new Specification that extends `groovyx.gaelyk.spock.GaelykUnitSpec`.

All classes extending this class have a simple DSL that declares the groovlet in the setup method using the line `groovlet 'xxxxx.groovy'`. This will automatically look for the `xxxxx.groovy` groovlet under `src/main/webapp/WEB-INF/groovy` of your template project. From this point on the `xxxxx` groovlet is now available as an instance variable in this Spec. You can now call methods on it such as xxxxx.get(). All other services and bindings are also available to this Spec implicitly.

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

Comprehensive examples of how to use GaelykUnitSpec with groovlets can be found in the `src/test/groovy` folder of this project. The groovlets under specification can be found under `src/main/webapp/WEB-INF/groovy`.

These examples cover all the possible services and bindings that groovlets running under Gaelyk have to offer. Stubbed Local Services provided by the Google App Engine SDK have been used where possible, with Spock Mocks covering all exceptional cases (`sout`, `oauth`, `channel`, `urlFetch`, `capabilities` and `backends`).

## Testing routes

To test routes your specification needs to extend from `GaelykRoutingSpec`. Following is a simple example of how a specification testing routes could look like:

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

        def "a forward of a mapping may be configured"() {
            expect:
            with post('/other'), {
                matches
                destination == "/blog/2008/10/20/welcome-to-my-other-blog"
                redirectionType == FORWARD
            }
        }
    }

Have a look at [RoutesSpec](https://github.com/gaelyk/gaelyk-spock/blob/master/src/test/groovy/groovyx/gaelyk/spock/RoutesSpec.groovy) which is a part of the test suite for the project to learn how to test:

* get, put, post, delete and all methods
* ignored routes
* destination
* redirection types
* email and jabber routes
* single and double star wildcards
* cached routes
* route variables extraction
* route validators
* capability-aware routes
* namespaced routes

## Source Code
This can be found at:
    <https://github.com/marcoVermeulen/gaelyk-spock>
With Continuous Integration at:
	<http://hashcode.co:9001/job/gaelyk-spock/>
	
## Release Notes

### 0.4
  
  * Upgraded to Gaelyk 2.0 and Groovy 2.0
  * **Breaking Change** New API of `GaelykRoutingSpec` covering more testing scenarios for routes
  * Ability to override GAE helper creation via `createLocalTestHelper()`

### 0.3.0.2
  
  * Compiled with Spock 0.7

### 0.3

  * Upgraded Spock to 0.6
  * New [ConventionalGaelykUnitTest](https://github.com/musketyr/gaelyk-spock/blob/master/src/main/groovy/groovyx/gaelyk/spock/ConventionalGaelykUnitSpec.groovy) with automatic groovlet name determination
  * Ability to specify Groovlets' directory (which is now default to `src/main/webapp/WEB-INF/groovy` instead of `war/WEB-INF/groovy` to reflex changes in Gaelyk 1.2


## Build with Gradle
A build.gradle file is present in the contrib folder of the project. This may be copied over into the root of a template project. All specs should be placed into `src/test/groovy` under an appropriate package structure, with resources in `src/test/resources`. Simply run `gradle test` from the root of the project to run your Spock specification against your Groovlets.


## Build with AntBuilder
A build.groovy file can be found in the contrib folder of this project. This file should be copied over into the root of the template project overwriting the existing build.groovy. All dependencies should also be placed in the lib folder along with the jar file found at:
<http://hashcode.co:9001/job/gaelyk-spock/lastSuccessfulBuild/artifact/build/libs/gaelyk-spock-0.1.jar>

Specs should be placed in the `tests/groovy` folder of the project.

The following dependencies should be placed in the lib folder:

* org.spockframework:spock-core:0.5-groovy-1.8
* glaforge:gaelyk:0.7
* com.google.appengine:appengine-api-1.0-sdk:1.5.0
* com.google.appengine:appengine-api-labs:1.5.0
* com.google.appengine:appengine-api-stubs:1.5.0
* com.google.appengine:appengine-testing:1.5.0
* javax.servlet:servlet-api:2.5
* cglib:cglib-nodep:2.2
* org.objenesis:objenesis:1.2


Not that this is a temporary work around until the Gradle build has been put in place.
