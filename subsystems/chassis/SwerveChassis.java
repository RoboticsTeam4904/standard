package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;

public class SwerveChassis extends Chassis {
	private class SwerveModule {
		public final Motor drive; // TODO: better variable name
		public final ServoSubsystem turn; // TODO: better variable name (turn implies an action or a quantity, not a motor)
		public double speed;
		public double angle;

		/**
		 * Constructs a single swerve module
		 * 
		 * @param drive
		 * @param turn
		 */

		public SwerveModule(Motor drive, ServoSubsystem turn) {
			this.drive = drive;
			this.turn = turn;
		}

		public void setState(double speed, double angle) {
			// add stuff that allows for modulating speed based on driver input
			this.speed = speed;
			if (angle >= 180) { // TODO: radians
				this.speed *= -1;
				this.angle = angle - 180;
			} else {
				this.angle = angle;
			}
			// I don't know if you'd be able to just set any angle to the servo. Maybe set it incrementally?
			// Should end up actually calling the motors
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

	public SwerveChassis(String name, Motor frontLeftWheelDrive, ServoSubsystem frontLeftWheelRotation,
			Motor frontRightWheelDrive, ServoSubsystem frontRightWheelRotation, Motor backLeftWheelDrive,
			ServoSubsystem backLeftWheelRotation, Motor backRightWheelDrive, ServoSubsystem backRightWheelRotation,
			double wheelBase, double trackWidth) {
		super(name, frontLeftWheelDrive, frontRightWheelDrive, frontRightWheelDrive, backLeftWheelDrive,
				backRightWheelDrive);

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
		// TODO: Big-brain move - don't assume 4 wheels in rectangle form. Represent each wheel as an angle and distance away from the center and find generic equations that aren't wheel specific
		double a = xSpeed - turnSpeed * (wheelBase / diagonal);
		double b = xSpeed + turnSpeed * (wheelBase / diagonal);
		double c = ySpeed - turnSpeed * (trackWidth / diagonal);
		double d = ySpeed + turnSpeed * (trackWidth / diagonal);

		double frontLeftWheelSpeed = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2)); // TODO: Add R/2
		double frontRightWheelSpeed = Math.sqrt(Math.pow(b, 2) + Math.pow(d, 2));
		double backLeftWheelSpeed = Math.sqrt(Math.pow(a, 2) + Math.pow(d, 2));
		double backRightWheelSpeed = Math.sqrt(Math.pow(a, 2) + Math.pow(c, 2)); // Math.pow is pretty inefficient, in java people often just use a*a and c*c, but it's honestly insignificant (except for how it looks)

		double frontLeftWheelAngle = Math.toDegrees(Math.atan(b / c)); // TODO: Kill degrees
		double frontRightWheelAngle = Math.toDegrees(Math.atan(b / d));
		double backLeftWheelAngle = Math.toDegrees(Math.atan(a / d));
		double backRightWheelAngle = Math.toDegrees(Math.atan(a / c)); // TODO: If not ugly, consider removing a,b,c,d since they're confusing and meaningless as is. (You could program the equation directly without the temporary helper variables) (some stuff will simplify (if not it might simplify in polar form?))

		double maxSpeed = Math.max(Math.max(frontLeftWheelSpeed, frontRightWheelSpeed),
				Math.max(backLeftWheelSpeed, backRightWheelSpeed));

		if (maxSpeed > 1) { // TODO: Shouldn't use this if statement. When maxSpeed is below 1 its magnitude isn't any more meaningful
			frontLeftWheelSpeed /= maxSpeed;
			frontRightWheelSpeed /= maxSpeed;
			backLeftWheelSpeed /= maxSpeed;
			backRightWheelSpeed /= maxSpeed;
		}

		frontLeftMod.setState(frontLeftWheelSpeed, frontLeftWheelAngle);
		frontRightMod.setState(frontRightWheelSpeed, frontRightWheelAngle);
		backLeftMod.setState(backLeftWheelSpeed, backLeftWheelAngle);
		backRightMod.setState(backRightWheelSpeed, backRightWheelAngle);

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

	@Override
	public double[] getMotorSpeeds() {
		return new double[] {frontLeftMod.speed, frontRightMod.speed, backLeftMod.speed, backRightMod.speed};
	}

	@Override
	public Motor[] getMotors() {
		return new Motor[] {frontLeftMod.drive, frontRightMod.drive, backLeftMod.drive, backLeftMod.drive};
	}

	public double[] getServoAngles() {
		return new double[] {frontLeftMod.angle, frontRightMod.angle, backLeftMod.angle, backRightMod.angle};
	}

	public ServoSubsystem[] getServos() {
		return new ServoSubsystem[] {frontLeftMod.turn, frontRightMod.turn, backLeftMod.turn, backRightMod.turn};
	}
}