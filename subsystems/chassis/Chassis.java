package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Generic chassis class.
 * In the 4904 standard, the Chassis is
 * treated as a container for motors and
 * as a calculator for motor speeds.
 *
 */
public abstract class Chassis extends Subsystem {
	protected int numberWheels;
	protected double[] motorSpeeds;
	protected Motor[] motors;
	
	/**
	 * 
	 * @param name
	 * @param motors
	 *        :
	 *        all the motors that are part of this chassis. Pass from front to back, left to right
	 */
	public Chassis(String name, Motor... motors) {
		super(name);
		this.motors = motors;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ChassisIdle(this));
	}
	
	/**
	 * returns the number of wheels
	 * 
	 * @return
	 */
	public int getNumberWheels() {
		return numberWheels;
	}
	
	/**
	 * returns an array of motors of the size getNumberWheels in the order that they were passed to the constructor
	 * 
	 * @return
	 */
	public Motor[] getMotors() {
		return motors;
	}
	
	/**
	 * returns an array of the correct motor speeds
	 * retrieves the speed calculated with the values
	 * inputed using the move functions.
	 * 
	 * @return
	 */
	public double[] getMotorSpeeds() {
		return motorSpeeds;
	}
	
	/**
	 * sets the movement to be calculated by the chassis
	 * to be the movement producing the speed at the angle
	 * with the turn speed.
	 * 
	 * @param speed
	 * @param angle
	 * @param turnSpeed
	 */
	public abstract void move2dp(double speed, double angle, double turnSpeed);
	
	/**
	 * sets the movement to be calculated by the chassis
	 * to be the movement producing the speed in the
	 * X and Y directions with the turn speed
	 * 
	 * @param xSpeed
	 * @param ySpeed
	 * @param turnSpeed
	 */
	public abstract void move2dc(double xSpeed, double ySpeed, double turnSpeed);
	
	/**
	 * sets the movement to be calculated by the chassis
	 * to be the movement producing the speed
	 * with the turn speed. Assumed to be straight forward.
	 * 
	 * @param speed
	 * @param turnSpeed
	 */
	public abstract void move(double speed, double turnSpeed);
}
