package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;

public class SwerveModule {
	public final Motor linear;
	public final ServoSubsystem rotation;
	public double angleFromCenter;
	public double distanceFromCenter;
	private double wheelSpeed;
	private double wheelAngle;

	/**
	 * Constructs a single swerve module
	 * 
	 * @param linear   must have a range of at least 180 degrees
	 * @param rotation
	 * @param angleFromCenter    (unit in degrees)
	 * @param distanceFromCenter
	 * @param wheeSPeed
	 * @param wheelAngle
	 */

	public SwerveModule(Motor linear, ServoSubsystem rotation, double angleFromCenter, 
						double distanceFromCenter, double wheelSpeed, double wheelAngle) {
		this.linear = linear;
		this.rotation = rotation;
		this.angleFromCenter = angleFromCenter;
		this.distanceFromCenter = distanceFromCenter;
		this.wheelSpeed = wheelSpeed;
		this.wheelAngle = wheelAngle;
	}

	public void setState(double speed, double angle) {
		this.setAngle(angle);
		this.setSpeed(speed);

	}

	public void setAngle(double angle) {
		if (angle >= 180) { // TODO: radians
			this.wheelSpeed *= -1;
			this.wheelAngle = angle - 180;
		} else {
			this.wheelAngle = angle;
		}
	}

	public void setSpeed(double speed) {
		this.wheelSpeed = speed; 
	}

	public double getCurrentPosition() {
		return this.rotation.getCurrentPosition();
	}
}