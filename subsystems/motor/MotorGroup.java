package org.usfirst.frc4904.standard.subsystems.motor;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorGroup extends Subsystem implements SpeedController {
	protected Motor[] motors;
	
	public MotorGroup(String name, Motor... motors) {
		super(name);
		this.motors = motors;
	}
	
	protected void initDefaultCommand() {
		for (Motor motor : motors) {
			motor.initDefaultCommand();
		}
	}
	
	public void pidWrite(double arg0) {
		for (Motor motor : motors) {
			motor.pidWrite(arg0);
		}
	}
	
	public void disable() {
		for (Motor motor : motors) {
			motor.disable();
		}
	}
	
	public double get() {
		return motors[0].get();
	}
	
	public void set(double arg0) {
		for (Motor motor : motors) {
			motor.set(arg0);
		}
	}
	
	public void set(double arg0, byte arg1) {
		for (Motor motor : motors) {
			motor.set(arg0, arg1);
		}
	}
}
