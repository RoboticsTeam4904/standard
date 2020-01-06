package org.usfirst.frc4904.standard.commands.motor.speedmodifiers;


import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.EnableableModifier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.HashSet;
import java.util.Set;

public class SetEnableableModifier implements Command {
	protected final EnableableModifier[] modifiers;
	protected final boolean enable;


	public SetEnableableModifier(boolean enable, EnableableModifier... modifiers) {
		this.modifiers = modifiers;
		this.enable = enable;
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

	@Override
	public Set<Subsystem> getRequirements() {
		Set<Subsystem> requirements = new HashSet<Subsystem>();
		for (EnableableModifier modifier : modifiers) {
			requirements.add(modifier);
		}
		// TODO Auto-generated method stub
		return requirements;
	}
}
