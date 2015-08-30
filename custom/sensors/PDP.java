package org.usfirst.frc4904.cmdbased.custom.sensors;


import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class PDP extends PowerDistributionPanel {
	public PDP() {}
	
	public void addLiveWindow() {
		LiveWindow.addSensor("PDP", "PDP", this);
	}
}
