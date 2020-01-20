package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.custom.Overridable;

public class OverrideDisable extends OverrideSet {
	public OverrideDisable(Overridable... overridables) {
		super(false, overridables);
	}
}
