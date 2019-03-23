package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;

public class SwerveChassis extends Chassis {
	protected final Motor frontLeftWheelDrive;
	protected final Motor frontLeftWheelRotation;
	protected final Motor frontRightWheelDrive;
	protected final Motor frontRightWheelRotation;
	protected final Motor backLeftWheelDrive;
	protected final Motor backLeftWheelRotation;
	protected final Motor backRightWheelDrive;
	protected final Motor backRightWheelRotation;

	/**
	 * Constructs a swerve drive chassis
	 * @param name
	 * @param frontLeftWheelDrive
	 * @param frontLeftWheelRotation
	 * @param frontRightWheelDrive
	 * @param frontRightWheelRotation
	 * @param backLeftWheelDrive
	 * @param backLeftWheelRotation
	 * @param backRightWheelDrive
	 * @param backRightWheelRotation
	 */

	public SwerveChassis(String name, Motor frontLeftWheelDrive, Motor frontLeftWheelRotation, 
						 Motor frontRightWheelDrive, Motor frontRightWheelRotation, Motor backLeftWheelDrive, 
						 Motor backLeftWheelRotation, Motor backRightWheelDrive, Motor backRightWheelRotation) {
		super(name, frontLeftWheelDrive, frontLeftWheelRotation, frontRightWheelDrive, frontRightWheelDrive,
			  frontRightWheelRotation, backLeftWheelDrive, backLeftWheelRotation, backRightWheelDrive,
			  backRightWheelRotation);
			  this.frontLeftWheelDrive = frontLeftWheelDrive;
			  this.frontLeftWheelRotation = frontLeftWheelRotation;
			  this.frontRightWheelDrive = frontRightWheelDrive;
			  this.frontRightWheelRotation = frontRightWheelRotation;
			  this.backLeftWheelDrive = backLeftWheelDrive;
			  this.backLeftWheelRotation = backLeftWheelRotation;
			  this.backRightWheelDrive = backRightWheelDrive;
			  this.backRightWheelRotation = backRightWheelRotation;
	}

	@Override
	public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
		double totalSpeed = Math.sqrt(Math.pow(ySpeed,2)+Math.pow(xSpeed,2));
		double angle = Math.PI/2 - Math.atan(ySpeed/xSpeed);
		movePolar(totalSpeed, angle, turnSpeed);
	}

	@Override
	public void movePolar(double speed, double angle, double turnSpeed) {
	}
}
