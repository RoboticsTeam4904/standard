package org.usfirst.frc4904.standard.custom.sensors;


import java.nio.ByteBuffer;
import org.usfirst.frc4904.standard.custom.CustomCAN;

/**
 * A sensor over CAN
 *
 */
public class CANSensor extends CustomCAN {
	private final int[] cachedValues;
	
	/**
	 *
	 * @param name
	 *        Name of CAN sensor (not really needed)
	 * @param id
	 *        ID of CAN sensor (0x600 to 0x700, must correspond to a Teensy or similar)
	 * @param modes
	 *        Number of modes for the CAN sensor
	 */
	public CANSensor(String name, int id, int modes) {
		super(name, id);
		cachedValues = new int[modes];
		for (int i = 0; i < modes; i++) {
			cachedValues[i] = 0; // Should we make this a more obscure value (e.g. 2^32 - 1)?
		}
	}
	
	/**
	 *
	 * @param name
	 *        Name of CAN sensor
	 * @param id
	 *        ID of CAN sensor (0x400 to 0x500, should correspond to a Teensy or similar)
	 */
	public CANSensor(String name, int id) {
		this(name, id, 1);
	}
	
	/**
	 * Mode determines what signal from the CAN node to look for. The first int
	 * is 0 if the data was returned correctly and -1 if no data was returned
	 *
	 * @param mode
	 * @return
	 */
	public int read(int mode, int retryMax) {
		super.write(new byte[] {(byte) ((byte) mode & 0xFF), 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01}); // Write to trigger read
		for (int i = 0; i < retryMax; i++) {
			ByteBuffer rawData = super.readBuffer();
			if (rawData != null && rawData.remaining() > 7) {
				rawData.rewind();
				long data = Long.reverseBytes(rawData.getLong());
				int value = (int) (data & 0xFFFFFFFF);
				int msgMode = (int) (data >> 32);
				if (msgMode <= cachedValues.length) {
					cachedValues[msgMode] = value;
				}
				if (msgMode == mode) {
					return value;
				}
			}
		}
		return cachedValues[mode];
	}
	
	/**
	 * Default read (retries 10 times)
	 *
	 * @param mode
	 * @return
	 */
	public int read(int mode) {
		return read(mode, 10);
	}
}
