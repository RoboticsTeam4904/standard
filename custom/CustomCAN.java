package org.usfirst.frc4904.standard.custom;


import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import edu.wpi.first.wpilibj.can.CANJNI;
import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

/**
 * This class allows sending and receiving
 * of messages over CAN to a specific ID.
 *
 */
public class CustomCAN {
	// Because CANJNI is basically static, we do not extend it.
	protected final int messageID;
	protected final String name;

	/**
	 * Constructor for a CustomCAN device.
	 * The name is local and for your convenience only.
	 * The id should be the same as the id programmed into the CAN device
	 *
	 * @param name
	 * @param id
	 *        ID of CAN device (0x600 to 0x700, corresponds to a Teensy)
	 */
	public CustomCAN(String name, int id) {
		this.name = name;
		messageID = id; // Ensure that the messageID is zeroed (32 bit int should be default, but better to be careful)
	}

	public String getName() {
		return name;
	}

	/**
	 * Used to write data to the device.
	 *
	 * @param data
	 *        Data to be written. Should be EXACTLY 8 bytes long ONLY.
	 * @throws IllegalArgumentException
	 */
	public void write(byte[] data) {
		ByteBuffer canData = ByteBuffer.allocateDirect(8);
		canData.put(data);
		CANJNI.FRCNetCommCANSessionMuxSendMessage(messageID, canData, CANJNI.CAN_SEND_PERIOD_NO_REPEAT);
	}

	/**
	 * Read data as bytebuffer
	 *
	 * @return
	 * 		ByteBuffer containing CAN message, or null
	 */
	protected ByteBuffer readBuffer() {
		IntBuffer idBuffer = ByteBuffer.allocateDirect(4).asIntBuffer();
		idBuffer.clear();
		idBuffer.put(0, Integer.reverseBytes(messageID));
		ByteBuffer timestamp = ByteBuffer.allocate(4);
		ByteBuffer response = null;
		try {
			response = CANJNI.FRCNetCommCANSessionMuxReceiveMessage(idBuffer, 0x1fffffff, timestamp);
		}
		catch (CANMessageNotFoundException e) {}
		return response;
	}

	/**
	 * Reads data
	 * Also stops repeating the last message.
	 *
	 * @return byte[] (8 long)
	 */
	public byte[] read() {
		ByteBuffer dataBuffer = readBuffer();
		if (dataBuffer == null) {
			return null;
		}
		if (dataBuffer.remaining() <= 0) {
			return null;
		}
		dataBuffer.rewind();
		byte[] data = new byte[dataBuffer.remaining()];
		for (int i = 0; i < dataBuffer.remaining(); i++) {
			data[i] = dataBuffer.get(i);
		}
		return data;
	}
}
