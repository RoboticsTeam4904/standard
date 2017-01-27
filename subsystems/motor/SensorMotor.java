package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class SensorMotor extends Motor {
	protected final MotionController motionController;
	private boolean isMotionControlEnabled;
	
	public SensorMotor(String name, boolean inverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		super(name, inverted, speedModifier, motors);
		this.motionController = motionController;
		isMotionControlEnabled = false;
	}
	
	public SensorMotor(String name, boolean isInverted, MotionController motionController, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), motionController, motors);
	}
	
	public SensorMotor(String name, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this(name, false, speedModifier, motionController, motors);
	}
	
	public SensorMotor(String name, MotionController motionController, SpeedController... motors) {
		this(name, false, new IdentityModifier(), motionController, motors);
	}
	
	public SensorMotor(boolean isInverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("SensorMotor", isInverted, speedModifier, motionController, motors);
	}
	
	public SensorMotor(boolean isInverted, MotionController motionController, SpeedController... motors) {
		this("SensorMotor", isInverted, motionController, motors);
	}
	
	public SensorMotor(SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		this("SensorMotor", speedModifier, motionController, motors);
	}
	
	public SensorMotor(MotionController motionController, SpeedController... motors) {
		this("SensorMotor", motionController, motors);
	}
	
	public void reset() throws InvalidSensorException {
		motionController.reset();
	}
	
	public void setInputRange(double minimum, double maximum) {}
	
	public void enablePID() {
		isMotionControlEnabled = true;
		motionController.enable();
	}
	
	public void disablePID() {
		isMotionControlEnabled = false;
		motionController.disable();
	}
	
	/**
	 * Set the position of a sensor motor
	 *
	 * @param position
	 * @throws InvalidSensorException
	 */
	public void setPositionSafely(double position) throws InvalidSensorException {
		motionController.setSetpoint(position);
		motionController.enable();
		double speed = motionController.getSafely();
		LogKitten.v(getName() + " set to position " + position + " at speed " + speed);
		super.set(speed);
	}
	
	/**
	 * Set the position of a sensor motor
	 *
	 * @param position
	 * @warning this does not indicate sensor failure
	 */
	public void setPosition(double position) {
		motionController.setSetpoint(position);
		motionController.enable();
		double speed = motionController.get();
		LogKitten.v(getName() + " set to position " + position + " at speed " + speed);
		super.set(speed);
	}
	
	public boolean onTarget() {
		return motionController.onTarget();
	}
	
	/**
	 * Set the velocity of a sensor motor
	 *
	 * @param distancePerSecond
	 * @throws InvalidSensorException
	 */
	public void setVelocitySafely(double distancePerSecond) throws InvalidSensorException {
		motionController.setSetpoint(distancePerSecond);
		motionController.enable();
		double speed = motionController.getSafely();
		LogKitten.v(getName() + " set to velocity " + distancePerSecond + " at speed " + speed);
		super.set(speed);
	}
	
	/**
	 * Set the velocity of a sensor motor
	 *
	 * @param distancePerSecond
	 * @warning this does not indicate sensor failure
	 */
	public void setVelocity(double distancePerSecond) {
		motionController.setSetpoint(distancePerSecond);
		motionController.enable();
		double speed = motionController.get();
		LogKitten.v(getName() + " set to velocity " + distancePerSecond + " at speed " + speed);
		super.set(speed);
	}
	
	@Override
	/***
	 * Set the SensorMotor to a desired speed.
	 */
	public void set(double speed) {
		LogKitten.v(speed + "");
		if (isMotionControlEnabled) {
			motionController.setSetpoint(motionController.getInputRange().scaleValue(speed));
			try {
				super.set(motionController.getSafely());
				return;
			}
			catch (InvalidSensorException e) {
				LogKitten.ex(e);
			}
		}
		super.set(speed);
	}
}
