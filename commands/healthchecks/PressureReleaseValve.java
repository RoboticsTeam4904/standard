package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class PressureReleaseValve extends AbstractHealthcheck {
	private final Compressor compressor;
	private final Solenoid solenoid;
	private boolean valveClosed;
	private byte solenoidsInitialValue;
	
	public PressureReleaseValve(String name, Compressor compressor, Solenoid solenoid) {
		super(name, new InterruptCompressor(compressor));
		this.compressor = compressor;
		this.solenoid = solenoid;
		valveClosed = false;
		solenoidsInitialValue = solenoid.getAll();
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
		if (solenoid.getAll() != solenoidsInitialValue) {
			return HealthStatus.DANGEROUS; // This means the compressor has been running for 15 seconds and we have had no increase in pressure (and no solenoids have been pressed)
		}
		solenoidsInitialValue = solenoid.getAll();
		return HealthStatus.UNCERTAIN;
	}
	
	protected boolean finished() {
		return valveClosed;
	}
}
