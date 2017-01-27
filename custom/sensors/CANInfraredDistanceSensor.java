package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.LogKitten;

/**
 * Infrared Distance Sensor connected via CAN
 * assumes mode containing distance values is 0
 */
public class CANInfraredDistanceSensor extends CANSensor implements DistanceSensor {
	public static final int DISTANCE_SENSOR_ARRAY_INDEX = 0;

	/**
	 * Construct a new Infrared Distance Sensor connected via CAN
	 *
	 * @param name
	 *        name of the CAN sensor
	 * @param id
	 *        ID of CAN sensor (0x600 to 0x700, must correspond to a Teensy or similar)
	 */
	public CANInfraredDistanceSensor(String name, int id) {
		super(name, id);
	}

	@Override
	public double getDistance() {
		try {
			return getDistanceSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}

	@Override
	public double getDistanceSafely() throws InvalidSensorException {
		int value = readSensor()[CANInfraredDistanceSensor.DISTANCE_SENSOR_ARRAY_INDEX];
		LogKitten.d(name + " read value " + value);
		return value;
	}
}
