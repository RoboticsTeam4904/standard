package org.usfirst.frc4904.standard.custom;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.hal.can.CANJNI;
import edu.wpi.first.hal.can.CANMessageNotFoundException;
import edu.wpi.first.hal.util.UncleanStatusException;

/**
 * This class allows sending and receiving of messages over CAN to a specific
 * ID.
 *
 */
public class CustomCAN {
	// Because CANJNI is basically static, we do not extend it.
	protected final int messageID;
	protected final String name;

	/**
	 * Constructor for a CustomCAN device. The name is local and for your
	 * convenience only. The id should be the same as the id programmed into the CAN
	 * device
	 *
	 * @param name
	 * @param id   ID of CAN device (0x600 to 0x700, corresponds to a Teensy)
	 */
	public CustomCAN(String name, int id) {
		this.name = name;
		messageID = id; // Ensure that the messageID is zeroed (32 bit int should be default, but better
						// to be careful)
	}

	public String getName() {
		return name;
	}

	/**
	 * Used to write data to the device.
	 *
	 * @param data Data to be written. Should be EXACTLY 8 bytes long ONLY.
	 */
	public void write(byte[] data) {
		try {
			writeSafely(data);
		} catch (UncleanStatusException e) {
			LogKitten.ex(e);
		}
	}

	/**
	 * Used to write data to the device.
	 *
	 * @param data Data to be written. Should be EXACTLY 8 bytes long ONLY.
	 * @throws UncleanStatusException
	 */
	public void writeSafely(byte[] data) {
		CANJNI.FRCNetCommCANSessionMuxSendMessage(messageID, data, CANJNI.CAN_SEND_PERIOD_NO_REPEAT);
	}

	/**
	 * Read data as bytebuffer
	 *
	 * @return ByteBuffer containing CAN message, or null
	 * @throws CANMEssageNotFoundException when no new message is available
	 */
	protected byte[] readBuffer() throws CANMessageUnavailableException {
		IntBuffer idBuffer = ByteBuffer.allocateDirect(4).asIntBuffer();
		idBuffer.clear();
		idBuffer.put(0, Integer.reverseBytes(messageID));
		ByteBuffer timestamp = ByteBuffer.allocate(4);
		try {
			return CANJNI.FRCNetCommCANSessionMuxReceiveMessage(idBuffer, 0x1fffffff, timestamp);
		} catch (CANMessageNotFoundException e) {
			throw new CANMessageUnavailableException(
					"Unable to read CAN device " + getName() + " with ID 0x" + Integer.toHexString(messageID), e);
		}
	}

	/**
	 * Reads data Also stops repeating the last message.
	 *
	 * @return byte[] (8 long)
	 */
	public byte[] readSafely() throws CANMessageUnavailableException {
		return readBuffer();
	}

	/**
	 * Reads data, returning an empty Optional if there is no available message.
	 * 
	 * @return Optional<byte[]>
	 */
	public Optional<byte[]> read() {
		try {
			return Optional.of(readSafely());
		} catch (CANMessageUnavailableException e) {
			return Optional.empty();
		}
	}
}
