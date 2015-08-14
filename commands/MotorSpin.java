package org.usfirst.frc4904.cmdbased.commands;


import org.usfirst.frc4904.cmdbased.OutPipable;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorSpin extends Command implements OutPipable {
	private final SpeedController motor;
	private double speed;
	private final LogKitten logger;
	
	/**
	 * This command drives the motor at a variable speed via a pipe
	 * 
	 * @param motor
	 */
	public <A extends Subsystem & SpeedController> MotorSpin(A motor) {
		super("WheelSpin");
		this.motor = motor;
		speed = 0;
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_VERBOSE);
		logger.v("MotorSpin created");
		requires(motor);
		setInterruptible(true);
	}
	
	protected void initialize() {
		logger.v("MotorSpin initialized");
	}
	
	/**
	 * The motor pipe is set to control the speed of the motor
	 */
	public void writePipe(double[] data) {
		speed = data[0];
		logger.d("MotorSpin writePipe set to " + Double.toString(speed));
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
		return false;
	}
}
