
import msg.OIGTL_DataMessage;
import msg.OIGTL_GetMessage;
import msg.OpenIGTMessage;
import msg.transform.GetTransformMessage;
import msg.transform.TransformMessage;
import network.OpenIGTLinkClient;
import protocol.MessageParser;

/**
 * Example class to illustrate the use of {@link OpenIGTLinkClient} 
 * as a base class for a client that implements the OpenIGTLink query
 * mechanism for {@link TransformMessage}
 * 
 * @author Andreas Rothfuss
 *
 */
public class OIGTL_TransformClient extends OpenIGTLinkClient{

	/** Member to store the last Transform message that has been received */
	TransformMessage lastTransfromReceived = null;

	/**
	 * Destination constructor
	 * 
	 * @param ipAdress
	 * 		IP-address, the client will try to connect to
	 * @param port
	 * 		port, the client will try to connect to
	 */
	public OIGTL_TransformClient(String ipAdress, int port, 
			MessageParser messageParser) {
		super(ipAdress, port, messageParser);
	}
	
	@Override
	public void messageReceived(OpenIGTMessage message) {
		log.debug("Message received: " + message.toString());
		
		/* check message data type and do something with the message */
		if (message instanceof TransformMessage) {
			/* store the last last received transform */
			lastTransfromReceived = (TransformMessage)message;
		}
	}

	@Override
	public OIGTL_DataMessage getMessageReceived(OIGTL_GetMessage message) {
		log.debug("Message received: " + message.toString());

		/* check message data type and repy with a proper message*/
		if (message instanceof GetTransformMessage) {
			/* if the device name fits that of the last transfrom received
			 * return that*/
			if (message.getDeviceName().equals(lastTransfromReceived.getDeviceName())) {
				return lastTransfromReceived;
			}
		}
		
		return null;		
	}

	@Override
	public String[] getCapability() {
		return new String[] {
			TransformMessage.DATA_TYPE,
			GetTransformMessage.DATA_TYPE
		};
	}

}
