package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.CustomPID;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class SensorMotor extends Motor {
	protected final CustomPID pid;
	protected final boolean isInRateMode;
	private boolean isPIDEnabled;
	protected double position;
	protected long lastUpdate;
	protected final PIDSource sensor;
	
	public SensorMotor(String name, boolean inverted, SpeedModifier slopeController, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		super(name, inverted, slopeController, motors);
		sensor.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new CustomPID(0.0, 0.0, 0.0, sensor);
		this.sensor = sensor;
		isPIDEnabled = false;
		this.isInRateMode = isInRateMode;
	}
	
	public SensorMotor(String name, boolean isInverted, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, isInRateMode, motors);
	}
	
	public SensorMotor(String name, SpeedModifier slopeController, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this(name, false, slopeController, sensor, isInRateMode, motors);
	}
	
	public SensorMotor(String name, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, isInRateMode, motors);
	}
	
	public SensorMotor(boolean isInverted, SpeedModifier speedModifier, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this("SensorMotor", isInverted, speedModifier, sensor, isInRateMode, motors);
	}
	
	public SensorMotor(boolean isInverted, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this("SensorMotor", isInverted, sensor, isInRateMode, motors);
	}
	
	public SensorMotor(SpeedModifier speedModifier, PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this("SensorMotor", speedModifier, sensor, isInRateMode, motors);
	}
	
	public SensorMotor(PIDSource sensor, boolean isInRateMode, SpeedController... motors) {
		this("SensorMotor", sensor, isInRateMode, motors);
	}
	
	public void reset() {
		pid.reset();
		position = sensor.pidGet();
	}
	
	public void setPID(double P, double I, double D) {
		pid.setPID(P, I, D);
	}
	
	public void setPIDF(double P, double I, double D, double F) {
		pid.setPIDF(P, I, D, F);
		LogKitten.d("P:" + P + "I:" + I + "D:" + D + "F:" + F);
	}
	
	public void setInputRange(double minimum, double maximum) {}
	
	public void enablePID() {
		isPIDEnabled = true;
		pid.enable();
	}
	
	public void disablePID() {
		isPIDEnabled = false;
		pid.disable();
	}
	
	public void setPosition(double position) {
		LogKitten.v(getName() + " set to position " + position);
		pid.setSetpoint(position);
		pid.enable();
		super.set(pid.get());
	}
	
	@Override
	public abstract void set(double speed);
	
	public void write(double speed) {
		if (isPIDEnabled) {
			if (isInRateMode) {
				LogKitten.v("Error: " + pid.getError());
				super.set(pid.get());
			} else {
				super.set(pid.get());
			}
		} else {
			super.set(speed);
		}
	}
}
