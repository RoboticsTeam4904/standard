package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;

public class SwerveChassis extends Chassis {
	private class SwerveModule {
		public final Motor drive;
		public final Motor turn;

		/**
		 * Constructs a single swerve module
		 * 
		 * @param drive
		 * @param turn
		 */

		public SwerveModule(Motor drive, Motor turn) {
			this.drive = drive;
			this.turn = turn;
		}
	}

	private final SwerveModule frontLeftMod;
	private final SwerveModule frontRightMod;
	private final SwerveModule backLeftMod;
	private final SwerveModule backRightMod;

	private final double wheelBase;
	private final double trackWidth;
	private final double diagonal;

	/**
	 * Constructs a swerve drive chassis
	 * 
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
			Motor backLeftWheelRotation, Motor backRightWheelDrive, Motor backRightWheelRotation, double wheelBase,
			double trackWidth) {
		super(name, frontLeftWheelDrive, frontLeftWheelRotation, frontRightWheelDrive, frontRightWheelDrive,
				frontRightWheelRotation, backLeftWheelDrive, backLeftWheelRotation, backRightWheelDrive,
				backRightWheelRotation);

		frontLeftMod = new SwerveModule(frontLeftWheelDrive, frontLeftWheelRotation);
		frontRightMod = new SwerveModule(frontRightWheelDrive, frontRightWheelRotation);
		backLeftMod = new SwerveModule(backLeftWheelDrive, backLeftWheelRotation);
		backRightMod = new SwerveModule(backRightWheelDrive, backRightWheelRotation);

		this.wheelBase = wheelBase;
		this.trackWidth = trackWidth;
		this.diagonal = Math.sqrt(Math.pow(wheelBase, 2) + Math.pow(trackWidth, 2));
	}

	@Override
	public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
		double a = xSpeed - turnSpeed * (wheelBase / diagonal);
		double b = xSpeed + turnSpeed * (wheelBase / diagonal);
		double c = ySpeed - turnSpeed * (trackWidth / diagonal);
		double d = ySpeed + turnSpeed * (trackWidth / diagonal);

		double frontLeftWheelSpeed = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));
		double frontRightWheelSpeed = Math.sqrt(Math.pow(b, 2) + Math.pow(d, 2));
		double backLeftWheelSpeed = Math.sqrt(Math.pow(a, 2) + Math.pow(d, 2));
		double backRightWheelSpeed = Math.sqrt(Math.pow(a, 2) + Math.pow(c, 2));

		double frontLeftWheelAngle = Math.toDegrees(Math.atan(b / c));
		double frontRightWheelAngle = Math.toDegrees(Math.atan(b / d));
		double backLeftWheelAngle = Math.toDegrees(Math.atan(a / d));
		double backRightWheelAngle = Math.toDegrees(Math.atan(a / c));

		double maxSpeed = Math.max(Math.max(frontLeftWheelSpeed, frontRightWheelSpeed),
				Math.max(backLeftWheelSpeed, backRightWheelSpeed));

		if (maxSpeed > 1) {
			frontLeftWheelSpeed /= maxSpeed;
			frontRightWheelSpeed /= maxSpeed;
			backLeftWheelSpeed /= maxSpeed;
			backRightWheelSpeed /= maxSpeed;
		}

		/*
		 * for switching from cartesian to polar double totalSpeed =
		 * Math.sqrt(Math.pow(ySpeed,2)+Math.pow(xSpeed,2)); double angle = Math.PI/2 -
		 * Math.atan(ySpeed/xSpeed); movePolar(totalSpeed, angle, turnSpeed);
		 */
	}

	@Override
	public void movePolar(double speed, double angle, double turnSpeed) {
		moveCartesian(speed * Math.cos(angle), speed * Math.sin(angle), turnSpeed);
	}
}
