package org.usfirst.frc4904.standard.commands.safety;

public class PartHealthCheck extends HealthCheck {
	public final Part part;
	public final PartHealthLevelProvider partHealthLevelProvider;

	public PartHealthCheck(Part part) {
		super("Part Health Check for part " + part);
		this.part = part;
		this.partHealthLevelProvider = part.getPartHealthLevelProvider();
		if (partHealthLevelProvider.getSubject() != part) {
			throw new IllegalArgumentException(
					"Part's health level provider's part is not equal to original part. Possibility of severed limbs.");
		}
	}

	@Override
	protected HealthLevel calculateHealthStatus() {
		return partHealthLevelProvider.calculateLevel();
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
