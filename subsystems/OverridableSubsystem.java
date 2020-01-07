package org.usfirst.frc4904.standard.subsystems;


import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class OverridableSubsystem implements Subsystem, Overridable {
	private boolean isOverridden = false;

	public void setOverridden(boolean isOverridden) {
		this.isOverridden = isOverridden;
	}

	public boolean isOverridden() {
		return isOverridden;
	}
}
