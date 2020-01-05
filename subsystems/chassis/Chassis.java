package org.usfirst.frc4904.standard.subsystems.chassis;

import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Generic Chassis class. In the 4904 standard, the Chassis is treated as a
 * container for motors and as a calculator for motor speeds. setDefaultCommand
 * should be called from RobotMap
 */

public abstract class Chassis implements Subsystem {
	protected double[] motorSpeeds;
	protected Motor[] motors;

	/**
	 *
	 * ] * @param motors all the motors that are part of this Chassis. Pass from
	 * front to back, left to right
	 */
	public Chassis(Motor... motors) {
		// super(name);
		this.motors = motors;
		motorSpeeds = new double[motors.length];
	}

	/**
	 * Returns an array of motors in the order that they were passed to the
	 * constructor
	 *
	 * @return all motors in the order passed to the constructor
	 */
	public Motor[] getMotors() {
		return motors;
	}

	/**
	 * Returns an array of the correct motor speeds calculated with the values
	 * inputted using the move functions.
	 *
	 * @return current motor speeds
	 */
	public double[] getMotorSpeeds() {
		return motorSpeeds;
	}

	/**
	 * Sets the movement to be calculated by the Chassis using polar coordinates.
	 *
	 * @param speed     The magnitude of the speed. In the range -1 to 1.
	 * @param angle     The direction of the speed in angles clockwise from straight
	 *                  ahead. In the range 0 to 2Pi.
	 * @param turnSpeed The speed at which the robot will revolve around itself
	 *                  during the maneuver. In the range -1 to 1.
	 */
	public abstract void movePolar(double speed, double angle, double turnSpeed);

	/**
	 * Sets the movement to be calculated by the Chassis using cartesian
	 * coordinates.
	 *
	 * @param xSpeed    The speed in the X direction (side to side, strafe). In the
	 *                  range -1 to 1.
	 * @param ySpeed    The speed in the Y direction (forward and back). In the
	 *                  range -1 to 1.
	 * @param turnSpeed The speed at which the robot will revolve around itself
	 *                  during the maneuver. In the range -1 to 1.
	 */
	public abstract void moveCartesian(double xSpeed, double ySpeed, double turnSpeed);
}
