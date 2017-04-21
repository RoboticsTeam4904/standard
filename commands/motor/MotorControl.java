package org.usfirst.frc4904.standard.commands.motor;


import java.util.function.Supplier;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.PositionSensorMotor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Controls a Motor directly from a Controller (e.g. Joystick or Xbox)
 *
 *
 */
public class MotorControl extends Command {
	protected final Motor motor;
	protected final Supplier<Double> speedSupplier;
	protected final double scale;
	protected final double offset;

	/**
	 * This Command directly controls a Motor based on a double supplier;
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param name
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 * @param offset
	 *        A constant to add to the motor's speed
	 *        Useful for using a controller for fine-tuning a constant speed
	 */
	public MotorControl(String name, Motor motor, Supplier<Double> speedSupplier, double scale, double offset) {
		super(name);
		this.motor = motor;
		this.speedSupplier = speedSupplier;
		this.scale = scale;
		this.offset = offset;
		requires(motor);
		setInterruptible(true);
		LogKitten.d("MotorControl created for " + motor.getName());
	}

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param name
	 * @param motor
	 * @param controller
	 * @param axis
	 */
	public MotorControl(String name, Motor motor, Supplier<Double> speedSupplier) {
		this(name, motor, speedSupplier, 1.0, 0.0);
	}

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public MotorControl(Motor motor, Supplier<Double> speedSupplier, double scale) {
		this("MotorControl", motor, speedSupplier, 1.0, 0.0);
	}

	/**
	 * This Command directly controls a Motor based on an axis of the Controller.
	 * This can allow an Operator to easily control a single Motor from an axis of the Controller.
	 *
	 * @param motor
	 * @param controller
	 * @param axis
	 * @param scale
	 */
	public MotorControl(Motor motor, Supplier<Double> speedSupplier) {
		this(motor, speedSupplier, 1.0);
	}

	@Override
	protected void initialize() {
		LogKitten.d("MotorControl initialized");
		if (motor instanceof PositionSensorMotor) {
			((PositionSensorMotor) motor).disableMotionController();
		}
	}

	@Override
	protected void execute() {
		LogKitten.d("MotorControl executing: " + speedSupplier.get());
		motor.set(speedSupplier.get() * scale + offset);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {
		LogKitten.d("MotorControl interrupted");
	}
}