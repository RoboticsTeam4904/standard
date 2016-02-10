package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.Linear;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.CANSpeedController;
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
		for (SpeedController motor : motors) {
			if (motor instanceof CANSpeedController) {
				((CANSpeedController) motor).setControlMode(0); // PercentVBus mode, closest to raw
			}
		}
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
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	@Override
	public void pidWrite(double speed) {
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.pidWrite(newSpeed);
		}
	}
	
	@Override
	public void disable() {
		for (SpeedController motor : motors) {
			motor.disable();
		}
	}
	
	/**
	 * Get the value returned by the underlying motors.
	 * For most motors, this is the most recently set speed.
	 * For some (like CANTalon) it may vary based on the mode.
	 *
	 * @return For most motors, the most recently set speed between -1.0 and 1.0.
	 */
	@Override
	public double get() {
		double value = motors[0].get();
		for (SpeedController motor : motors) {
			if (value != motor.get()) {
				throw new Error("Motors not getting at the same speed: " + getName());
			}
		}
		return value;
	}
	
	/**
	 * Set the motor speed. Passes through SpeedModifier.
	 *
	 * @param speed
	 *        The speed to set. Value should be between -1.0 and 1.0.
	 */
	@Override
	public void set(double speed) {
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.set(newSpeed);
		}
	}
	
	/**
	 * Set the motor speed. Passes through SpeedModifier.
	 *
	 * @deprecated For compatibility with CANJaguar. Use set(double speed)
	 * 			
	 * @param speed
	 *        The speed to set. Value should be between -1.0 and 1.0.
	 * @param syncGroup
	 *        On CANJaguar, the update group to add this Set() to, pending
	 *        UpdateSyncGroup(). If 0, update immediately.
	 */
	@Deprecated
	@Override
	public void set(double speed, byte syncGroup) {
		double newSpeed = speedModifier.modify(speed);
		for (SpeedController motor : motors) {
			motor.set(newSpeed, syncGroup);
		}
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
