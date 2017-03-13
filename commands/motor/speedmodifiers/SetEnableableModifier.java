package org.usfirst.frc4904.standard.commands.motor.speedmodifiers;


import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.EnableableModifier;
import edu.wpi.first.wpilibj.command.Command;

public class SetEnableableModifier extends Command {
	protected final EnableableModifier[] modifiers;
	protected final boolean enable;

	public SetEnableableModifier(boolean enable, EnableableModifier... modifiers) {
		this.modifiers = modifiers;
		this.enable = enable;
		for (EnableableModifier modifier : modifiers) {
			requires(modifier);
		}
	}

	@Override
	protected void initialize() {
		for (EnableableModifier modifier : modifiers) {
			modifier.setEnabled(enable);
		}
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
