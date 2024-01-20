package org.usfirst.frc4904.standard.commands.motor.speedmodifiers;

import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.EnableableModifier;
import edu.wpi.first.wpilibj2.command.Command;

public class SetEnableableModifier extends Command {
	protected final EnableableModifier[] modifiers;
	protected final boolean enable;

	public SetEnableableModifier(boolean enable, EnableableModifier... modifiers) {
		super();
		this.modifiers = modifiers;
		this.enable = enable;
		addRequirements(modifiers);
	}

	@Override
	public void initialize() {
		for (EnableableModifier modifier : modifiers) {
			modifier.setEnabled(enable);
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
