package org.usfirst.frc4904.standard.subsystems;


import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class OverridableSubsystem extends SubsystemBase implements Overridable {
	private boolean isOverridden = false;

	public OverridableSubsystem(String name) {
		setName(name);
	}

	public void setOverridden(boolean isOverridden) {
		this.isOverridden = isOverridden;
	}

	public boolean isOverridden() {
		return isOverridden;
	}
}
