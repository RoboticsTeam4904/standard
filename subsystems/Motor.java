package org.usfirst.frc4904.cmdbased.subsystems;


import org.usfirst.frc4904.cmdbased.commands.MotorIdle;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Motor extends Subsystem implements SpeedController {
	private final SpeedController motor;
	
	public Motor(String name, SpeedController motor) {
		this.motor = motor;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	public void pidWrite(double arg0) {
		motor.pidWrite(arg0);
	}
	
	public void disable() {
		motor.disable();
	}
	
	public double get() {
		return motor.get();
	}
	
	public void set(double arg0) {
		motor.set(arg0);
	}
	
	public void set(double arg0, byte arg1) {
		motor.set(arg0, arg1);
	}
}
