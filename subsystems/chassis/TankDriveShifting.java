package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;

/**
 * A tank drive chassis with the shifting system.
 * This effectively adds a solenoid shifter to the chassis as a contained object.
 *
 */
public class TankDriveShifting extends TankDrive implements ShiftingChassis {
	protected final SolenoidShifters shifter;
	
	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param name
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param shifter
	 */
	public TankDriveShifting(String name, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB, SolenoidShifters shifter) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
		this.shifter = shifter;
	}
	
	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param name
	 * @param leftWheel
	 * @param rightWheel
	 * @param shifter
	 */
	public TankDriveShifting(String name, Motor leftWheel, Motor rightWheel, SolenoidShifters shifter) {
		super(name, leftWheel, rightWheel);
		this.shifter = shifter;
	}
	
	/**
	 * Returns an array of solenoids in the order left, right
	 */
	@Override
	public SolenoidShifters getShifter() {
		return shifter;
	}
}
