assert files != null

// let's first create a new blob file through the regular FileService method
def file = files.createNewBlobFile("text/plain", params.name)
 
file.withWriter(encoding: "US-ASCII", locked: false, finalize: false) { writer ->
	writer << params.text
}


log.info file.namePart
