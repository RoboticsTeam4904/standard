package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.LogKitten;

public class CANUltrasonicDistanceSensor extends CANSensor implements DistanceSensor {
	public static final int DISTANCE_SENSOR_ARRAY_INDEX = 0;

	/**
	 * Construct a new Ultrasonic Distance Sensor connected via CAN
	 *
	 * @param name
	 *        name of the CAN sensor
	 * @param id
	 *        ID of CAN sensor (0x600 to 0x700, must correspond to a Teensy or similar)
	 */
	public CANUltrasonicDistanceSensor(String name, int id) {
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
		return super.readSensor()[CANInfraredDistanceSensor.DISTANCE_SENSOR_ARRAY_INDEX];
	}
}
