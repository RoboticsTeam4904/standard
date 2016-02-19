package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A limit switch that extends the generic
 * button class.
 *
 */
public class CustomDigitalLimitSwitch extends Button implements CustomButton {
	protected final DigitalInput limitSwitch;
	
	public CustomDigitalLimitSwitch(DigitalInput limitSwitch) {
		this.limitSwitch = limitSwitch;
	}
	
	public CustomDigitalLimitSwitch(int port) {
		this(new DigitalInput(port));
	}
	
	/**
	 * Returns true when the limit switch is pressed.
	 * This is based on the raw value of the limit
	 * switch, not only once when it is first pressed.
	 */
	@Override
	public boolean get() {
		return !limitSwitch.get();
	}
}
