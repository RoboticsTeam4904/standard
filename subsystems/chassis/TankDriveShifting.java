package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;
import edu.wpi.first.wpilibj.Solenoid;

public class TankDriveShifting extends TankDrive implements ShiftingChassis {
	private final Solenoid leftSolenoid;
	private final Solenoid rightSolenoid;
	
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
	public TankDriveShifting(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel, Solenoid leftSolenoid, Solenoid rightSolenoid) {
		super(name, frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
		this.leftSolenoid = leftSolenoid;
		this.rightSolenoid = rightSolenoid;
	}
	
	public TankDriveShifting(String name, Motor leftWheel, Motor rightWheel, Solenoid leftSolenoid, Solenoid rightSolenoid) {
		super(name, leftWheel, rightWheel);
		this.leftSolenoid = leftSolenoid;
		this.rightSolenoid = rightSolenoid;
	}
	
	/**
	 * Returns an array of solenoids in the order left, right
	 */
	public Solenoid[] getSolenoids() {
		return new Solenoid[] {leftSolenoid, rightSolenoid};
	}
}
