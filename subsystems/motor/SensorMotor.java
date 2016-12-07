package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
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
		motionController.addOutput(this);
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
	
	public void reset() {
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
	
	public void setPosition(double position) {
		motionController.setSetpoint(position);
		motionController.enable();
	}
	
	public boolean onTarget() {
		return motionController.onTarget();
	}
	
	@Override
	public void set(double speed) {
		LogKitten.v(speed + "");
		if (isMotionControlEnabled) {
			motionController.setSetpoint(speed);
		} else {
			super.set(speed);
		}
	}
}
