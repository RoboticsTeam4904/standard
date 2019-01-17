package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Controls a SensorMotor's position directly from a Controller (e.g. Joystick or Xbox)
 *
 */
public class MotorPositionControl extends Command {
	protected final PositionSensorMotor motor;
	protected final Controller controller;
	protected final int axis;
	protected final boolean invert;
	protected final Util.Range motorPositionRange;
	protected final Command fallbackCommand;

	/**
	 * This Command directly controls a SensorMotor's position based on an axis of the Controller.
	 * This can allow an Operator to easily control the position of a single SensorMotor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param invert
	 * @param fallbackCommand
	 *        If the sensor fails for some reason, this command will be cancelled, then the fallbackCommand will start
	 */
	public MotorPositionControl(PositionSensorMotor motor, Util.Range motorPositionRange, Controller controller, int axis, boolean invert, Command fallbackCommand) {
		super("MotorPositionControl");
		this.motor = motor;
		this.motorPositionRange = motorPositionRange;
		this.controller = controller;
		this.axis = axis;
		this.invert = invert;
		this.fallbackCommand = fallbackCommand;
		requires(motor);
		setInterruptible(true);
		LogKitten.d("MotorControl created for " + motor.getName());
	}

	/**
	 * This Command directly controls a SensorMotor's position based on an axis of the Controller.
	 * This can allow an Operator to easily control the position of a single SensorMotor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param invert
	 */

	public MotorPositionControl(PositionSensorMotor motor, Util.Range motorPositionRange, Controller controller, int axis, boolean invert) {
		this(motor, motorPositionRange, controller, axis, invert, null);
	}

	@Override
	protected void initialize() {
		LogKitten.d("MotorPositionControl initialized");
	}

	@Override
	protected void execute() {
		double axisValue = invert ? -1.0 * controller.getAxis(axis) : controller.getAxis(axis);
		double targetPosition = motorPositionRange.scaleValue(axisValue);
		LogKitten.d("MotorPositionControl executing: " + targetPosition);
		try {
			motor.setPositionSafely(targetPosition);
		}
		catch (InvalidSensorException e) {
			cancel();
			if (fallbackCommand != null) {
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
	protected void interrupted() {
		LogKitten.d("MotorPositionControl interrupted");
	}
}