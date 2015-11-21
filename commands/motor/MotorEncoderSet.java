package org.usfirst.frc4904.standard.commands.motor;


import org.usfirst.frc4904.standard.subsystems.motor.EncodedMotor;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * This class handles speed based PID in a similar way to motor set.
 */
public class MotorEncoderSet extends MotorSet implements PIDOutput {
	protected Encoder encoder;
	protected PIDController pid;
	
	/**
	 * Constructs a (speed) encoded motor setting command. It tries to maintain the set speed
	 * using PID from the encoder.
	 * @param motor
	 * @param encoder
	 * @param P
	 * @param I
	 * @param D
	 * @param defaultDistancePerPulse
	 * @param minimum
	 * @param maximum
	 */
	public MotorEncoderSet(Motor motor, Encoder encoder, double P, double I, double D) {
		super(motor); // Calls requires (so we don't need to recall)
		this.encoder = encoder;
		pid = new PIDController(P, I, D, encoder, this);
		pid.setInputRange(-1.0, 1.0);
		pid.setOutputRange(-1.0, 1.0);
		pid.setContinuous(false); // Make sure this line is here. Otherwise gearboxes will die.
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
	}
	
	/**
	 * 
	 * @param motor: An encoded motor (if you don't know if the motor is encoded, try a "tryCastMotor"
	 * @param defaultDistancePerPulse
	 */
	public MotorEncoderSet(EncodedMotor motor) {
		this(motor, motor.getEncoder(), motor.getP(), motor.getI(), motor.getD());
	}
	
	/**
	 * This will attempt to return a constructed MotorEncoderSet
	 * from a motor you are not sure of.
	 * Please make sure you check null after using this
	 * function. There will not be an error if the motor
	 * is not and EncodedMotor.
	 * 
	 * @param motor
	 * @return
	 */
	public static MotorEncoderSet tryCastMotor(Motor motor) {
		if (motor instanceof EncodedMotor) {
			return new MotorEncoderSet((EncodedMotor) motor);
		}
		return null;
	}
	
	public void pidWrite(double output) {
		super.set(output);
	}
	
	public void set(double speed) {
		pid.setSetpoint(speed);
	}
}
