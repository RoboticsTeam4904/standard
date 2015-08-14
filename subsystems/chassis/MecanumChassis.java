package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.subsystems.Motor;

public class MecanumChassis extends FourWheelChassis {
	public MecanumChassis(String name, Motor frontLeftWheel, Motor frontRightWheel, Motor backLeftWheel, Motor backRightWheel) {
		super(name, frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel);
	}
	
	/**
	 * @param speed
	 *        (0 to 1)
	 *        : speed in all directions
	 * @param angle
	 *        (0 to 2pi)
	 *        : angle of movement
	 * @param turnSpeed
	 *        (0 to 1)
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
	
	// At the moment, I see no reason that
	private static class MecanumHelper {
		public static double[] calculateWheels(double speed, double angle, double turnSpeed) {
			angle = angle % (Math.PI * 2); // make sure angle makes sense
			double frontLeft = speed * Math.sin(angle + Math.PI / 4) + turnSpeed;
			double frontRight = speed * Math.cos(angle + Math.PI / 4) - turnSpeed;
			double backLeft = speed * Math.cos(angle + Math.PI / 4) + turnSpeed;
			double backRight = speed * Math.sin(angle + Math.PI / 4) - turnSpeed;
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
			return new double[] {speed, angle};
		}
	}
}
