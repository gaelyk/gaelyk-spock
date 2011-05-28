//memcache
assert memcache
memcache.put('person', person)

//mail
assert mail
mail.send sender: params.sender, 
	to: "recipient@somecompany.com",
	subject: "Hello",
	textBody: "Hello, how are you doing? -- MrG"

//urlFetch
assert urlFetch
def response = urlFetch.fetch(new URL(params.url))
log.info response
request.result = response
