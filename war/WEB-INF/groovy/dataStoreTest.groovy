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
