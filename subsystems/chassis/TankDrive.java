package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;

/**
 * Tank drive chassis. Has two sets of wheels, left and right. Can only turn left or right.
 */
public class TankDrive extends Chassis {
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
	}

	/**
	 *
	 * @param name
	 * @param leftWheel
	 * @param rightWheel
	 */
	public TankDrive(String name, Motor leftWheel, Motor rightWheel) {
		super(name, leftWheel, rightWheel);
	}

	/**
	 * Sets the movement to be calculated by the Chassis using 2d polar coordinates.
	 *
	 * @param speed
	 *        The magnitude of the speed. In the range -1 to 1.
	 * @param angle
	 *        The direction of the speed in angles clockwise from straight ahead. In the range 0 to 2Pi.
	 * @param turnSpeed
	 *        The speed at which the robot will revolve around itself during the maneuver. In the range -1 to 1.
	 */
	@Override
	public void movePolar(double speed, double angle, double turnSpeed) {
		double normalize = Math.max(Math.max(Math.abs(speed + turnSpeed), Math.abs(speed - turnSpeed)), 1);
		double leftSpeed = (speed + turnSpeed) / normalize;
		double rightSpeed = (speed - turnSpeed) / normalize;
		if (motors.length == 2) {
			motorSpeeds = new double[] {leftSpeed, rightSpeed};
		} else {
			motorSpeeds = new double[] {leftSpeed, leftSpeed, rightSpeed, rightSpeed};
		}
	}

	/**
	 * Sets the movement to be calculated by the Chassis using 2d cartesian coordinates.
	 *
	 * @param xSpeed
	 *        The speed in the X direction (side to side, strafe). In the range -1 to 1.
	 * @param ySpeed
	 *        The speed in the Y direction (forward and back). In the range -1 to 1.
	 * @param turnSpeed
	 *        The speed at which the robot will revolve around itself during the maneuver. In the range -1 to 1.
	 */
	@Override
	public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
		movePolar(ySpeed, 0.0, turnSpeed);
	}
}
