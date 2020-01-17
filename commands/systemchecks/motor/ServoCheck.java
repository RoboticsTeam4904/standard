package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import org.usfirst.frc4904.standard.commands.systemchecks.SubsystemCheck;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;

/**
 * Systemcheck on ServoSubsystems
 */
public class ServoCheck extends SubsystemCheck {
	protected static final double DEFAULT_ANGLE = 50;
	protected final double angle;
	protected final ServoSubsystem[] servos;

	/**
	 * Systemcheck on ServoSubsystems
	 * 
	 * @param name
	 *               Name of check
	 * @param angle
	 *               Angle to set servos
	 * @param servos
	 *               ServoSubsystems to test
	 */
	public ServoCheck(String name, double angle, ServoSubsystem... servos) {
		super(name, servos);
		this.servos = servos;
		this.angle = angle;
	}

	/**
	 * Systemcheck on ServoSubsystems
	 * 
	 * @param name
	 *               Name of check
	 * @param servos
	 *               ServoSubsystems to test
	 */
	public ServoCheck(String name, ServoSubsystem... servos) {
		this(name, DEFAULT_ANGLE, servos);
	}

	/**
	 * Systemcheck on ServoSubsystems
	 * 
	 * @param angle
	 *               Angle to set servos
	 * @param servos
	 *               ServoSubsystems to test
	 */
	public ServoCheck(double angle, ServoSubsystem... servos) {
		this("ServoCheck", angle, servos);
	}

	/**
	 * Systemcheck on ServoSubsystems
	 * 
	 * @param servos
	 *               ServoSubsystems to test
	 */
	public ServoCheck(ServoSubsystem... servos) {
		this("ServoCheck", servos);
	}

	/**
	 * Tests setting of servo angle
	 */
	public void initialize() {
		for (ServoSubsystem servo : servos) {
			try {
				servo.setAngle(angle);
			}
			catch (Exception e) {
				updateStatusFail(servo.getName(), e);
			}
		}
	}
}