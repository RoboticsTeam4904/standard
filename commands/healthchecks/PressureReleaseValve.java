package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.Compressor;

public class PressureReleaseValve extends AbstractHealthcheck {
	private final Compressor compressor;
	private boolean valveClosed;
	
	public PressureReleaseValve(String name, Compressor compressor) {
		super(name, new InterruptCompressor(compressor));
		this.compressor = compressor;
		valveClosed = false;
	}
	
	protected void initialize() {
		resetTimer();
		setTimeInterval(15.0);
	}
	
	protected HealthStatus getStatus() {
		if (!compressor.getPressureSwitchValue()) {
			valveClosed = true;
			return HealthStatus.SAFE;
		}
		if (!getTimed()) {
			return HealthStatus.SAFE; // If the compressor is running for less than 15 seconds, we are safe, not uncertain
		}
		return HealthStatus.DANGEROUS; // This means the compressor has been running for 15 seconds and we have had no increase in pressure
	}
	
	protected boolean finished() {
		return valveClosed;
	}
}
