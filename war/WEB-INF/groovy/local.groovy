import com.google.appengine.api.datastore.*

//datastore
assert datastore
def entity = new Entity('person')
entity.firstname = 'Marco'
entity.lastname = 'Vermeulen'
entity.age = 40
entity.save()

log.info "My name is $entity.firstname $entity.lastname"
log.info "My age is $entity.age"

//memcache
assert memcache
memcache.put('person', entity)

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
