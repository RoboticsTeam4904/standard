package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;
import edu.wpi.first.wpilibj.Solenoid;

public class TankDriveFourMotorShifting extends TankDriveFourMotor implements ShiftingChassis {
	private final Solenoid leftSolenoid;
	private final Solenoid rightSolenoid;
	
	public TankDriveFourMotorShifting(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel, Solenoid leftSolenoid, Solenoid rightSolenoid) {
		super(name, frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
		this.leftSolenoid = leftSolenoid;
		this.rightSolenoid = rightSolenoid;
	}
	
	public Solenoid[] getSolenoids() {
		return new Solenoid[] {leftSolenoid, rightSolenoid};
	}
}
