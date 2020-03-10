import java.io.IOException;

import msg.OIGTL_RTSMessage;
import msg.OIGTL_STTMessage;
import msg.sensor.RTSSensorMessage;
import msg.sensor.STPSensorMessage;
import msg.sensor.STTSensorMessage;
import msg.sensor.SensorMessage;
import network.IOpenIGTMessageSender;
import network.stream.OpenIGTLinkStreamingClient;
import network.stream.StreamRunner;
import util.RTSMessageStatus;

/**
 * Example class to illustrate the use of {@link OpenIGTLinkStreamingClient} 
 * as a base class for a client that implements the OpenIGTLink streaming
 * mechanism for {@link SensorMessage}
 * 
 * @author Andreas Rothfuss
 *
 */
public class OIGTL_SensorStreamClient extends OpenIGTLinkStreamingClient{

	/**
	 * Destination constructor
	 * 
	 * @param ipAdress
	 * 		IP-address the client will try to connect to
	 * @param port
	 * 		port the client will try to connect to
	 */
	public OIGTL_SensorStreamClient(String ipAdress, int port) {
		super(ipAdress, port);
	}

	@Override
	public OIGTL_RTSMessage sttMessageReceived(OIGTL_STTMessage message, IOpenIGTMessageSender replyTo) {
		/* Log the message */
		log.debug("Message received: " + message.toString());

		/* filter by message type */
		if ( message instanceof STTSensorMessage) {
			/* react to STT sensor message
			 * Create a new implementation of @StreamRunner */
			StreamRunner sensorStreamRunner = new StreamRunner(message.getDeviceName(), replyTo) {
				/* Override method send reply (this will be called regularly) */
				@Override
				protected void sendReply() {
					/* send a reply message
					 * be sure to catch Exceptions that might occur because a 
					 * uncaught Exception will terminate the scheduled execution*/
					try { 
						replyTo.send(new SensorMessage(deviceName)); 
					} catch (IOException e) {e.printStackTrace(); }
				}
			};
			
			/* schedule the new instance of stream runner to be called regularly with a period of
			 * message.getResolution();*/
			scheduleStreamRunner(message.getResolution(), sensorStreamRunner);
			
			return new RTSSensorMessage(message.getDeviceName(), RTSMessageStatus.Success);
		}
		return null;
	}

	@Override
	public String[] getCapability() {
		return new String[] {
				STTSensorMessage.DATA_TYPE,
				STPSensorMessage.DATA_TYPE
			};
	}

}
