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
	private long age; // data age
	private final long MAX_AGE = 100;

	/**
	 *
	 * @param name
	 *        Name of CAN sensor (not really needed)
	 * @param id
	 *        ID of CAN sensor (0x600 to 0x700, must correspond to a Teensy or similar)
	 */
	public CANSensor(String name, int id) {
		super(name, id);
		values = new int[2];
		values[0] = 0;
		values[1] = 0;
		age = System.currentTimeMillis();
	}

	/**
	 * Read the pair of ints from a CAN sensor
	 *
	 * @return
	 * 		The latest pair of integers from the sensor
	 *
	 * @throws InvalidSensorException
	 *         If the available data is more than one tenth of a second old,
	 *         this function will throw an InvalidSensorException
	 *         to indicate that.
	 */
	public int[] readSensor() throws InvalidSensorException {
		ByteBuffer rawData = super.readBuffer();
		if (rawData != null && rawData.remaining() > 7) {
			rawData.rewind();
			long data = Long.reverseBytes(rawData.getLong());
			values[0] = (int) data & 0xFFFFFFFF;
			values[1] = (int) (data >> 32) & 0xFFFFFFFF;
			age = System.currentTimeMillis();
			return values;
		}
		if (System.currentTimeMillis() - age > MAX_AGE) {
			throw new InvalidSensorException("CAN data oudated For CAN sensor " + getName());
		}
		LogKitten.v("Cached Sensor Value Used\n");
		return values;
	}
}
