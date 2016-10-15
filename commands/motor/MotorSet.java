package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * An indefinite command that, every execute cycle,
 * sets a motor to a variable speed. A method set(double)
 * is exposed which can be called to set the desired speed
 * of the motor. That value will, in turn, be passed to the
 * motor the next time execute is called.
 * 
 * This is better than setting the motor because it uses
 * requires to avoid having multiple attempts to set a
 * motor simultaneously. It also reduces the overhead of
 * running multiple MotorConstant commands whenever the
 * desired speed is changed.
 */
public class MotorSet extends Command {
	protected final SpeedController motor;
	protected double speed;
	
	public MotorSet(Motor motor) {
		super("MotorSet");
		this.motor = motor;
		speed = 0;
		LogKitten.d("MotorSet created for " + motor.getName());
		requires(motor);
		setInterruptible(true);
	}
	
	@Override
	protected void initialize() {
		LogKitten.d("MotorSet initialized");
	}
	
	/**
	 * Set the speed of the motor
	 */
	public void set(double speed) {
		this.speed = speed;
		LogKitten.d("MotorSet writePipe set to " + Double.toString(speed));
	}
	
	@Override
	protected void execute() {
		motor.set(speed);
		LogKitten.d("MotorSet executing with speed " + Double.toString(speed));
	}
	
	@Override
	protected void end() {
		motor.set(0);
		LogKitten.d("MotorSet ended (motor speed set to 0)");
	}
	
	@Override
	protected void interrupted() {
		LogKitten.d("MotorSet interupted (motor speed undefined)");
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}
