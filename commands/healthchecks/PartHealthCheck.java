package org.usfirst.frc4904.standard.commands.healthchecks;


public class PartHealthCheck extends AbstractHealthCheck {
	public final Part part;
	public final PartHealthLevelProvider partHealthLevelProvider;
	
	public PartHealthCheck(Part part) {
		super("Part Health Check for part " + part);
		this.part = part;
		this.partHealthLevelProvider = part.getPartHealthLevelProvider();
		if (partHealthLevelProvider.getSubject() != part) {
			throw new IllegalArgumentException("Part's health level provider's part is not equal to original part. Possibility of severed limbs.");
		}
	}
	
	@Override
	protected HealthLevel getStatus() {
		return partHealthLevelProvider.calculateLevel();
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected boolean finished() {
		return false;
	}
}
