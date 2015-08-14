package org.usfirst.frc4904.cmdbased.commands;


import org.usfirst.frc4904.cmdbased.OutPipable;
import org.usfirst.frc4904.cmdbased.subsystems.Motor;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public class MotorSpin extends Command implements OutPipable {
	private final Motor motor;
	private double speed;
	private final LogKitten logger;
	
	public MotorSpin(Motor motor) {
		super("WheelSpin");
		this.motor = motor;
		speed = 0;
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_ERROR);
		logger.v("MotorSpin created");
	}
	
	protected void initialize() {
		requires(motor);
		logger.v("MotorSpin initialized");
	}
	
	/**
	 * The motor pipe is set to control the speed of the motor
	 */
	public void writePipe(double[] data) {
		speed = data[0];
		logger.v("MotorSpin writePipe set to " + Double.toString(speed));
	}
	
	/**
	 * Motors can only spin, so there are no modes
	 */
	public void setPipe(int mode) {}
	
	/**
	 * Spins the motor at the speed set by writePipe
	 */
	protected void execute() {
		motor.set(speed);
		logger.d("MotorSpin executing with speed " + Double.toString(speed));
	}
	
	protected void end() {
		motor.set(0);
		logger.v("MotorSpin ended (motor speed set to 0)");
	}
	
	protected void interrupted() {
		logger.w("MotorSpin interupted (motor speed undefined)");
	}
	
	protected boolean isFinished() {
		return true;
	}
}
