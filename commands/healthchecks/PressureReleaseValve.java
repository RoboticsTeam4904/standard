package org.usfirst.frc4904.standard.commands.healthchecks;


import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class PressureReleaseValve extends AbstractHealthCheck {
	private final Compressor compressor;
	private final Solenoid solenoid;
	private boolean valveClosed;
	private byte solenoidsLastValue;
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
		solenoidsLastValue = solenoid.getAll();
		this.cylinderFillTime = cylinderFillTime;
	}
	
	@Override
	protected void initialize() {
		resetTimer();
		setTimeInterval(cylinderFillTime);
	}
	
	@Override
	protected HealthLevel getStatus() {
		if (solenoid.getAll() != solenoidsLastValue) {
			solenoidsLastValue = solenoid.getAll();
			resetTimer();
			return HealthLevel.UNKNOWN;
		}
		if (!compressor.enabled()) {
			resetTimer(); /// If the compressor is not running, do not try to time it.
		}
		if (valveClosed == true) {
			return HealthLevel.SAFE;
		}
		if (compressor.getPressureSwitchValue()) {
			valveClosed = true;
			return HealthLevel.SAFE;
		}
		if (!getTimed()) {
			return HealthLevel.SAFE; // If the compressor is running for less than 15 seconds, we are safe, not uncertain
		}
		return HealthLevel.DANGEROUS;
	}
	
	@Override
	protected boolean finished() {
		LogKitten.v(Boolean.toString(valveClosed));
		return valveClosed;
	}
}
