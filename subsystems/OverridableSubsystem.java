package org.usfirst.frc4904.standard.subsystems;

import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class OverridableSubsystem extends SubsystemBase implements Overridable {
	private boolean isOverridden = false;

	public OverridableSubsystem(String name) {
		super();
		setName(name);
	}

	public OverridableSubsystem() {
		this("OverridableSubsystem");
	}

	@Override
	public void setOverridden(boolean isOverridden) {
		this.isOverridden = isOverridden;
	}

	@Override
	public boolean isOverridden() {
		return isOverridden;
	}
}
