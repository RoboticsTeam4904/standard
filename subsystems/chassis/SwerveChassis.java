package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;

public class SwerveChassis extends Chassis {
	public final Motor frontLeftWheelSwerve;
	public final Motor frontRightWheelSwerve;
	public final Motor backLeftWheelSwerve;
	public final Motor backRightWheelSwerve;
	
	/**
	 * Constructs a swerve drive chassis
	 *
	 * @param name
	 * @param frontLeftWheel
	 * @param frontRightWheel
	 * @param backLeftWheel
	 * @param backRightWheel
	 * @param frontLeftWheelSwerve
	 * @param frontRightWheelSwerve
	 * @param backLeftWheelSwerve
	 * @param backRightWheelSwerve
	 */
	public SwerveChassis(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel, Motor frontLeftWheelSwerve, Motor frontRightWheelSwerve, Motor backLeftWheelSwerve, Motor backRightWheelSwerve) {
		super(name, frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
		this.frontLeftWheelSwerve = frontLeftWheelSwerve;
		this.frontRightWheelSwerve = frontRightWheelSwerve;
		this.backLeftWheelSwerve = backLeftWheelSwerve;
		this.backRightWheelSwerve = backRightWheelSwerve;
	}
	
	@Override
	public void movePolar(double xSpeed, double ySpeed, double turnSpeed) {
		// TODO Implement
	}
	
	@Override
	public void moveCartesian(double speed, double angle, double turnSpeed) {
		// TODO Implement
	}
}
