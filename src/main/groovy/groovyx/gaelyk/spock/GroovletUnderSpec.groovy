package groovyx.gaelyk.spock

class GroovletUnderSpec {
    GroovyScriptEngine gse
    Binding binding = new Binding()
    def log = new GroovletMockLogger(level:'info')
    String scriptName

    String forward = ''
    String redirect = ''
    String logging = ''

    GroovletUnderSpec(scriptName, String scriptDir = 'src/main/webapp/WEB-INF/groovy'){
        gse = new GroovyScriptEngine("$scriptDir")
        if(!scriptName){
            throw new IllegalStateException('The scriptName was not defined in setup()')
        }
        if(! new File("$scriptDir/$scriptName").exists()){
            throw new IllegalArgumentException("$scriptName not found. No such file in $scriptDir?")
        }
        this.scriptName = scriptName
        bindVariables()
    }

    def bindVariables = {
        binding.setVariable 'params', [:]
        binding.setVariable 'headers', [:]
        binding.setVariable 'request', [:]
        binding.setVariable 'forward', { forward = it }
        binding.setVariable 'redirect', { redirect = it }
        binding.setVariable 'log', log
    }

    void get(){
        binding.request.method = 'GET'
        run()
    }

    void post(){
        binding.request.method = 'POST'
        run()
    }

    void put(){
        binding.request.method = 'PUT'
        run()
    }

    void delete(){
        binding.request.method = 'DELETE'
        run()
    }

    void run(){
        def result = gse.run scriptName, binding
        logging = this.log.buffer
        println logging
        result
    }

    def propertyMissing(String name, value){
        log.config "Adding $name:$value to the $scriptName binding."
        binding.setVariable name, value
    }

    def propertyMissing(String name){
        binding.getVariable name
    }
}
