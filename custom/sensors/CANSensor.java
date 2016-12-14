package org.usfirst.frc4904.standard.custom.sensors;


import java.nio.ByteBuffer;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.CustomCAN;

/**
 * A sensor over CAN
 *
 */
public class CANSensor extends CustomCAN {
	private final int[] values;
	private final long[] ages;
	private final long MAX_AGE = 1000;

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
		values = new int[modes];
		ages = new long[modes];
		for (int i = 0; i < modes; i++) {
			values[i] = 0; // Should we make this a more obscure value (e.g. 2^32 - 1)?
			ages[i] = System.currentTimeMillis();
		}
	}

	/**
	 *
	 * @param name
	 *        Name of CAN sensor
	 * @param id
	 *        ID of CAN sensor (0x600 to 0x700, should correspond to a Teensy or similar)
	 */
	public CANSensor(String name, int id) {
		this(name, id, 1);
	}

	/**
	 * Read an int from a CAN sensor with retries
	 * Retries are now ignored
	 *
	 * @param mode
	 *        Which mode to read the sensor in (interpreted by the Teensy)
	 * @return
	 * 		The integer the Teensy returned for that mode
	 *
	 * @throws InvalidSensorException
	 *         If the available data is more than one second old,
	 *         this function will throw an InvalidSensorException
	 *         to indicate that.
	 */
	@Deprecated
	public int read(int mode, int retryMax) throws InvalidSensorException {
		return read(mode);
	}

	/**
	 * Read an int from a CAN sensor
	 *
	 * @param mode
	 *        Which mode to read the sensor in (interpreted by the Teensy)
	 * @return
	 * 		The integer the Teensy returned for that mode
	 *
	 * @throws InvalidSensorException
	 *         If the available data is more than one second old,
	 *         this function will throw an InvalidSensorException
	 *         to indicate that.
	 */
	public int read(int mode) throws InvalidSensorException {
		write(new byte[] {(byte) ((byte) mode & 0xFF), 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01}); // Write to trigger read
		ByteBuffer rawData = readBuffer();
		if (rawData != null && rawData.remaining() > 7) {
			rawData.rewind();
			long data = Long.reverseBytes(rawData.getLong());
			int value = (int) (data & 0xFFFFFFFF);
			int msgMode = (int) (data >> 32);
			if (msgMode == mode) {
				ages[mode] = System.currentTimeMillis();
				values[mode] = value;
				return values[mode];
			}
		}
		if (System.currentTimeMillis() - ages[mode] > MAX_AGE) {
			throw new InvalidSensorException("CAN data oudated For CAN sensor " + getName() + " with ID " + messageID);
		}
		LogKitten.v("Cached Sensor Value Used\n");
		return values[mode];
	}
}
