package org.usfirst.frc4904.cmdbased.subsystems.motor;


import org.usfirst.frc4904.cmdbased.OutPipable;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class EncodedMotor extends PIDSubsystem implements SpeedController, OutPipable {
	protected final SpeedController motor;
	protected final Encoder encoder;
	private final LogKitten logger;
	
	public EncodedMotor(String name, double P, double I, double D, SpeedController motor, Encoder encoder) {
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
		logger.d(Double.toString(speed), true);
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
	private EncodedMotorMode pipeMode;
	
	public enum EncodedMotorMode {
		PIDSET, SET;
	}
	
	public void writePipe(double[] data) {
		switch (pipeMode) {
			case PIDSET:
				set(data[0]);
				return;
			case SET:
				motor.set(data[0]);
				return;
			default:
				set(data[0]);
				return;
		}
	}
	
	public void setPipe(Enum mode) {
		pipeMode = (EncodedMotorMode) mode;
	}
}