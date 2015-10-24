package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorIdle extends Command {
	private final SpeedController motor;
	
	public <A extends Subsystem & SpeedController> MotorIdle(A motor) {
		super("MotorIdle");
		this.motor = motor;
		requires(motor);
		setInterruptible(true); // default command
		LogKitten.v("MotorIdle created");
	}
	
	protected void initialize() {
		motor.set(0);
		LogKitten.v("MotorIdle initialized");
	}
	
	protected void execute() {
		motor.set(0);
		LogKitten.d("MotorIdle executing");
	}
	
	protected void end() {
		LogKitten.v("MotorIdle ended");
	}
	
	protected void interrupted() {
		LogKitten.w("MotorIdle interupted");
	}
	
	protected boolean isFinished() {
		return false; // default command
	}
}
