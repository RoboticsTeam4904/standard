package org.usfirst.frc4904.standard.custom;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.can.CANJNI;
import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

public class CustomCAN implements Named{
	// Because CANJNI is basically static, we do not extend it.
	
	private final int messageID;
	private final String name;
	
	/**
	 * Constructor for a CustomCAN device.
	 * The name is local and for your convenience only.
	 * The id should be the same as the id programmed into the CAN device
	 * 
	 * @param name
	 * @param id
	 */
	public CustomCAN(String name, int id){
		this.name = name;
		this.messageID = id;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Used to send a message with the message id and data data.
	 * Data should be 8 bytes ONLY.
	 * @param data
	 */
	public void write(byte[] data){
		ByteBuffer canData = ByteBuffer.allocateDirect(8);
		canData.clear();
		for(int i = 0; i < 8; i++){
			canData.put(i, data[i]);
		}
		
		CANJNI.FRCNetworkCommunicationCANSessionMuxSendMessage(messageID, canData, CANJNI.CAN_SEND_PERIOD_NO_REPEAT);
	}
	
	protected ByteBuffer readBuffer(){
		IntBuffer idBuffer = ByteBuffer.allocateDirect(4).asIntBuffer();
		idBuffer.clear();
		idBuffer.put(0, messageID);
		
		ByteBuffer timestamp = ByteBuffer.allocate(4);
		
		try {
			return CANJNI.FRCNetworkCommunicationCANSessionMuxReceiveMessage(idBuffer, CANJNI.CAN_MSGID_FULL_M, timestamp); 
		}
		catch(CANMessageNotFoundException e){
			return null;
		}
	}
	
	/**
	 * 
	 * @return byte[] (8 long)
	 */
	public byte[] read(){
		ByteBuffer dataBuffer = readBuffer();
		
		if(dataBuffer != null){
			if (dataBuffer.remaining() > 0){
				dataBuffer.rewind();
				byte[] data = new byte[dataBuffer.remaining()];
				
				for(int i = 0; i < dataBuffer.remaining(); i++){
					data[i] = dataBuffer.get(i);
				}
				
				return data;
			}
		}
		return null;
	}

}
