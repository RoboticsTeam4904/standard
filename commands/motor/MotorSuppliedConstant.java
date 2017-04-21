package org.usfirst.frc4904.standard.commands.motor;


import java.util.function.Supplier;
import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MotorSuppliedConstant extends Command {
	protected final Motor motor;
	protected final Supplier<Double> speedSupply;
	protected final double scale;
	protected final double offset;
	protected double speed;

	public MotorSuppliedConstant(String name, Motor motor, Supplier<Double> speedSupply, double scale, double offset) {
		super(name);
		this.motor = motor;
		requires(motor);
		this.speedSupply = speedSupply;
		this.scale = scale;
		this.offset = offset;
		LogKitten.d("MotorSuppliedConstant created for " + motor.getName());
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
