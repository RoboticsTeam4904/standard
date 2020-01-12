package org.usfirst.frc4904.standard.commands.motor;

import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * MotorPositionConstant is a Command that runs while setting a SensorMotor's
 * position to the provided (double) value. If (boolean) endOnArrival is
 * provided and set to false, the command will run indefinitely. Otherwise, the
 * command will end when the motor#onTarget() returns true (as set by internal
 * PID).
 *
 */
public class MotorPositionConstant extends CommandBase {
	protected PositionSensorMotor motor;
	protected double position;
	protected boolean endOnArrival;
	protected final CommandBase fallbackCommand;

	/**
	 * Constructor MotorPositionConstant is a Command that runs while setting a
	 * SensorMotor's position to the provided (double) value.
	 *
	 * @param motor
	 * @param position        position to set the motor to
	 * @param endOnArrival    If (boolean) endOnArrival is provided and set to
	 *                        false, the command will run indefinitely. Otherwise,
	 *                        the command will end when the motor#onTarget() returns
	 *                        true (as set by internal PID).
	 * @param fallbackCommand If the sensor fails for some reason, this command will
	 *                        be cancelled, then the fallbackCommand will start
	 */
	public MotorPositionConstant(PositionSensorMotor motor, double position, boolean endOnArrival,
			CommandBase fallbackCommand) {
		super();
		this.motor = motor;
		this.position = position;
		this.endOnArrival = endOnArrival;
		this.fallbackCommand = fallbackCommand;
	}

	public MotorPositionConstant(PositionSensorMotor motor, double position, boolean endOnArrival) {
		this(motor, position, endOnArrival, null);
	}

	public MotorPositionConstant(PositionSensorMotor motor, double position) {
		this(motor, position, true, null);
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
			return;
		}
	}

	@Override
	public void execute() {
		if (motor.checkSensorException() != null) {
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.schedule();
			}
		}
	}

	@Override
	public boolean isFinished() {
		if (endOnArrival) {
			return motor.onTarget();
		}
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		if (!interrupted) {
			motor.disableMotionController();
			// Please just die in a hole and never come back
			if (fallbackCommand != null && fallbackCommand.isScheduled()) {
				fallbackCommand.cancel();
			}
		}
	}
}
