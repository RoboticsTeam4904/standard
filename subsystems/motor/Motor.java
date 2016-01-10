package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Motor extends Subsystem implements SpeedController {
	protected final SpeedController motor;
	protected boolean inverted;
	
	public Motor(String name, SpeedController motor, boolean inverted) {
		super(name);
		this.motor = motor;
		this.inverted = inverted;
	}
	
	public Motor(String name, SpeedController motor) {
		this(name, motor, false);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	public void pidWrite(double arg0) {
		if (!inverted) {
			motor.pidWrite(arg0);
		} else {
			motor.pidWrite(-1 * arg0);
		}
	}
	
	public void disable() {
		motor.disable();
	}
	
	public double get() {
		if (!inverted) {
			return motor.get();
		} else {
			return -1 * motor.get();
		}
	}
	
	public void set(double arg0) {
		if (!inverted) {
			motor.set(arg0);
		} else {
			motor.set(-1 * arg0);
		}
	}
	
	public void set(double arg0, byte arg1) {
		if (!inverted) {
			motor.set(arg0, arg1);
		} else {
			motor.set(-1 * arg0, arg1);
		}
	}

	public boolean getInverted() {
		return inverted;
	}

	public void setInverted(boolean arg) {
		inverted = arg;
	}
}
