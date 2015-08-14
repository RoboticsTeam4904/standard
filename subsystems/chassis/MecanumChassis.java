package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;

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
	
	public int getControllerMode() {
		return 0;
	}
	
	/**
	 * @param speed
	 *        (-1 to 1)
	 *        : speed in all directions
	 * @param angle
	 *        (0 to 2pi)
	 *        : angle of movement
	 * @param turnSpeed
	 *        (-1 to 1)
	 *        : rate of rotation around center
	 */
	public void move2dp(double speed, double angle, double turnSpeed) {
		motorSpeeds = MecanumHelper.calculateWheels(speed, angle, turnSpeed);
	}
	
	/**
	 * @param xSpeed
	 *        : speed in x direction
	 * @param ySpeed
	 *        : speed in y direction
	 */
	public void move2dc(double xSpeed, double ySpeed, double turnSpeed) {
		double[] polar = MecanumHelper.cartesianToPolar(xSpeed, ySpeed);
		move2dp(polar[0], polar[1], turnSpeed);
	}
	
	/**
	 * @param speed
	 *        : speed in the y direction
	 * @param turnSpeed
	 *        : rate of rotation around center
	 */
	public void move(double speed, double turnSpeed) {
		move2dc(0.0, speed, turnSpeed);
	}
	
	// At the moment, I see no reason that
	private static class MecanumHelper {
		public static double[] calculateWheels(double speed, double angle, double turnSpeed) {
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
		
		public static double[] cartesianToPolar(double x, double y) {
			double speed = Math.sqrt(x * x + y * y);
			double angle = Math.atan2(y, x);
			// atan2 rotated 45* counterclockwise, still not sure why
			angle -= Math.PI / 4.0;
			return new double[] {speed, angle};
		}
	}
}
