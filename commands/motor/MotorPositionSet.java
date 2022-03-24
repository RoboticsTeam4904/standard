package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets a motor to a position and keeps it there using an encoder.
 */
public class MotorPositionSet extends CommandBase {
	protected PositionSensorMotor motor;
	protected double position;
	protected final CommandBase fallbackCommand;

	/**
	 * Constructor. The MotorSensorHold command holds a motor to a position.
	 *
	 * @param motor           A Motor that also implements PositionSensorMotor.
	 * @param fallbackCommand If the sensor fails for some reason, this command will
	 *                        be cancelled, then the fallbackCommand will start
	 */
	public MotorPositionSet(String name, PositionSensorMotor motor, CommandBase fallbackCommand) {
		super();
		setName(name);
		addRequirements(motor);
		this.motor = motor;
		this.fallbackCommand = fallbackCommand;
	}

	/**
	 * Constructor. The MotorSensorHold command holds a motor to a position.
	 *
	 * @param motor A Motor that also implements PositionSensorMotor.
	 */
	public MotorPositionSet(PositionSensorMotor motor) {
		this("MotorPositionSet", motor, null);
	}

	/**
	 * Sets the motor to this position.
	 *
	 * @param position The position to set the motor to.
	 */
	public void setPosition(double position) {
		this.position = position;
	}

	@Override
	public void initialize() {
		try {
			motor.reset();
			motor.enableMotionController();
			motor.setPositionSafely(position);
		} catch (InvalidSensorException e) {
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.schedule();
			}
		}
	}

	@Override
	public void execute() {
		Exception potentialSensorException = motor.checkSensorException();
		if (potentialSensorException != null) {
			cancel();
			if (fallbackCommand != null && !fallbackCommand.isScheduled()) {
				fallbackCommand.schedule();
			}
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
