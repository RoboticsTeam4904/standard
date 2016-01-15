package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

public class CustomDigitalLimitSwitch extends Button implements CustomButton {
	private final DigitalInput limitSwitch;
	
	public CustomDigitalLimitSwitch(DigitalInput limitSwitch) {
		this.limitSwitch = limitSwitch;
	}
	
	public CustomDigitalLimitSwitch(int port) {
		this(new DigitalInput(port));
	}
	
	public boolean get() {
		return !limitSwitch.get();
	}
}
