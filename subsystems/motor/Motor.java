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
	
	/**
	 * A SpeedController Subsystem.
	 * 
	 * @param name
	 * @param inverted
	 * @param slopeController
	 *        Modifies the relationship between
	 *        input speed and set speed. Can also
	 *        regulate set speed to prevent
	 *        brownouts.
	 * @param motors
	 */
	public Motor(String name, boolean inverted, SpeedModifier slopeController, SpeedController... motors) {
		super(name);
		this.motors = motors;
		this.speedModifier = slopeController;
		setInverted(inverted);
	}
	
	/**
	 * A SpeedController Subsystem.
	 * 
	 * @param name
	 * @param inverted
	 * @param motors
	 */
	public Motor(String name, boolean inverted, SpeedController... motors) {
		this(name, inverted, new Linear(), motors);
	}
	
	/**
	 * A SpeedController Subsystem.
	 * 
	 * @param name
	 * @param slopeController
	 *        Modifies the relationship between
	 *        input speed and set speed. Can also
	 *        regulate set speed to prevent
	 *        brownouts.
	 * @param motors
	 */
	public Motor(String name, SpeedModifier slopeController, SpeedController... motors) {
		this(name, false, slopeController, motors);
	}
	
	/**
	 * A SpeedController Subsystem.
	 * 
	 * @param name
	 * @param motors
	 */
	public Motor(String name, SpeedController... motors) {
		this(name, false, new Linear(), motors);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	public void pidWrite(double speed) {
		set(speed);
	}
	
	/**
	 * Disables motors
	 */
	public void disable() {
		for (SpeedController motor : motors) {
			motor.disable();
		}
	}
	
	/**
	 * Gets speed of motors
	 */
	public double get() {
		return motors[0].get();
	}
	
	/**
	 * Sets speed of motors
	 */
	public void set(double speed) {
		speed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.set(speed);
		}
	}
	
	/**
	 * @deprecated
	 */
	public void set(double speed, byte arg1) {
		set(speed);
	}
	
	/**
	 * Are the motors inverted?
	 */
	public boolean getInverted() {
		return motors[0].getInverted();
	}
	
	/**
	 * Inverts the motors
	 * 
	 * If true, inverts each motor individually
	 * If false, does not change motors
	 * This is necessary for gearboxes with
	 * opposite turning motors.
	 */
	public void setInverted(boolean inverted) {
		for (SpeedController motor : motors) {
			if (inverted) {
				motor.setInverted(!motor.getInverted());
			}
		}
	}
}
