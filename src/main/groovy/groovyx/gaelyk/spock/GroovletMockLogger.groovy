package groovyx.gaelyk.spock

class GroovletMockLogger{
	static final logLevels = ['severe', 'warning', 'info', 'config', 'fine', 'finer', 'finest']
	def level = 'info'
	def buffer = ""
		
	def methodMissing(String name, value){
		def valid = logLevels.find { it == name }
		if(!valid) throw new IllegalArgumentException("$name is not a valid method for the logger.")
		
		def foundLevel = false
		def output = logLevels.findAll {
			if(level == it) { foundLevel = true ; return true }
			return !foundLevel
		}
		
		if(name in output){
			buffer += "[${name.toUpperCase().padRight(7)}] : ${value[0]}\n"
		}
	}
	
	String toString(){ buffer }
}
