import static com.google.appengine.api.capabilities.Capability.*
import static com.google.appengine.api.capabilities.CapabilityStatus.*

//http methods with forward and redirect
get "/about", redirect: "/blog/2008/10/20/welcome-to-my-blog"
post "/other", forward: "/blog/2008/10/20/welcome-to-my-other-blog"
put "/my-put", forward: "/myput"
delete "/my-delete", forward: "/mydelete"
all "/my-all", redirect: "/myall", cache: 30.seconds
get "/article/@year/@month/@day/@title", forward: "/article.groovy?year=@year&month=@month&day=@day&title=@title"
get "/number/@number", forward: "/number.groovy", validate: { number.isNumber() }, cache: 2.hours
all "/validate/request", forward: "/validRequest.groovy", validate: { request.registered == true }

//forward to groovlet
post "/new-article", forward: "/post.groovy"

//email and jabber routing rules
email to: "/receiveEmail.groovy"

jabber chat, to: "/chatJabber.groovy"
jabber subscription, to: "/subscribeJabber.groovy"
jabber presence, to: "/presenceJabber.groovy"

//ignored
all "/_ah/**", ignore: true

all '/withWildcard/*', forward: '/defaultSingleStar.groovy'
all '/withWildcard/**', forward: '/defaultDoubleStar.groovy'

//capability routing
get "/update", forward: {
	to "/update.groovy"
	to "/maintenance.gtpl" on DATASTORE not ENABLED
	to "/readonly.gtpl" on DATASTORE_WRITE not ENABLED
}

//namespaced routing
post "/update/@namespace", forward: "/namespacedUpdate.groovy", namespace: { "namespace-$namespace" }
