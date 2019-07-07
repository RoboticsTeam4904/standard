package org.usfirst.frc4904.standard.commands.motor;


import java.util.function.Supplier;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Set the speed of a motor based on a speed supplied on initialize.
 * This should be used when the speed might change between construction time and when the motor should start.
 */
public class MotorSuppliedConstant extends Command {
	protected final Motor motor;
	protected final Supplier<Double> speedSupply;
	protected double speed;

	/**
	 * Set the speed of a motor based on a speed supplied on initialize.
	 * This should be used when the speed might change between construction time and when the motor should start.
	 * 
	 * @param name
	 * @param motor
	 * @param speedSupply
	 */
	public MotorSuppliedConstant(String name, Motor motor, Supplier<Double> speedSupply) {
		super(name);
		this.motor = motor;
		requires(motor);
		this.speedSupply = speedSupply;
		setInterruptible(true);
		LogKitten.d("MotorSuppliedConstant created for " + motor.getName());
	}

	/**
	 * Set the speed of a motor based on a speed supplied on initialize.
	 * This should be used when the speed might change between construction time and when the motor should start.
	 * 
	 * @param name
	 * @param motor
	 * @param speedSupply
	 */
	public MotorSuppliedConstant(Motor motor, Supplier<Double> speedSupply) {
		this("MotorSuppliedConstant", motor, speedSupply);
	}

	@Override
	protected void initialize() {
		speed = speedSupply.get();
		motor.set(speed);
	}

	@Override
	protected void execute() {
		motor.set(speed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {
		LogKitten.d("MotorSuppliedConstant interrupted");
	}
}
