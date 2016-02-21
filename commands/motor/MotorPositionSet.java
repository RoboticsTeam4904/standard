package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets a motor to a position and keeps it there using an encoder.
 */
public class MotorPositionSet extends Command {
	protected SensorMotor motor;
	protected double position;
	
	/**
	 * Constructor.
	 * The MotorSensorHold command holds a motor to a position.
	 *
	 * @param motor
	 *        A Motor that also implements PositionSensorMotor.
	 */
	public MotorPositionSet(SensorMotor motor) {
		super("MotorPositionSet");
		this.motor = motor;
		requires(motor);
		motor.enablePID();
		setInterruptible(true);
	}
	
	/**
	 * Sets the motor to this position.
	 *
	 * @param position
	 *        The position to set the motor to.
	 */
	public void setPosition(double position) {
		this.position = position;
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {
		motor.setPosition(position);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
}
