package org.usfirst.frc4904.standard.commands.safety;

public interface PartHealthLevelProvider {
	public Part getSubject();

	public HealthLevel calculateLevel();
}
