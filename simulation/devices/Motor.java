/**
 * 
 */
package org.usfirst.frc4904.ourWPI.devices;

public class Motor extends Motor /*implements com.diozero.api.OutputDeviceInterface*/ {
	
	// private GpioPinDigitalOutput forward, backward;
	// private GpioPinPwmOutput enable;
	
	/**
	 * Constructs a new DigitalMotor.
	 * @param forward the forward digital pin (for forward direction)
	 * @param backward the backward digital pin (for backward motion)
	 * @param enable the enable analog pin, giving the magnitude of the speed
	 */

	float voltage;
	float torque;
	float angle;
	public float angularVelocity;
	float changeAngularVelocity;
	float current;
	
	float TORQUE_CONSTANT;
	float ANGULAR_VELOCITY_CONSTANT;
	float RESISTANCE;
	float ROTATIONAL_INERTIA;
	float MOTOR_X;
	float MOTOR_Y;
	float CIRCLE_RADIUS;
	
	
	public Motor(String name, boolean isInverted, TalonSRX speedController) {
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
		this.angle = this.angle + this.angularVelocity * timeStep;
		if (this.angle >= 360) {
		  this.angle -= 360;
		}
		this.angularVelocity = this.angularVelocity + this.changeAngularVelocity * timeStep; 
    }
}
