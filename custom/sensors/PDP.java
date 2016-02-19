package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Local version of the PowerDistributionPanel class
 *
 */
public class PDP extends PowerDistributionPanel {
	public PDP() {}
	
	public void addLiveWindow() {
		LiveWindow.addSensor("PDP", "PDP", this);
	}
}
