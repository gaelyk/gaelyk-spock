import com.google.appengine.api.xmpp.*

JID jid = new JID("example@gmail.com")
String msgBody = "Someone has sent you a gift on Example.com. To view: http://example.com/gifts/"
Message msg = new MessageBuilder()
	.withRecipientJids(jid)
	.withBody(msgBody)
	.build()
                
boolean messageSent = false
if (xmpp.getPresence(jid).isAvailable()) {
	SendResponse status = xmpp.sendMessage(msg)
	messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS)
}

request.success = messageSent
