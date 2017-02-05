package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.Idle;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ServoSubsystem extends Subsystem {
	protected final Servo[] servos;
	protected boolean isInverted;
	protected double lastPosition;
	// Constants from wpilib's Servo.java.
	protected static final double MIN_SERVO_ANGLE = 0.0;
	protected static final double MAX_SERVO_ANGLE = 180.0;

	/**
	 * A class that wraps around a variable number of Servo objects to give them Subsystem functionality.
	 *
	 * @param name
	 *        The name for the motor
	 * @param isInverted
	 *        Inverts the direction of all of the Servos.
	 *        This does not override the individual inversions of the servos.
	 * @param servos
	 *        The Servos in this subsystem.
	 *        Can be a single Servo or multiple Servos.
	 */
	public ServoSubsystem(String name, boolean isInverted, Servo... servos) {
		super(name);
		this.servos = servos;
		this.isInverted = isInverted;
		set(0);
	}

	/**
	 * A class that wraps around a variable number of Servo objects to give them Subsystem functionality.
	 *
	 * @param name
	 *        The name for the motor.
	 * @param servos
	 *        The Servos in this subsystem.
	 *        Can be a single Servo or multiple Servos.
	 */
	public ServoSubsystem(String name, Servo... servos) {
		this(name, false, servos);
	}

	/**
	 * A class that wraps around a variable number of Servo objects to give them Subsystem functionality.
	 *
	 * @param isInverted
	 *        Inverts the direction of all of the Servos.
	 *        This does not override the individual inversions of the servos.
	 * @param servos
	 *        The Servos in this subsystem.
	 *        Can be a single Servo or multiple Servos.
	 */
	public ServoSubsystem(boolean isInverted, Servo... servos) {
		this("ServoSubsystem", isInverted, servos);
	}

	/**
	 * A class that wraps around a variable number of Servo objects to give them Subsystem functionality.
	 *
	 * @param servos
	 *        The Servos in this subsystem.
	 *        Can be a single Servo or multiple Servo.
	 */
	public ServoSubsystem(Servo... servos) {
		this("ServoSubsystem", servos);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Idle(this));
	}

	/**
	 * Get the servo position.
	 *
	 * <p>
	 * Servo positions range from 0.0 to 1.0 corresponding to the range of full left to full right.
	 *
	 * @return Position from 0.0 to 1.0.
	 */
	public double get() {
		return lastPosition;
	}

	/**
	 * Get the servo position.
	 *
	 * <p>
	 * Servo positions range from 0.0 to 1.0 corresponding to the range of full left to full right.
	 * (This method is equivalent to the {@link #get() get()} method.)
	 *
	 * @return Position from 0.0 to 1.0.
	 */
	public double getPosition() {
		return get();
	}

	/**
	 * Get the servo angle.
	 *
	 * @return The angle in degrees to which the servo is set.
	 */
	public double getAngle() {
		return ServoSubsystem.convertPositionToAngle(get());
	}

	/**
	 * Set the servo position.
	 *
	 * <p>
	 * Servo values range from 0.0 to 1.0 corresponding to the range of full left to full right.
	 *
	 * @param position
	 *        Position from 0.0 to 1.0.
	 */
	public void set(double position) {
		if (isInverted) {
			position = ServoSubsystem.invertPosition(position);
		}
		for (Servo servo : servos) {
			servo.set(position);
		}
		lastPosition = position;
	}

	/**
	 * Set the servo position.
	 *
	 * <p>
	 * Servo positions range from 0.0 to 1.0 corresponding to the range of full left to full right.
	 * (This method is equivalent to the {@link #set(double) set()} method.)
	 *
	 * @param value
	 *        Position from 0.0 to 1.0.
	 */
	public void setPosition(double position) {
		set(position);
	}

	/**
	 * Set the servo angle.
	 *
	 * @return The angle in degrees to which the servo is set.
	 */
	public void setAngle(double degrees) {
		if (isInverted) {
			degrees = ServoSubsystem.invertDegree(degrees);
		}
		for (Servo servo : servos) {
			servo.setAngle(degrees);
		}
	}

	/**
	 * Get whether this entire servo is inverted.
	 *
	 * @return isInverted
	 *         The state of inversion, true is inverted.
	 */
	public boolean getInverted() {
		return isInverted;
	}

	/**
	 * Set whether this entire servo is inverted.
	 * Note that this will also convert the values returned by {@link #get() get()}, {@link #getPosition() getPosition()},
	 * and {@link #getAngle() getAngle()} to the new coordinate system, so anything continuously reading these methods
	 * will see a discontinuity.
	 * 
	 * @param shouldBeInverted
	 *        The desired state of inversion, true is inverted.
	 */
	public void setInverted(boolean shouldBeInverted) {
		if (shouldBeInverted != isInverted) {
			lastPosition = ServoSubsystem.invertPosition(lastPosition);
		}
		isInverted = shouldBeInverted;
	}

	// Internal helper functions
	/**
	 * Get the range of the servo in degrees
	 * 
	 * @return the range of the servo in degrees
	 */
	protected static double getServoAngleRange() {
		return ServoSubsystem.MAX_SERVO_ANGLE - ServoSubsystem.MIN_SERVO_ANGLE;
	}

	/**
	 * Convert a servo set value to degrees.
	 * 
	 * @param value
	 *        servo set value to convert to degrees. Should be in the range [0, 1]
	 * @return the value converted to degrees
	 */
	protected static double convertPositionToAngle(double value) {
		return value * ServoSubsystem.getServoAngleRange() + ServoSubsystem.MIN_SERVO_ANGLE;
	}

	/**
	 * Convert a degree to a servo set value.
	 * 
	 * @param degrees
	 *        the servo degree to convert to a servo set
	 * @return a servo set value in the range [0, 1] (as long as the input degree was in the servo's range)
	 */
	protected static double convertDegreesToValue(double degrees) {
		return ((degrees - ServoSubsystem.MIN_SERVO_ANGLE)) / ServoSubsystem.getServoAngleRange();
	}

	/**
	 * Invert the given value (0 becomes 1, 1 becomes 0)
	 * 
	 * @param value
	 *        The value to invert. Should be in the range [0, 1]
	 * @return the inverted value
	 */
	protected static double invertPosition(double value) {
		return 1 - value;
	}

	/**
	 * Invert the given degree ({@link #MIN_SERVO_ANGLE kMinServoAngle} becomes {@link #MAX_SERVO_ANGLE kMaxServoAngle}, {@link #MAX_SERVO_ANGLE kMaxServoAngle} becomes {@link #MIN_SERVO_ANGLE kMinServoAngle})
	 * 
	 * @param degrees
	 *        The degree to invert
	 * @return the inverted degree
	 */
	protected static double invertDegree(double degrees) {
		double value = ServoSubsystem.convertDegreesToValue(degrees);
		value = ServoSubsystem.invertPosition(value);
		return ServoSubsystem.convertPositionToAngle(value);
	}
}
