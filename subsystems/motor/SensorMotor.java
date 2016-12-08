package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class SensorMotor extends Motor {
	protected final MotionController motionController;
	
	public SensorMotor(String name, boolean inverted, SpeedModifier speedModifier, MotionController motionController, SpeedController... motors) {
		super(name, inverted, speedModifier, motors);
		this.motionController = motionController;
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
	
	public void setInputRange(double minimum, double maximum) {
		motionController.setInputRange(minimum, maximum);
	}
	
	public void enableMC() {
		motionController.setOutput(this);
		motionController.enable();
	}
	
	public void disableMC() {
		motionController.disable();
	}
	
	public boolean onTarget() {
		return motionController.onTarget();
	}
}
