println 'made it here'

get "/about", redirect: "/blog/2008/10/20/welcome-to-my-blog"
post "/other", forward: "/blog/2008/10/20/welcome-to-my-other-blog"