package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.MotorSubsystem;

public class SwerveChassis extends Chassis {
	public final MotorSubsystem frontLeftWheelSwerve;
	public final MotorSubsystem frontRightWheelSwerve;
	public final MotorSubsystem backLeftWheelSwerve;
	public final MotorSubsystem backRightWheelSwerve;

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
	public SwerveChassis(String name, MotorSubsystem frontLeftWheel, MotorSubsystem frontRightWheel, MotorSubsystem backLeftWheel,
			MotorSubsystem backRightWheel, MotorSubsystem frontLeftWheelSwerve, MotorSubsystem frontRightWheelSwerve, MotorSubsystem backLeftWheelSwerve,
			MotorSubsystem backRightWheelSwerve) {
		super(frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
		this.frontLeftWheelSwerve = frontLeftWheelSwerve;
		this.frontRightWheelSwerve = frontRightWheelSwerve;
		this.backLeftWheelSwerve = backLeftWheelSwerve;
		this.backRightWheelSwerve = backRightWheelSwerve;
	}

	public SwerveChassis(MotorSubsystem frontLeftWheel, MotorSubsystem frontRightWheel, MotorSubsystem backLeftWheel, MotorSubsystem backRightWheel,
			MotorSubsystem frontLeftWheelSwerve, MotorSubsystem frontRightWheelSwerve, MotorSubsystem backLeftWheelSwerve,
			MotorSubsystem backRightWheelSwerve) {
		this("SwerveChassis", frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel, frontLeftWheelSwerve,
				frontRightWheelSwerve, backLeftWheelSwerve, backRightWheelSwerve);
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
