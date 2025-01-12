package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.usfirst.frc4904.standard.custom.Overridable;

public class OverrideSet extends InstantCommand {

    protected final boolean isOverridden;
    protected final Overridable[] overridables;

    public OverrideSet(boolean isOverridden, Overridable... overridables) {
        this.isOverridden = isOverridden;
        this.overridables = overridables;
    }

    @Override
    public void initialize() {
        for (Overridable overridable : overridables) {
            overridable.setOverridden(isOverridden);
        }
    }
}
