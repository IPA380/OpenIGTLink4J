
import msg.OIGTL_DataMessage;
import msg.OIGTL_GetMessage;
import msg.transform.GetTransformMessage;
import msg.transform.TransformMessage;
import network.OpenIGTLinkServer;
import protocol.MessageParser;

/**
 * Example class to illustrate the use of {@link OpenIGTLinkServer} 
 * as a base class for a server that implements the OpenIGTLink query
 * mechanism for {@link TransformMessage}
 * 
 * @author Andreas Rothfuss
 *
 */
public class OIGTL_TransfromServer extends OpenIGTLinkServer {

	/**
	 * Destination constructor
	 * 
	 * @param port
	 * 		port, the server will be listening to
	 */
	public OIGTL_TransfromServer(int port) {
		super(port, new MessageParser(false));
	}

	@Override
	public OIGTL_DataMessage getMessageReceived(OIGTL_GetMessage message) {
		log.debug("Message received: " + message.toString());

		/* check message data type and repy with a proper message*/
		if (message instanceof GetTransformMessage) {
			TransformMessage msg = new TransformMessage(message.getDeviceName());
			msg.setPositionVector(0, 0, 0);
			return msg;
		}
		
		return null;		
	}

	@Override
	public String[] getCapability() {
		return new String[] {
			GetTransformMessage.DATA_TYPE
		};
	}

}
