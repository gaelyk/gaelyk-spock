def clientId = params.clientId
def message = params.message

def token = channel.createChannel(clientId)
def channelMessage = channel.parseMessage(request)
channel.sendMessage channelMessage
