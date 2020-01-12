package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.custom.Overridable;

public class OverrideEnable extends OverrideSet {
	public OverrideEnable(Overridable... overridables) {
		super(true, overridables);
	}
}
