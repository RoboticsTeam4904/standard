package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;

public class ChassisTurnAbsolute extends ChassisTurnDegrees {
	
	public ChassisTurnAbsolute(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		super(chassis, (finalAngle % 360), imu, motionController);
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
