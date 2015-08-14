package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;

public abstract class TwoWheelChassis extends Chassis {
	protected final Motor leftWheel;
	protected final Motor rightWheel;
	
	public TwoWheelChassis(String name, Motor leftWheel, Motor rightWheel) {
		super(name);
		this.leftWheel = leftWheel;
		this.rightWheel = rightWheel;
		this.numberWheels = 2;
		// Left then right
		motorSpeeds = new double[2];
	}
	
	public Motor[] getMotors() {
		return new Motor[] {leftWheel, rightWheel};
	}
	
	// It makes no sense to call this. You can't move at an angle with two wheels.
	// I am going to ignore the angle because I am too lazy to calculate ySpeed.
	public void move2dp(double speed, double angle, double turnSpeed) {
		move(speed, turnSpeed);
	}
	
	// It makes no sense to call this. You can't move sideways with two wheels.
	// I am going to ignore xSpeed
	public void move2dc(double xSpeed, double ySpeed, double turnSpeed) {
		move(ySpeed, turnSpeed);
	}
}
