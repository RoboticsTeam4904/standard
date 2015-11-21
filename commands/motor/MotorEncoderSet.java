package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * This class handles speed based PID in a similar way to motor set.
 */
public class MotorEncoderSet extends MotorSet implements PIDOutput {
	protected Encoder encoder;
	PIDController pid;
	
	public MotorEncoderSet(Motor motor, Encoder encoder, double P, double I, double D) {
		super(motor); // Calls requires (so we don't need to recall)
		this.encoder = encoder;
		pid = new PIDController(P, I, D, encoder, this);
		pid.setInputRange(-1.0, 1.0);
		pid.setOutputRange(-1.0, 1.0);
		pid.setContinuous(false); // Make sure this line is here. Otherwise gearboxes will die.
	}
	
	public void pidWrite(double output) {
		super.set(output);
	}
	
	public void set(double speed) {
		pid.setSetpoint(speed);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
}
