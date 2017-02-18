package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.commands.Idle;
import edu.wpi.first.wpilibj.command.Subsystem;

public class EnableableModifier extends Subsystem implements SpeedModifier {
	protected boolean enabled;
	protected final SpeedModifier modifier;

	public EnableableModifier(SpeedModifier modifier) {
		this.modifier = modifier;
		enabled = false;
	}

	@Override
	public double modify(double speed) {
		if (enabled) {
			return modifier.modify(speed);
		}
		return speed;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void enable() {
		setEnabled(true);
	}

	public void disable() {
		setEnabled(false);
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Idle(this));
	}
}