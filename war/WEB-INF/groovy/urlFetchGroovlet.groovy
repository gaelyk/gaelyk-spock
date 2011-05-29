//urlFetch
assert urlFetch
def response = urlFetch.fetch(new URL(params.url))
log.info response
request.result = response
