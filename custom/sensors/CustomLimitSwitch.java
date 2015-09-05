package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

public class CustomLimitSwitch extends Button {
	private final DigitalInput limitSwitch;
	
	public CustomLimitSwitch(DigitalInput limitSwitch) {
		this.limitSwitch = limitSwitch;
	}
	
	public CustomLimitSwitch(int port) {
		this(new DigitalInput(port));
	}
	
	public boolean get() {
		return !limitSwitch.get();
	}
}
