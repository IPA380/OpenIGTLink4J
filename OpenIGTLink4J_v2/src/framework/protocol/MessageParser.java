/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package protocol;

import msg.OpenIGTMessage;
import msg.capability.CapabilityMessage;
import msg.capability.GetCapabilityMessage;
import msg.command.CommandMessage;
import msg.image.GetImageMessage;
import msg.image.ImageMessage;
import msg.image.RTSImageMessage;
import msg.image.STPImageMessage;
import msg.image.STTImageMessage;
import msg.ndarray.NDArrayMessage;
import msg.point.GetPointMessage;
import msg.point.PointMessage;
import msg.position.GetPositionMessage;
import msg.position.PositionMessage;
import msg.position.RTSPositionMessage;
import msg.position.STPPositionMessage;
import msg.position.STTPositionMessage;
import msg.sensor.GetSensorMessage;
import msg.sensor.RTSSensorMessage;
import msg.sensor.STPSensorMessage;
import msg.sensor.STTSensorMessage;
import msg.sensor.SensorMessage;
import msg.status.GetStatusMessage;
import msg.status.RTSStatusMessage;
import msg.status.STPStatusMessage;
import msg.status.STTStatusMessage;
import msg.status.StatusMessage;
import msg.string.GetStringMessage;
import msg.string.StringMessage;
import msg.track.qtdata.GetQuaternionTrackingDataMessage;
import msg.track.qtdata.QuaternionTrackingDataMessage;
import msg.track.qtdata.RTSQuaternionTrackingDataMessage;
import msg.track.qtdata.STPQuaternionTrackingDataMessage;
import msg.track.qtdata.STTQuaternionTrackingDataMessage;
import msg.track.tdata.GetTrackingDataMessage;
import msg.track.tdata.RTSTrackingDataMessage;
import msg.track.tdata.STPTrackingDataMessage;
import msg.track.tdata.STTTrackingDataMessage;
import msg.track.tdata.TrackingDataMessage;
import msg.trajectory.GetTrajectoryMessage;
import msg.trajectory.TrajectoryMessage;
import msg.transform.GetTransformMessage;
import msg.transform.RTSTransformMessage;
import msg.transform.STPTransformMessage;
import msg.transform.STTTransformMessage;
import msg.transform.TransformMessage;
import util.Header;

/**
 * Class used to parse OpenIGTLink messages 
 * 
 * @author Andreas Rothfuss
 *
 */
public class MessageParser {

