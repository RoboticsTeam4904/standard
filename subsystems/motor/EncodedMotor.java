package org.usfirst.frc4904.cmdbased.subsystems.motor;


import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class EncodedMotor extends PIDSubsystem implements SpeedController {
	protected final Motor motor;
	protected final Encoder encoder;
	private final LogKitten logger;
	
	public EncodedMotor(String name, double P, double I, double D, Motor motor, Encoder encoder) {
		super(name, P, I, D);
		this.motor = motor;
		logger = new LogKitten(LogKitten.LEVEL_DEBUG, LogKitten.LEVEL_DEBUG);
		this.encoder = encoder;
		setOutputRange(-1, 1);
		getPIDController().setContinuous(false);
	}
	
	protected void initDefaultCommand() {}
	
	protected double returnPIDInput() {
		return encoder.getRate();
	}
	
	protected void usePIDOutput(double speed) {
		logger.d(Double.toString(speed));
		motor.set(speed);
	}
	
	public void set(double speed) {
		setSetpoint(speed);
	}
	
	public void pidWrite(double speed) {
		set(speed);
	}
	
	public double get() {
		return motor.get();
	}
	
	public void set(double speed, byte arg1) {
		set(speed);
	}
	
	/**
	 * This allows you to write to the motor directly.
	 * For overrides (you terrible person)
	 * 
	 * @return the motor of the EncodedMotor
	 */
	public Motor getMotor() {
		return motor;
	}
	
	public boolean isPID() {
		return getPIDController().isEnable();
	}
}