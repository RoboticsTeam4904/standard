package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;

public abstract class FourWheelChassis extends Chassis {
	public final Motor frontLeftWheel;
	public final Motor frontRightWheel;
	public final Motor backLeftWheel;
	public final Motor backRightWheel;
	
	public FourWheelChassis(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel) {
		super(name);
		this.frontLeftWheel = frontLeftWheel;
		this.frontRightWheel = frontRightWheel;
		this.backLeftWheel = backLeftWheel;
		this.backRightWheel = backRightWheel;
		this.numberWheels = 4;
		// Left then right then forward then backward
		motorSpeeds = new double[4];
	}
	
	public Motor[] getMotors() {
		return new Motor[] {frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel};
	}
	
	// We assume you want to go forward while turning
	public void move(double speed, double turnSpeed) {
		move2dp(0.00, speed, turnSpeed);
	}
}
