/**
 * 
 */
package org.usfirst.frc4904.standard.simulation.devices;

import org.usfirst.frc4904.standard.simulation.simulation.PhysicsSimulator;

public class SimulatedMotor extends org.usfirst.frc4904.standard.subsystems.motor.Motor {
	
	// private GpioPinDigitalOutput forward, backward;
	// private GpioPinPwmOutput enable;
	
	/**
	 * Constructs a new DigitalMotor.
	 * @param forward the forward digital pin (for forward direction)
	 * @param backward the backward digital pin (for backward motion)
	 * @param enable the enable analog pin, giving the magnitude of the speed
	 */

	double voltage;
	double torque;
	double angle;
	public double angularVelocity;
	double changeAngularVelocity;
	double current;
	
	double TORQUE_CONSTANT;
	double ANGULAR_VELOCITY_CONSTANT;
	double RESISTANCE;
	double ROTATIONAL_INERTIA;
	double MOTOR_X;
	double MOTOR_Y;
	double CIRCLE_RADIUS;
	
	
	public SimulatedMotor(String name, boolean isInverted, TalonSRX speedController) {
		super(name);
		this.voltage = 2;
		this.torque = 2;
		this.angle = 0;
		this.angularVelocity = 0;
			
		this.TORQUE_CONSTANT = 4.904;
		this.ANGULAR_VELOCITY_CONSTANT = 5.026; 
		this.RESISTANCE = 1.323;
		this.ROTATIONAL_INERTIA = 6.9;
		this.MOTOR_X = 150;
		this.MOTOR_Y = 250;
		this.CIRCLE_RADIUS = 10;
		
		this.current = this.voltage/this.RESISTANCE;
		this.changeAngularVelocity = (this.TORQUE_CONSTANT*(
		  this.voltage-(this.angularVelocity/this.ANGULAR_VELOCITY_CONSTANT))) / 
		  (this.RESISTANCE * this.current);
		
	}
	
    /**
    * Sets the motor to operate at a given voltage.
    * @param speed the voltage at which the motors should be set
    */
    // @Override
    public void set(float speed) {
		this.voltage = speed;
		this.changeAngularVelocity = (this.TORQUE_CONSTANT*(
		  this.voltage-(this.angularVelocity/this.ANGULAR_VELOCITY_CONSTANT))) / 
		  (this.RESISTANCE * this.current);
		this.angle = this.angle + this.angularVelocity * PhysicsSimulator.inst.timeStep;
		if (this.angle >= 360) {
		  this.angle -= 360;
		}
		this.angularVelocity = this.angularVelocity + this.changeAngularVelocity * PhysicsSimulator.inst.timeStep; 
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pidWrite(double speed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub

	}

	@Override
	public double get() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void set(double speed) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getInverted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInverted(boolean isInverted) {
		// TODO Auto-generated method stub

	}
}
