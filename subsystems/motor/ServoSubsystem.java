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
		this.isInverted = false;
		this.servos = servos;
		setInverted(isInverted);
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
	 * A class that wraps around a variable number of SpeedController objects to give them Subsystem functionality.
	 * Can also modify their speed with a SpeedModifier for things like scaling or brownout protection.
	 *
	 * @param motors
	 *        The SpeedControllers in this subsystem.
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
		return convertPositionToAngle(get());
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
			position = invertPosition(lastPosition);
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
			degrees = invertDegree(degrees);
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
	 * @param isInverted
	 *        The state of inversion, true is inverted.
	 */
	public void setInverted(boolean isInverted) {
		if (isInverted != this.isInverted) {
			lastPosition = invertPosition(lastPosition);
		}
		this.isInverted = isInverted;
	}

	// Internal helper functions
	/**
	 * Get the range of the servo in degrees
	 * 
	 * @return the range of the servo in degrees
	 */
	protected double getServoAngleRange() {
		return ServoSubsystem.MAX_SERVO_ANGLE - ServoSubsystem.MIN_SERVO_ANGLE;
	}

	/**
	 * Convert a servo set value to degrees.
	 * 
	 * @param value
	 *        servo set value to convert to degrees. Should be in the range [0, 1]
	 * @return the value converted to degrees
	 */
	protected double convertPositionToAngle(double value) {
		return value * getServoAngleRange() + ServoSubsystem.MIN_SERVO_ANGLE;
	}

	/**
	 * Convert a degree to a servo set value.
	 * 
	 * @param degrees
	 *        the servo degree to convert to a servo set
	 * @return a servo set value in the range [0, 1] (as long as the input degree was in the servo's range)
	 */
	protected double convertDegreesToValue(double degrees) {
		return ((degrees - ServoSubsystem.MIN_SERVO_ANGLE)) / getServoAngleRange();
	}

	/**
	 * Invert the given value (0 becomes 1, 1 becomes 0)
	 * 
	 * @param value
	 *        The value to invert. Should be in the range [0, 1]
	 * @return the inverted value
	 */
	protected double invertPosition(double value) {
		return -1 * value + 1;
	}

	/**
	 * Invert the given degree ({@link #MIN_SERVO_ANGLE kMinServoAngle} becomes {@link #MAX_SERVO_ANGLE kMaxServoAngle}, {@link #MAX_SERVO_ANGLE kMaxServoAngle} becomes {@link #MIN_SERVO_ANGLE kMinServoAngle})
	 * 
	 * @param degrees
	 *        The degree to invert
	 * @return the inverted degree
	 */
	protected double invertDegree(double degrees) {
		double value = convertDegreesToValue(degrees);
		value = invertPosition(value);
		return convertPositionToAngle(value);
	}
}
