package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.motor.MotorSet;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorGroup extends Subsystem implements SpeedController {
	protected MotorSet[] motorSets;
	protected double currentSpeed;
	
	public MotorGroup(String name, Motor... motors) {
		super(name);
		motorSets = new MotorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			motorSets[i] = new MotorSet(motors[i]);
			motorSets[i].start();
		}
		currentSpeed = 0.0;
	}
	
	protected void initDefaultCommand() {}
	
	public void pidWrite(double arg0) {
		for (MotorSet motorSet : motorSets) {
			motorSet.set(arg0);
		}
	}
	
	public void disable() {}
	
	public double get() {
		return currentSpeed;
	}
	
	public void set(double arg0) {
		for (MotorSet motorSet : motorSets) {
			if (!motorSet.isRunning()) {
				motorSet.start();
			}
			motorSet.set(arg0);
		}
		currentSpeed = arg0;
	}
	
	public void set(double arg0, byte arg1) {
		for (MotorSet motorSet : motorSets) {
			if (!motorSet.isRunning()) {
				motorSet.start();
			}
			motorSet.set(arg0);
		}
		currentSpeed = arg0;
	}

	public boolean getInverted() {
		return false;
	}

	/**
	 * An entire motor group can not be inverted.
	 * Do not use this function.
	 */
	public void setInverted(boolean arg) {
	}
}
