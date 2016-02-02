package org.usfirst.frc4904.standard.subsystems.motor;


import edu.wpi.first.wpilibj.SpeedController;

public class MotorFactory {
	public static Motor getMotor(String name, SpeedController controllers) {
		return new Motor(name, controllers);
	}
	
	public static MotorGroup getMotorGroup(String name, SpeedController... controllers) {
		Motor[] motors = new Motor[controllers.length];
		for (int i = 0; i < controllers.length; i++) {
			motors[i] = new Motor(name, controllers[i]);
		}
		return new MotorGroup(name, motors);
	}
}
