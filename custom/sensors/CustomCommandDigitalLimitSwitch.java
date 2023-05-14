package org.usfirst.frc4904.standard.custom.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// TO DO untested

/** 
 * Digital limit switch that provides the same Trigger interface as HID
 * controller buttons. Updated 2023.
 */
public class CustomCommandDigitalLimitSwitch extends Trigger {
	public CustomCommandDigitalLimitSwitch(DigitalInput limitSwitch) {
        super(() -> limitSwitch.get());
	}

	public CustomCommandDigitalLimitSwitch(int port) {
		this(new DigitalInput(port));
	}
}
