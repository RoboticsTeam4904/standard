package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;
import org.usfirst.frc4904.standard.subsystems.motor.SwerveModule;

public class SwerveChassis extends Chassis {
	private final SwerveModule[] modules;
	private double[] wheelSpeed;
	private double[] wheelAngle;

	/**
	 * Constructs a swerve drive chassis
	 * 
	 * @param name
	 * @param modules
	 */

	public SwerveChassis(String name, SwerveModule ... modules) {
		super(name);
		this.modules = modules;
	}

	@Override
	public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
		for(int i = 0; i<modules.length; i++){
			double xVector;
			double yVector;
			xVector = xSpeed + modules[i].distanceFromCenter*Math.cos(modules[i].angleFromCenter);
			yVector = ySpeed + modules[i].distanceFromCenter*Math.sin(modules[i].angleFromCenter);
			wheelSpeed[i] = Math.sqrt(xVector * xVector + yVector * yVector);
			wheelAngle[i] = Math.atan(xVector/yVector);
		}
		
		/*
		TODO: normalize
		code from previous version is here:
		frontLeftWheelSpeed /= maxSpeed;
		frontRightWheelSpeed /= maxSpeed;
		backLeftWheelSpeed /= maxSpeed;
		backRightWheelSpeed /= maxSpeed;
		*/

		for (int i = 0; i<modules.length; i++){
			modules[i].setState(wheelSpeed[i],wheelAngle[i]);
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

	@Override
	public double[] getMotorSpeeds() {
		return wheelSpeed;
	}

	@Override 
	public Motor[] getMotors() {
		Motor[] motors = new Motor[modules.length];
		for (int i = 0; i<modules.length; i++){
			motors[i] = modules[i].linear;
		}
		return motors;
	}

	public double[] getServoAngles() {
		return wheelAngle;
	}
	
	public ServoSubsystem[] getServos() {
		ServoSubsystem[] servos = new ServoSubsystem[modules.length];
		for (int i = 0; i<modules.length; i++){
			servos[i] = modules[i].rotation;
		}
		return servos;
	}

	public SwerveModule[] getSwerveModule() {
		return modules;
	}
}