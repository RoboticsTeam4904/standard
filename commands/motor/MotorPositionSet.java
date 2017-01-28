package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets a motor to a position and keeps it there using an encoder.
 */
public class MotorPositionSet extends Command {
	protected PositionSensorMotor motor;
	protected double position;
	protected final Command fallbackCommand;

	/**
	 * Constructor.
	 * The MotorSensorHold command holds a motor to a position.
	 *
	 * @param motor
	 *        A Motor that also implements PositionSensorMotor.
	 * @param fallbackCommand
	 *        If the sensor fails for some reason, this command will be cancelled, then the fallbackCommand will start
	 */
	public MotorPositionSet(PositionSensorMotor motor, Command fallbackCommand) {
		super("MotorPositionSet");
		this.motor = motor;
		requires(motor);
		setInterruptible(true);
		this.fallbackCommand = fallbackCommand;
	}

	/**
	 * Constructor.
	 * The MotorSensorHold command holds a motor to a position.
	 *
	 * @param motor
	 *        A Motor that also implements PositionSensorMotor.
	 */
	public MotorPositionSet(PositionSensorMotor motor) {
		this(motor, null);
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
	protected void initialize() {
		try {
			motor.reset();
			motor.enableMotionController();
			motor.setPositionSafely(position);
		}
		catch (InvalidSensorException e) {
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.start();
			}
		}
	}

	@Override
	protected void execute() {
		Exception potentialSensorException = motor.checkSensorException();
		if (potentialSensorException != null) {
			cancel();
			if (fallbackCommand != null && !fallbackCommand.isRunning()) {
				fallbackCommand.start();
			}
		}
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
