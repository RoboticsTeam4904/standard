package org.usfirst.frc4904.cmdbased.subsystems.chassis;


import org.usfirst.frc4904.cmdbased.InPipable;
import org.usfirst.frc4904.cmdbased.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.cmdbased.subsystems.Motor;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Chassis extends Subsystem implements InPipable {
	protected int numberWheels;
	protected double[] motorSpeeds;
	
	public Chassis(String name) {
		super(name);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ChassisIdle(this));
	}
	
	public int getNumberWheels() {
		return numberWheels;
	}
	
	public abstract Motor[] getMotors();
	
	public double[] readPipe() {
		return motorSpeeds;
	}
	
	/**
	 * The Chassis always returns the motor speeds
	 */
	public void setPipe(int mode) {}
	
	public abstract void move2dp(double speed, double angle, double turnSpeed);
	
	public abstract void move2dc(double xSpeed, double ySpeed, double turnSpeed);
	
	public abstract void move(double speed, double turnSpeed);
}
