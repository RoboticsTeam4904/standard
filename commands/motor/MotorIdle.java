package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.HashSet;
import java.util.Set;

/**
 * Idles the motor (sets speed to 0).
 *
 */
public class MotorIdle implements Command {
	protected final Motor motor;

	/**
	 * Constructor.
	 *
	 * @param motor
	 */
	public MotorIdle(Motor motor) {
		this.motor = motor;
		LogKitten.d("MotorIdle created");
	}

	@Override
	public void initialize() {
		motor.set(0);
		LogKitten.d("MotorIdle initialized");
	}

	@Override
	public void execute() {
		motor.set(0);
		LogKitten.d("MotorIdle executing");
	}

	@Override
	public boolean isFinished() {
		return false; // default command
	}

	@Override
	public void end(boolean interrupted) {
		LogKitten.d("MotorIdle interrupted");
	}
	@Override
	public Set<Subsystem> getRequirements() {
		Set<Subsystem> motors = new HashSet<Subsystem>();
		motors.add(motor);
		// TODO Auto-generated method stub
		return motors;
	}
}
