package org.usfirst.frc4904.standard.commands.healthchecks;


import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class PressureReleaseValve extends AbstractHealthcheck {
	private final Compressor compressor;
	private final Solenoid solenoid;
	private boolean valveClosed;
	private byte solenoidsInitialValue;
	private double cylinderFillTime;
	
	/**
	 * Constructor for a PressureRelaseValve check
	 * 
	 * @param name
	 * @param compressor
	 *        The compressor connected to that pressure release valve.
	 * @param solenoid
	 *        Any solenoid on that pressure release valve.
	 */
	public PressureReleaseValve(String name, Compressor compressor, Solenoid solenoid, double cylinderFillTime) {
		super(name, new InterruptCompressor(compressor));
		this.compressor = compressor;
		this.solenoid = solenoid;
		valveClosed = false;
		solenoidsInitialValue = solenoid.getAll();
		this.cylinderFillTime = cylinderFillTime;
	}
	
	protected void initialize() {
		resetTimer();
		setTimeInterval(cylinderFillTime);
	}
	
	protected HealthStatus getStatus() {
		if (compressor.getPressureSwitchValue()) {
			valveClosed = true;
			return HealthStatus.SAFE;
		}
		if (!getTimed()) {
			return HealthStatus.SAFE; // If the compressor is running for less than 15 seconds, we are safe, not uncertain
		}
		if ((byte) solenoid.getAll() == (byte) solenoidsInitialValue) {
			return HealthStatus.DANGEROUS; // This means the compressor has been running for 15 seconds and we have had no decrease in pressure (and no solenoids have been pressed)
		}
		solenoidsInitialValue = solenoid.getAll();
		return HealthStatus.UNCERTAIN;
	}
	
	protected boolean finished() {
		LogKitten.v(Boolean.toString(valveClosed));
		return valveClosed;
	}
}
