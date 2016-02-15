package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class SensorMotor extends Motor {
	protected final PIDController pid;
	private boolean enablePID;
	protected double position;
	protected long lastUpdate;
	protected final PIDSource sensor;
	
	public SensorMotor(String name, boolean inverted, SpeedModifier slopeController, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		super(name, inverted, slopeController, motors);
		sensor.setPIDSourceType(type);
		pid = new PIDController(0.0, 0.0, 0.0, sensor, this);
		pid.setOutputRange(-1.0, 1.0);
		this.sensor = sensor;
		enablePID = false;
	}
	
	public SensorMotor(String name, boolean isInverted, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, type, motors);
	}
	
	public SensorMotor(String name, SpeedModifier slopeController, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this(name, false, slopeController, sensor, type, motors);
	}
	
	public SensorMotor(String name, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, type, motors);
	}
	
	public SensorMotor(boolean isInverted, SpeedModifier speedModifier, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this("SensorMotor", isInverted, speedModifier, sensor, type, motors);
	}
	
	public SensorMotor(boolean isInverted, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this("SensorMotor", isInverted, sensor, type, motors);
	}
	
	public SensorMotor(SpeedModifier speedModifier, PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this("SensorMotor", speedModifier, sensor, type, motors);
	}
	
	public SensorMotor(PIDSource sensor, PIDSourceType type, SpeedController... motors) {
		this("SensorMotor", sensor, type, motors);
	}
	
	public void reset() {
		pid.reset();
		position = sensor.pidGet();
	}
	
	public void setPID(double P, double I, double D) {
		pid.setPID(P, I, D);
	}
	
	public void setInputRange(double minimum, double maximum) {
		pid.setInputRange(minimum, maximum);
	}
	
	public void enablePID() {
		enablePID = true;
		pid.enable();
	}
	
	public void disablePID() {
		enablePID = false;
		pid.disable();
	}
	
	public void setPosition(double position) {
		pid.setSetpoint(position);
		pid.enable();
		super.set(pid.get());
	}
	
	@Override
	public abstract void set(double speed);
	
	public void write(double speed) {
		LogKitten.v(Double.toString(pid.getError()));
		if (enablePID) {
			super.set(pid.get());
		} else {
			super.set(speed);
		}
	}
	
	@Override
	public void pidWrite(double speed) {
		LogKitten.v(Double.toString(speed));
		super.set(speed);
	}
}
