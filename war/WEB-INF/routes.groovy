//http methods with forward and redirect
get "/about", redirect: "/blog/2008/10/20/welcome-to-my-blog"
post "/other", forward: "/blog/2008/10/20/welcome-to-my-other-blog"
put "/my-put", forward: "/myput"
delete "/my-delete", forward: "/mydelete"
all "/my-all", redirect: "/myall"

//forward to groovlet
post "/new-article", forward: "/post.groovy"

//email and jabber routing rules
email  to: "/receiveEmail.groovy"
jabber to: "/receiveJabber.groovy"
//synonyms
jabber chat, to:"/chatJabber.groovy"
jabber subscribe, to:"/subscribeJabber.groovy"
jabber presence, to:"/presenceJabber.groovy"