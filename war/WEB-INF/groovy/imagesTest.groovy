//images
assert images
assert params.path

log.info params.path

// retrieve an image stored in the blobstore
def image = new File(params.path).image
 
// apply a resize transform on the image to create a thumbnail
def thumbnail = images.applyTransform(images.makeResize(260, 260), image)
 
// serve the binary data of the image to the servlet output stream
sout << (thumbnail.imageData)