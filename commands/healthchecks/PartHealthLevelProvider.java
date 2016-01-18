package org.usfirst.frc4904.standard.commands.healthchecks;

public interface PartHealthLevelProvider {
	public Part getSubject();

	public HealthLevel calculateLevel();
}
