package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.MotorSubsystem;

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

	public TankDriveShifting(String name, Double turnCorrection, MotorSubsystem leftWheelA, MotorSubsystem leftWheelB, MotorSubsystem rightWheelA,
			MotorSubsystem rightWheelB, SolenoidShifters shifter) {
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

	public TankDriveShifting(Double turnCorrection, MotorSubsystem leftWheelA, MotorSubsystem leftWheelB, MotorSubsystem rightWheelA,
			MotorSubsystem rightWheelB, SolenoidShifters shifter) {
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

	public TankDriveShifting(String name, Double turnCorrection, MotorSubsystem leftWheel, MotorSubsystem rightWheel,
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

	public TankDriveShifting(Double turnCorrection, MotorSubsystem leftWheel, MotorSubsystem rightWheel, SolenoidShifters shifter) {
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
	public TankDriveShifting(String name, MotorSubsystem leftWheelA, MotorSubsystem leftWheelB, MotorSubsystem rightWheelA, MotorSubsystem rightWheelB,
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
	public TankDriveShifting(MotorSubsystem leftWheelA, MotorSubsystem leftWheelB, MotorSubsystem rightWheelA, MotorSubsystem rightWheelB,
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
	public TankDriveShifting(String name, MotorSubsystem leftWheel, MotorSubsystem rightWheel, SolenoidShifters shifter) {
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
	public TankDriveShifting(MotorSubsystem leftWheel, MotorSubsystem rightWheel, SolenoidShifters shifter) {
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
