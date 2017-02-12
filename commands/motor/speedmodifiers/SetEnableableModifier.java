package org.usfirst.frc4904.standard.commands.motor.speedmodifiers;


import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.EnableableModifier;
import edu.wpi.first.wpilibj.command.Command;

public class SetEnableableModifier extends Command {
	protected final EnableableModifier modifier;
	protected final boolean enable;

	public SetEnableableModifier(EnableableModifier modifier, boolean enable) {
		this.modifier = modifier;
		this.enable = enable;
		requires(modifier);
	}

	@Override
	protected void initialize() {
		modifier.setEnabled(enable);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}