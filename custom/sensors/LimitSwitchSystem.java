package org.usfirst.frc4904.cmdbased.custom.sensors;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LimitSwitchSystem extends Subsystem {
	private final DigitalInput rightInnerSwitch;
	private final DigitalInput leftInnerSwitch;
	private final DigitalInput rightOuterSwitch;
	private final DigitalInput leftOuterSwitch;
	
	public LimitSwitchSystem(DigitalInput rightInnerSwitch, DigitalInput leftInnerSwitch, DigitalInput rightOuterSwitch, DigitalInput leftOuterSwitch) {
		super("LimitSwitchSystem");
		this.rightInnerSwitch = rightInnerSwitch;
		this.leftInnerSwitch = leftInnerSwitch;
		this.rightOuterSwitch = rightOuterSwitch;
		this.leftOuterSwitch = leftOuterSwitch;
	}

	protected void initDefaultCommand() {}
	
	public boolean isInnerSwitchPressed() {
		return !rightInnerSwitch.get() || !leftInnerSwitch.get(); // get() returns opposite - true if not pressed
	}
	
	public boolean isOuterSwitchPressed() {
		return !rightOuterSwitch.get() || !leftOuterSwitch.get(); // get() returns opposite - true if not pressed
	}
}
