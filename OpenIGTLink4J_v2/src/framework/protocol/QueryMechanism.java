/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package protocol;

import msg.capability.CapabilityMessage;
import msg.capability.GetCapabilityMessage;
import msg.image.GetImageMessage;
import msg.image.ImageMessage;
import msg.image.RTSImageMessage;
import msg.image.STPImageMessage;
import msg.image.STTImageMessage;
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

/**
 * Class used get the correct reply data type for the OpenIGTLink 
 * query mechanism
 * 
 * @author Andreas Rothfuss
 *
 */
public class QueryMechanism {

	/**
	 * Method to get the correct reply data type for a messages 
	 * data type
	 * 
	 * @param dataType
	 * 		the data type to find the reply data type to
	 * @return
	 * 		the correct reply data type
	 */
	public static String getReplyDataType(String dataType) {
		/** Version 1 **/
		
		/* CAPABILITY */
		if ( dataType.equals(GetCapabilityMessage.DATA_TYPE) )
			return CapabilityMessage.DATA_TYPE;

		/* IMAGE */	
		if ( dataType.equals(GetImageMessage.DATA_TYPE) )
			return ImageMessage.DATA_TYPE;
		if ( dataType.equals(STTImageMessage.DATA_TYPE) || dataType.equals(STPImageMessage.DATA_TYPE))
			return RTSImageMessage.DATA_TYPE;

		/* POSITION */	
		if ( dataType.equals(GetPositionMessage.DATA_TYPE) )
			return PositionMessage.DATA_TYPE;
		if ( dataType.equals(STTPositionMessage.DATA_TYPE) || dataType.equals(STPPositionMessage.DATA_TYPE))
			return RTSPositionMessage.DATA_TYPE;

		/* QTRANS */	
		if ( dataType.equals(GetPositionMessage.OLD_DATA_TYPE) )
			return PositionMessage.OLD_DATA_TYPE;
		if ( dataType.equals(STTPositionMessage.OLD_DATA_TYPE) || dataType.equals(STPPositionMessage.OLD_DATA_TYPE))
			return RTSPositionMessage.OLD_DATA_TYPE;

		/* STATUS */	
		if ( dataType.equals(GetStatusMessage.DATA_TYPE) )
			return StatusMessage.DATA_TYPE;
		if ( dataType.equals(STTStatusMessage.DATA_TYPE) || dataType.equals(STPStatusMessage.DATA_TYPE))
			return RTSStatusMessage.DATA_TYPE;

		/* TRANSFORM */		
		if ( dataType.equals(GetTransformMessage.DATA_TYPE) )
			return TransformMessage.DATA_TYPE;
		if ( dataType.equals(STTTransformMessage.DATA_TYPE) || dataType.equals(STPTransformMessage.DATA_TYPE))
			return RTSTransformMessage.DATA_TYPE;

		/** Version 2 **/

		/* BIND */		
//				if ( dataType.equals(GetBindMessage.DATA_TYPE) )
//					return BindMessage.DATA_TYPE;

			/* ColorTable */		
//					if ( dataType.equals(GetColorTableMessage.DATA_TYPE) )
//						return ColorTableMessage.DATA_TYPE;	

			/* ImageMeta  */		
//					if ( dataType.equals(GetImageMetaMessage.DATA_TYPE) )
//						return ImageMetaMessage.DATA_TYPE;

			/* LabelMeta  */		
//					if ( dataType.equals(GetLabelMetaMessage.DATA_TYPE) )
//						return LabelMetaMessage.DATA_TYPE;

		/* NDARRAY */		
//					if ( dataType.equals(GetNDArrayMessage.DATA_TYPE) )
//						return NDArrayMessage.DATA_TYPE;

		/* POINT */		
		if ( dataType.equals(GetPointMessage.DATA_TYPE) )
			return PointMessage.DATA_TYPE;
//		if ( dataType.equals(STTPointMessage.DATA_TYPE) || dataType.equals(STPPointMessage.DATA_TYPE))
//			return RTSPointMessage.DATA_TYPE;

			/* POLYDATA */		
//					if ( dataType.equals(GetPolyDataMessage.DATA_TYPE) )
//						return PolyDataMessage.DATA_TYPE;

		/* QuaternionTrackingData */		
		if ( dataType.equals(GetQuaternionTrackingDataMessage.DATA_TYPE) )
			return QuaternionTrackingDataMessage.DATA_TYPE;
		if ( dataType.equals(STTQuaternionTrackingDataMessage.DATA_TYPE) || dataType.equals(STPQuaternionTrackingDataMessage.DATA_TYPE))
			return RTSQuaternionTrackingDataMessage.DATA_TYPE;

		/* SENSOR */		
		if ( dataType.equals(GetSensorMessage.DATA_TYPE) )
			return SensorMessage.DATA_TYPE;
		if ( dataType.equals(STTSensorMessage.DATA_TYPE) || dataType.equals(STPSensorMessage.DATA_TYPE))
			return RTSSensorMessage.DATA_TYPE;

		/* STRING */		
		if ( dataType.equals(GetStringMessage.DATA_TYPE) )
			return StringMessage.DATA_TYPE;
//		if ( dataType.equals(STTStringMessage.DATA_TYPE) || dataType.equals(STPStringMessage.DATA_TYPE))
//			return RTSStringMessage.DATA_TYPE;

		/* TrackingData  */		
		if ( dataType.equals(GetTrackingDataMessage.DATA_TYPE) )
			return TrackingDataMessage.DATA_TYPE;
		if ( dataType.equals(STTTrackingDataMessage.DATA_TYPE) || dataType.equals(STPTrackingDataMessage.DATA_TYPE))
			return RTSTrackingDataMessage.DATA_TYPE;

		/* TRAJ */		
		if ( dataType.equals(GetTrajectoryMessage.DATA_TYPE) )
			return TrajectoryMessage.DATA_TYPE;
			
		/** Version 3 **/
	
		/* COMMAND */
		
		return null;
	}

}
