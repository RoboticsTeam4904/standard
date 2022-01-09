package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.Noop;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets a motor to a position and keeps it there using an encoder.
 */
public class MotorVelocitySet extends CommandBase {
	protected VelocitySensorMotor motor;
	protected double velocity;
	protected final CommandBase fallbackCommand;

	/**
	 * Constructor. The MotorVelocitySet command brings a motor to a velocity.
	 *
	 * @param motor           A Motor that also implements VelocitySensorMotor.
	 * @param velocity the velocity to set the motor to.
	 * @param fallbackCommand If the sensor fails for some reason, this command will
	 *                        be cancelled, then the fallbackCommand will start
	 */
	public MotorVelocitySet(String name, VelocitySensorMotor motor, double velocity, CommandBase fallbackCommand) {
		super();
		setName(name);
		// addRequirements(motor);
		this.motor = motor;
		this.fallbackCommand = fallbackCommand;
		this.setVelocity(velocity);
	}

	/**
	 * Constructor. The MotorVelocitySet command brings a motor to a velocity.
	 * 
	 * @param motor A Motor that also implements VelocitySensorMotor.
	 * @param velocity the velocity to set the motor to.
	 */
	public MotorVelocitySet(String name, VelocitySensorMotor motor, double velocity) {
		this(name, motor, velocity, new Noop());
	}

	/**
	 * Constructor. The MotorVelocitySet command brings a motor to a velocity.
	 * 
	 * @param motor A Motor that also implements VelocitySensorMotor.
	 * @param velocity the velocity to set the motor to.
	 */
	public MotorVelocitySet(VelocitySensorMotor motor, double velocity) {
		this("MotorVelocitySet", motor, velocity);
	}

	/**
	 * Sets the motor to this velocity.
	 *
	 * @param position The velocity to set the motor to.
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	@Override
	public void initialize() {
		try {
			motor.reset();
			motor.enableMotionController();
			motor.set(velocity);
		} catch (InvalidSensorException e) {
			cancel();
			fallbackCommand.schedule();
		}
	}

	@Override
	public void execute() {
		motor.set(velocity);
		Exception potentialSensorException = motor.checkSensorException();
		if (potentialSensorException != null) {
			cancel();
			if (!fallbackCommand.isScheduled()) {
				fallbackCommand.schedule();
			}
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		motor.disableMotionController();
		motor.set(0.0);
		if (!interrupted) {
			motor.set(0.0);
		}
	}
}
