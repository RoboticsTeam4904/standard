package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;

/**
 * Mecanum Chassis.
 * Has one wheel for each corner.
 * Can move in all directions.
 *
 */
public class MecanumChassis extends Chassis {
	/**
	 * Constructs a mecanum chassis
	 *
	 * @param name
	 * @param frontLeftWheel
	 * @param frontRightWheel
	 * @param backLeftWheel
	 * @param backRightWheel
	 */
	public MecanumChassis(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel) {
		super(name, frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
	}
	
	/**
	 * Sets the movement to be calculated by the MecanumChassis using 2d polar coordinates.
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
		motorSpeeds = MecanumChassis.calculateWheels(speed, angle, turnSpeed);
	}
	
	/**
	 * Sets the movement to be calculated by the MecanumChassis using 2d cartesian coordinates.
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
		double[] polar = MecanumChassis.cartesianToPolar(xSpeed, ySpeed);
		movePolar(polar[0], polar[1], turnSpeed);
	}
	
	/**
	 * Calculates the speeds for each motor given polar coordinates.
	 *
	 * @param speed
	 *        The magnitude of the speed. In the range -1 to 1.
	 * @param angle
	 *        The direction of the speed in angles clockwise from straight ahead. In the range 0 to 2Pi.
	 * @param turnSpeed
	 *        The speed at which the robot will revolve around itself during the maneuver. In the range -1 to 1.
	 * 
	 * @return
	 * 		An array {frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed}
	 */
	public static double[] calculateWheels(double speed, double angle, double turnSpeed) {
		angle -= Math.PI / 4.0; // Shift axes to work with mecanum
		angle = angle % (Math.PI * 2); // make sure angle makes sense
		double frontLeft = speed * Math.sin(angle) + turnSpeed;
		double frontRight = -1 * speed * Math.cos(angle) + turnSpeed;
		double backLeft = speed * Math.cos(angle) + turnSpeed;
		double backRight = -1 * speed * Math.sin(angle) + turnSpeed;
		double scaleFactor = Math.max(Math.max(Math.max(Math.abs(frontLeft), Math.abs(frontRight)), Math.abs(backLeft)), Math.abs(backRight));
		if (scaleFactor < 1) {
			scaleFactor = 1;
		}
		frontLeft /= scaleFactor;
		frontRight /= scaleFactor;
		backLeft /= scaleFactor;
		backRight /= scaleFactor;
		return new double[] {frontLeft, frontRight, backLeft, backRight};
	}
	
	/**
	 * Converts an x and y coordinate into an array of speed and angle.
	 *
	 * @param x
	 *        The x coordinate
	 * @param y
	 *        The y coordinate
	 * @return
	 * 		An array {speed, angle}
	 */
	public static double[] cartesianToPolar(double x, double y) {
		double speed = Math.sqrt(x * x + y * y);
		double angle = Math.atan2(y, x);
		return new double[] {speed, angle};
	}
}
