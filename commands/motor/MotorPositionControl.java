package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.controllers.Controller;
import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Controls a SensorMotor's position directly from a Controller (e.g. Joystick or Xbox)
 *
 */
public class MotorPositionControl extends Command {
	protected final SensorMotor motor;
	protected final Controller controller;
	protected final int axis;
	protected final boolean invert;
	protected final Util.Range motorPositionRange;
	
	/**
	 * This Command directly controls a SensorMotor's position based on an axis of the Controller.
	 * This can allow an Operator to easily control the position of a single SensorMotor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param invert
	 */
	public MotorPositionControl(SensorMotor motor, Util.Range motorPositionRange, Controller controller, int axis, boolean invert) {
		super("MotorPositionControl");
		this.motor = motor;
		this.motorPositionRange = motorPositionRange;
		this.controller = controller;
		this.axis = axis;
		this.invert = invert;
		requires(motor);
		setInterruptible(true);
		LogKitten.d("MotorControl created for " + motor.getName());
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
		motor.setPosition(targetPosition);
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