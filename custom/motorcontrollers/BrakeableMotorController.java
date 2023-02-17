
// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.motorcontrollers;

public interface BrakeableMotorController extends MotorController {
	public abstract BrakeableMotorController setBrakeOnNeutral();
	public abstract BrakeableMotorController setCoastOnNeutral();
	public abstract void neutralOutput();
}
