// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;

/**
 * Tank drive chassis. Has two sets of wheels, left and right. Can only turn
 * left or right.
 */
public class TankDrive extends Chassis {
	public double turnCorrection; // 0 by default but can be manually overwritten. Positive values induce a turn
	// to the right

	/**
	 * 
	 * @param name
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param turnCorrection Amount by which to correct turning to make up for dead
	 *                       CIM or unbalanced weight. In the range -1 to 1.
	 */
	public TankDrive(String name, Double turnCorrection, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA,
			Motor rightWheelB) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
		this.turnCorrection = turnCorrection;
	}

	/**
	 * 
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 * @param turnCorrection Amount by which to correct turning to make up for dead
	 *                       CIM or unbalanced weight. In the range -1 to 1.
	 */
	public TankDrive(Double turnCorrection, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB) {
		this("TankDrive", turnCorrection, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
	}

	/**
	 * @param name
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 */
	public TankDrive(String name, Double turnCorrection, Motor leftWheel, Motor rightWheel) {
		super(name, leftWheel, rightWheel);
		this.turnCorrection = turnCorrection;
	}

	/**
	 *
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 */
	public TankDrive(Double turnCorrection, Motor leftWheel, Motor rightWheel) {
		this("TankDrive", turnCorrection, leftWheel, rightWheel);
	}

	/**
	 *
	 * @param name
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 */
	public TankDrive(String name, Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB) {
		super(name, leftWheelA, leftWheelB, rightWheelA, rightWheelB);
		this.turnCorrection = 0.0;
	}

	/**
	 *
	 * @param leftWheelA
	 * @param leftWheelB
	 * @param rightWheelA
	 * @param rightWheelB
	 */
	public TankDrive(Motor leftWheelA, Motor leftWheelB, Motor rightWheelA, Motor rightWheelB) {
		this("TankDrive", leftWheelA, leftWheelB, rightWheelA, rightWheelB);
	}

	/**
	 *
	 * @param name
	 * @param leftWheel
	 * @param rightWheel
	 */
	public TankDrive(String name, Motor leftWheel, Motor rightWheel) {
		super(name, leftWheel, rightWheel);
		this.turnCorrection = 0.0;
	}

	/**
	 * 
	 * @param leftWheel
	 * @param rightWheel
	 */
	public TankDrive(Motor leftWheel, Motor rightWheel) {
		this("TankDrive", leftWheel, rightWheel);
	}

	@Override
	public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
		movePolar(ySpeed, 0.0, turnSpeed);
	}

	@Override
	public void movePolar(double speed, double angle, double turnSpeed) {
		turnSpeed += turnCorrection * speed; // turns to deal with constant turning error due to unbalanced weight or
												// dead CIM
		double normalize = Math.max(Math.max(Math.abs(speed + turnSpeed), Math.abs(speed - turnSpeed)), 1);
		double leftSpeed = (speed + turnSpeed) / normalize;
		double rightSpeed = (speed - turnSpeed) / normalize;
		if (motors.length == 2) {
			motorSpeeds = new double[] { leftSpeed, rightSpeed };
		} else {
			motorSpeeds = new double[] { leftSpeed, leftSpeed, rightSpeed, rightSpeed };
		}
	}

}
