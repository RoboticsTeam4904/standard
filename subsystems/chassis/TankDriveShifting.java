package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;

/**
 * A tank drive chassis with the shifting system. This effectively adds a
 * solenoid shifter to the chassis as a contained object.
 *
 */
public class TankDriveShifting extends TankDrive implements ShiftingChassis {
	protected final SolenoidShifters shifter;

	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param name
	 * @param turnCorrection
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param shifter
	 */

	public TankDriveShifting(String name, Double turnCorrection, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA,
			Motor rightWheelB, SolenoidShifters shifter) {
		super(name, turnCorrection, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
		this.shifter = shifter;
	}

	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param turnCorrection
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param shifter
	 */

	public TankDriveShifting(Double turnCorrection, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA,
			Motor rightWheelB, SolenoidShifters shifter) {
		this("Tank Drive Shifting", turnCorrection, leftWheelA, leftWheelB, rightWheelA, rightWheelB, shifter);
	}

	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param name
	 * @param turnCorrection
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param shifter
	 */

	public TankDriveShifting(String name, Double turnCorrection, Motor leftWheel, Motor rightWheel,
			SolenoidShifters shifter) {
		super(name, turnCorrection, leftWheel, rightWheel);
		this.shifter = shifter;
	}

	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param turnCorrection
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param shifter
	 */

	public TankDriveShifting(Double turnCorrection, Motor leftWheel, Motor rightWheel, SolenoidShifters shifter) {
		this("Tank Drive Shifting", turnCorrection, leftWheel, rightWheel, shifter);
	}

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
	public TankDriveShifting(String name, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB,
			SolenoidShifters shifter) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
		this.shifter = shifter;
	}

	/**
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param shifter
	 */
	public TankDriveShifting(Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB,
			SolenoidShifters shifter) {
		this("Tank Drive Shifting", leftWheelA, leftWheelB, rightWheelA, rightWheelB, shifter);
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
	 * A tank drive with shifting solenoids (only two solenoids supported)
	 *
	 * @param leftWheel
	 * @param rightWheel
	 * @param shifter
	 */
	public TankDriveShifting(Motor leftWheel, Motor rightWheel, SolenoidShifters shifter) {
		this("Tank Drive Shifting", leftWheel, rightWheel, shifter);
	}

	/**
	 * Returns an array of solenoids in the order left, right
	 */
	@Override
	public SolenoidShifters getShifter() {
		return shifter;
	}
}
