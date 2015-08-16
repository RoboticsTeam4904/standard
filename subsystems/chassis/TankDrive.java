package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.custom.controllers.Controller;
import org.usfirst.frc4904.cmdbased.subsystems.motor.Motor;

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
	
	public Enum getControllerMode() {
		return Controller.PipeModes.XYTwist;
	}
	
	/**
	 * @param speed
	 *        (-1 to 1)
	 *        : speed in all directions
	 * @param angle
	 *        (0 to 2pi)
	 *        : angle of movement (this is ignored)
	 * @param turnSpeed
	 *        (-1 to 1)
	 *        : rate of rotation around center
	 */
	public void move2dp(double speed, double angle, double turnSpeed) {
		double normalize = Math.max(Math.abs(speed + turnSpeed), Math.abs(speed - turnSpeed));
		double leftSpeed = (speed + turnSpeed) / normalize;
		double rightSpeed = (speed - turnSpeed) / normalize;
		motorSpeeds = new double[] {leftSpeed, leftSpeed, rightSpeed, rightSpeed};
	}
	
	/**
	 * @param xSpeed
	 *        : speed in x direction
	 * @param ySpeed
	 *        : speed in y direction (this is ignored)
	 * @param turnSpeed
	 *        : (-1 to 1)
	 *        rate of rotation around center
	 */
	public void move2dc(double xSpeed, double ySpeed, double turnSpeed) {
		move2dp(xSpeed, 0.0, turnSpeed);
	}
	
	/**
	 * @param speed
	 *        : speed in y direction (forward)
	 * @param turnSpeed
	 *        : rate of rotation around center
	 */
	public void move(double speed, double turnSpeed) {}
}
