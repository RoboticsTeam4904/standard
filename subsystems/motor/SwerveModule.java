package org.usfirst.frc4904.standard.subsystems.motor;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;


public class SwerveModule {
		public final Motor linear;
		public final ServoSubsystem rotation;
		public double angleFromCenter;
		public double distanceFromCenter;
		public double wheelSpeed;
		public double wheelAngle;


		/**
		 * Constructs a single swerve module
		 * 
		 * @param linear must have a range of at least 180 degrees
		 * @param rotation
         * @param angle unit in degrees
         * @param distance
		 */

		public SwerveModule(Motor linear, ServoSubsystem rotation, Double angleFromCenter, Double distanceFromCenter) {
			this.linear = linear;
            this.rotation = rotation;
            this.angleFromCenter = angleFromCenter;
			this.distanceFromCenter = distanceFromCenter;
		}

		public void setState(double speed, double angle) {
			this.wheelSpeed = speed;
			if (angle >= 180) { // TODO: radians
				this.wheelSpeed *= -1;
				this.wheelAngle = angle - 180;
			} else {
				this.wheelAngle = angle;
			}
			// I don't know if you'd be able to just set any angle to the servo. Maybe set it incrementally?
			// Should end up actually calling the motors
		}
	}