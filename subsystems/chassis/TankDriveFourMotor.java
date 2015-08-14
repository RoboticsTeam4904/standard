package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;

public class TankDriveFourMotor extends FourWheelChassis {
	public TankDriveFourMotor(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel) {
		super(name, frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
	}
	
	public int getControllerMode() {
		return 0;
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
}
