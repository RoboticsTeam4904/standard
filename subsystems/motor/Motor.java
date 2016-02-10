package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.Linear;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A speed controller that
 * is also a subsystem.
 *
 */
public class Motor extends Subsystem implements SpeedController {
	protected final SpeedController[] motors;
	protected final SpeedModifier speedModifier;
	protected boolean inverted;
	
	public Motor(String name, boolean inverted, SpeedModifier slopeController, SpeedController... motors) {
		super(name);
		this.motors = motors;
		this.speedModifier = slopeController;
		setInverted(inverted);
		this.inverted = inverted;
	}
	
	public Motor(String name, boolean inverted, SpeedController... motors) {
		this(name, inverted, new Linear(), motors);
	}
	
	public Motor(String name, SpeedModifier slopeController, SpeedController... motors) {
		this(name, false, slopeController, motors);
	}
	
	public Motor(String name, SpeedController... motors) {
		this(name, false, new Linear(), motors);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	public void pidWrite(double speed) {
		set(speed);
	}
	
	public void disable() {
		for (SpeedController motor : motors) {
			motor.disable();
		}
	}
	
	public double get() {
		double value = motors[0].get();
		for (SpeedController motor : motors) {
			if (value != motor.get()) {
				throw new Error("Motors not getting at the same speed: " + getName());
			}
		}
		return value;
	}
	
	public void set(double speed) {
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.set(newSpeed);
		}
	}
	
	public void set(double speed, byte arg1) {
		set(speed);
	}
	
	public boolean getInverted() {
		return motors[0].getInverted();
	}
	
	public void setInverted(boolean inverted) {
		for (SpeedController motor : motors) {
			if (this.inverted != inverted) {
				motor.setInverted(!motor.getInverted());
				this.inverted = inverted;
			}
		}
	}
}
