package groovyx.gaelyk.spock

import groovyx.gaelyk.routes.RoutesBaseScript
import org.codehaus.groovy.control.CompilerConfiguration

class RoutesUnderSpec {

	def binding
	def log = new GroovletMockLogger(level: 'info')
	def routes
	def logging = ''

	RoutesUnderSpec(scriptName, binding, webAppDir = 'src/main/webapp/') {
		this.binding = binding
		def config = new CompilerConfiguration()
		config.scriptBaseClass = RoutesBaseScript.class.name

		binding.log = log
		binding.chat = 'chat'
		binding.presence = 'presence'
		binding.subscription = 'subscription'

		def routesFile = new File(webAppDir + "WEB-INF/$scriptName")
		if (!routesFile.exists()) {
			throw new IllegalArgumentException("$scriptName not found. No such file in ${webAppDir}WEB-INF?")
		}

		RoutesBaseScript script = (RoutesBaseScript) new GroovyShell(binding, config).parse(routesFile)

		script.run()

		logging = log.buffer
		println logging

		routes = script.routes
	}
}
