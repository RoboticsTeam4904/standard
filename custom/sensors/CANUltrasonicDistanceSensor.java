package org.usfirst.frc4904.standard.custom.sensors;


public class CANUltrasonicDistanceSensor extends CANSensor implements DistanceSensor {
	protected static final int CAN_SENSOR_MODE = 0;
	
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
		return super.read(CANUltrasonicDistanceSensor.CAN_SENSOR_MODE);
	}
}
