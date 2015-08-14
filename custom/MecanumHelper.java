package org.usfirst.frc4904.cmdbased.custom;


public class MecanumHelper {
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
