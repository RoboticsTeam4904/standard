
// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.subsystems.motor;

public interface BrakeableMotorController extends MotorController {
	// TODO: should we have these? brake() = setBrakeOnNeutral(); neutral()
	// public abstract void setBrakeOnNeutral();
	// public abstract void setCoastOnNeutral();
	// public abstract void setDisableOnNeutral();
	// public abstract void brake();
	// public abstract void coast();
	// public abstract void neutral();

	public abstract void setBrakeMode();

	public abstract void setCoastMode();

	public abstract void neutralOutput();
}
