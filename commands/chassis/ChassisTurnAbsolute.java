package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;

public class ChassisTurnAbsolute extends ChassisTurnDegrees {
	
	/**
	 * Constructor
	 * This command rotates the chassis to a position relative to the starting point of the robot
	 * (e.g. the position where the imu was last reset).
	 *
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param motionController
	 */
	public ChassisTurnAbsolute(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		super(chassis, (finalAngle % 360) - 180, imu, motionController);
	}
	
	/**
	 * Constructor
	 * This command rotates the chassis to a position relative to the starting point of the robot
	 * (e.g. the position where the imu was last reset).
	 *
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param fallbackController
	 *        If the sensor fails for some reason, this controller will be cancelled, then the fallbackController will start
	 * @param motionController
	 */
	public ChassisTurnAbsolute(Chassis chassis, double finalAngle, IMU imu, ChassisController fallbackController, MotionController motionController) {
		super(chassis, (finalAngle % 360) - 180, imu, fallbackController, motionController);
	}

	@Override
	protected void initialize() {
		// ChassisTurnDegrees measures an initial angle and compensates for it
		// to make its turns relative;
		super.initialize();
		// Not anymore
		super.initialAngle = 0.0;
	}
}
