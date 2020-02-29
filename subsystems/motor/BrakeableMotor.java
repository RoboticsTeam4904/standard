package org.usfirst.frc4904.standard.subsystems.motor;

public interface BrakeableMotor {

	public abstract void setBrakeMode();

	public abstract void setCoastMode();

	public abstract void neutralOutput();
}