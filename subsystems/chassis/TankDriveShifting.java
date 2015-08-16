package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.motor.Motor;

public class TankDriveShifting extends TankDrive implements ShiftingChassis {
	private final SolenoidShifters shifter;
	
	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 * 
	 * @param name
	 * @param frontLeftWheel
	 * @param frontRightWheel
	 * @param backLeftWheel
	 * @param backRightWheel
	 * @param leftSolenoid
	 * @param rightSolenoid
	 */
	public TankDriveShifting(String name, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB, SolenoidShifters shifter) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
		this.shifter = shifter;
	}
	
	public TankDriveShifting(String name, Motor leftWheel, Motor rightWheel, SolenoidShifters shifter) {
		super(name, leftWheel, rightWheel);
		this.shifter = shifter;
	}
	
	/**
	 * Returns an array of solenoids in the order left, right
	 */
	public SolenoidShifters getShifter() {
		return shifter;
	}
}