	/**
	 * Method to parse a {@link Header} and the message body bytes 
	 * into a {@link OpenIGTMessage}
	 * 
	 * @param header
	 * 		the {@link Header} of the message to be parsed
	 * @param body
	 * 		the body of the message to be parsed
	 * @return
	 * 		the parsed {@link OpenIGTMessage}
	 */
	public OpenIGTMessage parse(Header header, byte[] body) {
			String dataType;
			/** Protocol Version 1 **/
			dataType = header.getDataType();
				/** Version 1 **/
			
				/* CAPABILITY */
				if ( dataType.equals(CapabilityMessage.DATA_TYPE) )
					return new CapabilityMessage(header, body);
				if ( dataType.equals(GetCapabilityMessage.DATA_TYPE) )
					return new GetCapabilityMessage(header, body);
	
				/* IMAGE */	
				if ( dataType.equals(ImageMessage.DATA_TYPE) )
					return new ImageMessage(header, body);
				if ( dataType.equals(GetImageMessage.DATA_TYPE) )
					return new GetImageMessage(header, body);
				if ( dataType.equals(STTImageMessage.DATA_TYPE) )
					return new STTImageMessage(header, body);
				if ( dataType.equals(STPImageMessage.DATA_TYPE) )
					return new STPImageMessage(header, body);
				if ( dataType.equals(RTSImageMessage.DATA_TYPE) )
					return new RTSImageMessage(header, body);
	
				/* POSITION */	
				if ( dataType.equals(PositionMessage.DATA_TYPE) )
					return new PositionMessage(header, body);
				if ( dataType.equals(GetPositionMessage.DATA_TYPE) )
					return new GetPositionMessage(header, body);
				if ( dataType.equals(STTPositionMessage.DATA_TYPE) )
					return new STTPositionMessage(header, body);
				if ( dataType.equals(STPPositionMessage.DATA_TYPE) )
					return new STPPositionMessage(header, body);
				if ( dataType.equals(RTSPositionMessage.DATA_TYPE) )
					return new RTSPositionMessage(header, body);
	
				/* QTRANS */	
				if ( dataType.equals(PositionMessage.OLD_DATA_TYPE) )
					return new PositionMessage(header, body);
				if ( dataType.equals(GetPositionMessage.OLD_DATA_TYPE) )
					return new GetPositionMessage(header, body);
				if ( dataType.equals(STTPositionMessage.OLD_DATA_TYPE) )
					return new STTPositionMessage(header, body);
				if ( dataType.equals(STPPositionMessage.OLD_DATA_TYPE) )
					return new STPPositionMessage(header, body);
				if ( dataType.equals(RTSPositionMessage.OLD_DATA_TYPE) )
					return new RTSPositionMessage(header, body);
	
				/* STATUS */	
				if ( dataType.equals(StatusMessage.DATA_TYPE) )
					return new StatusMessage(header, body);
				if ( dataType.equals(GetStatusMessage.DATA_TYPE) )
					return new GetStatusMessage(header, body);
				if ( dataType.equals(STTStatusMessage.DATA_TYPE) )
					return new STTStatusMessage(header, body);
				if ( dataType.equals(STPStatusMessage.DATA_TYPE) )
					return new STPStatusMessage(header, body);
				if ( dataType.equals(RTSStatusMessage.DATA_TYPE) )
					return new RTSStatusMessage(header, body);
	
				/* TRANSFORM */		
				if ( dataType.equals(TransformMessage.DATA_TYPE) )
					return new TransformMessage(header, body);
				if ( dataType.equals(GetTransformMessage.DATA_TYPE) )
					return new GetTransformMessage(header, body);
				if ( dataType.equals(STTTransformMessage.DATA_TYPE) )
					return new STTTransformMessage(header, body);
				if ( dataType.equals(STPTransformMessage.DATA_TYPE) )
					return new STPTransformMessage(header, body);
				if ( dataType.equals(RTSTransformMessage.DATA_TYPE) )
					return new RTSTransformMessage(header, body);
	
				/** Version 2 **/
	
				/* BIND 
				TODO: Implement BindMessage	
						if ( dataType.equals(BindMessage.DATA_TYPE) )
							return new BindMessage(header, body);
				TODO: Implement GetBindMessage
						if ( dataType.equals(GetBindMessage.DATA_TYPE) )
							return new GetBindMessage(header, body);
				TODO: Implement STT_BindMessage	
						if ( dataType.equals(STTBindMessage.DATA_TYPE) )
							return new STTBindMessage(header, body);
				TODO: Implement STP_BindMessage	
						if ( dataType.equals(STPBindMessage.DATA_TYPE) )
							return new STPBindMessage(header, body);
				TODO: Implement RTS_BindMessage	
						if ( dataType.equals(RTSBindMessage.DATA_TYPE) )
							return new RTSBindMessage(header, body);	
	
				/* ColorTable 		
				TODO: Implement ColorTableMessage	
							if ( dataType.equals(ColorTableMessage.DATA_TYPE) )
								return new ColorTableMessage(header, body);
				TODO: Implement GetColorTableMessage
							if ( dataType.equals(GetColorTableMessage.DATA_TYPE) )
								return new GetColorTableMessage(header, body);	
	
				/* ImageMeta  
				TODO: Implement ImageMetaMessage		
							if ( dataType.equals(ImageMetaMessage.DATA_TYPE) )
								return new ImageMetaMessage(header, body);
				TODO: Implement GetImageMetaMessage
							if ( dataType.equals(GetImageMetaMessage.DATA_TYPE) )
								return new GetImageMetaMessage(header, body);
	
				/* LabelMeta 	
				TODO: Implement LabelMetaMessage	
							if ( dataType.equals(LabelMetaMessage.DATA_TYPE) )
								return new LabelMetaMessage(header, body);
				TODO: Implement GetLabelMetaMessage
							if ( dataType.equals(GetLabelMetaMessage.DATA_TYPE) )
								return new GetLabelMetaMessage(header, body);
				*/
	
				/* NDARRAY */		
				if ( dataType.equals(NDArrayMessage.DATA_TYPE) )
					return new NDArrayMessage(header, body);
				/*
				TODO: Implement GetNDArrayMessage
							if ( dataType.equals(GetNDArrayMessage.DATA_TYPE) )
								return new GetNDArrayMessage(header, body);
				*/
	
				/* POINT */		
				if ( dataType.equals(PointMessage.DATA_TYPE) )
					return new PointMessage(header, body);
				if ( dataType.equals(GetPointMessage.DATA_TYPE) )
					return new GetPointMessage(header, body);
	
				/* POLYDATA
				TODO: Implement PolyDataMessage	
							if ( dataType.equals(PolyDataMessage.DATA_TYPE) )
								return new PolyDataMessage(header, body);
				TODO: Implement GetPolyDataMessage
							if ( dataType.equals(GetPolyDataMessage.DATA_TYPE) )
								return new GetPolyDataMessage(header, body);
				TODO: Implement STT_PolyDataMessage
							if ( dataType.equals(STTPolyDataMessage.DATA_TYPE) )
								return new STTPolyDataMessage(header, body);
				TODO: Implement STP_PolyDataMessage
							if ( dataType.equals(STPPolyDataMessage.DATA_TYPE) )
								return new STPPolyDataMessage(header, body);
				TODO: Implement RTS_PolyDataMessage
							if ( dataType.equals(RTSPolyDataMessage.DATA_TYPE) )
								return new RTSPolyDataMessage(header, body);	
				*/
	
				/* QuaternionTrackingData */		
				if ( dataType.equals(QuaternionTrackingDataMessage.DATA_TYPE) )
					return new QuaternionTrackingDataMessage(header, body);
				if ( dataType.equals(GetQuaternionTrackingDataMessage.DATA_TYPE) )
					return new GetQuaternionTrackingDataMessage(header, body);
				if ( dataType.equals(STTQuaternionTrackingDataMessage.DATA_TYPE) )
					return new STTQuaternionTrackingDataMessage(header, body);
				if ( dataType.equals(STPQuaternionTrackingDataMessage.DATA_TYPE) )
					return new STPQuaternionTrackingDataMessage(header, body);
				if ( dataType.equals(RTSQuaternionTrackingDataMessage.DATA_TYPE) )
					return new RTSQuaternionTrackingDataMessage(header, body);	
	
				/* SENSOR */		
				if ( dataType.equals(SensorMessage.DATA_TYPE) )
					return new SensorMessage(header, body);
				if ( dataType.equals(GetSensorMessage.DATA_TYPE) )
					return new GetSensorMessage(header, body);
				if ( dataType.equals(STTSensorMessage.DATA_TYPE) )
					return new STTSensorMessage(header, body);
				if ( dataType.equals(STPSensorMessage.DATA_TYPE) )
					return new STPSensorMessage(header, body);
				if ( dataType.equals(RTSSensorMessage.DATA_TYPE) )
					return new RTSSensorMessage(header, body);	
	
				/* STRING */		
				if ( dataType.equals(StringMessage.DATA_TYPE) )
					return new StringMessage(header, body);
				if ( dataType.equals(GetStringMessage.DATA_TYPE) )
					return new GetStringMessage(header, body);
	
				/* TrackingData  */		
				if ( dataType.equals(TrackingDataMessage.DATA_TYPE) )
					return new TrackingDataMessage(header, body);
				if ( dataType.equals(GetTrackingDataMessage.DATA_TYPE) )
					return new GetTrackingDataMessage(header, body);
	
				/* TRAJ */		
				if ( dataType.equals(TrajectoryMessage.DATA_TYPE) )
					return new TrajectoryMessage(header, body);
				if ( dataType.equals(GetTrajectoryMessage.DATA_TYPE) )
					return new GetTrajectoryMessage(header, body);
				if ( dataType.equals(STTTrackingDataMessage.DATA_TYPE) )
					return new STTTrackingDataMessage(header, body);
				if ( dataType.equals(STPTrackingDataMessage.DATA_TYPE) )
					return new STPTrackingDataMessage(header, body);
				if ( dataType.equals(RTSTrackingDataMessage.DATA_TYPE) )
					return new RTSTrackingDataMessage(header, body);	
					
				/** Version 3 **/
			
				/* COMMAND */
				if (dataType.equals(CommandMessage.DATA_TYPE) )
					return new CommandMessage(header, body);
				
				return null;		
		}

}
