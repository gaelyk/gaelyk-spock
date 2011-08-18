//params
assert params.param1
assert params.param2

//out
out.write 'hello'

//response
response.setHeader 'HEADER', 'TEST'

//logging
log.severe 'severe log'
log.warning 'warning log'
log.info 'info log'
log.config 'config log'
log.fine 'fine log'
log.finer 'finer log'
log.finest 'finest log'

//request
request << params
request.param3 = 'three'

if(params.redirectme){
	log.info 'In the redirect block...'
	redirect 'some.groovy'
} else {
	log.info 'In the forward block...'
	forward '/WEB-INF/pages/some.gtpl'
}

