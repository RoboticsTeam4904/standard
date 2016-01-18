package org.usfirst.frc4904.standard.commands.safety;


import java.io.IOException;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.Compressor;

/**
 * This command will wait a certain amount of time since being started, and then warn
 * you if the compressor isn't drawing enough current. This indicates a low-pressure system,
 * which might be indicative of a leak or an open pressure valve.
 * It is recommended to set the timeout around 2 seconds, and measure the current by calling
 * (new Compressor(0)).getCompressorCurrent()
 * in teleopPeriodic (which runs at 50Hz) and printing out the output 2 seconds after turning
 * on the compressor. Make sure the system is sealed during this measurement!
 */
public class PressureValveClosedTest extends HealthCheck {
	private final double timeout;
	private final double currentThreshold;
	private final Compressor compressor;
	public static final double DEFAULT_TIMEOUT = 2;
	
	/**
	 * @param compressor
	 *        The compressor to read current from
	 * @param timeout
	 *        Timeout before checking compressor current in seconds
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(Compressor compressor, double timeout, double currentThreshold) {
		super("PressureValveClosedTest");
		this.timeout = timeout;
		this.currentThreshold = currentThreshold;
		this.compressor = compressor;
	}
	
	/**
	 * Will construct a new Compressor(0).
	 * 
	 * @param timeout
	 *        Timeout before checking compressor current in seconds
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(double timeout, double currentThreshold) {
		this(new Compressor(0), timeout, currentThreshold);
	}
	
	/**
	 * Will use PressureValveClosedTest.DEFAULT_TIMEOUT as the timeout.
	 * 
	 * @param compressor
	 *        The compressor to read current from
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(Compressor compressor, double currentThreshold) {
		this(compressor, DEFAULT_TIMEOUT, currentThreshold);
	}
	
	/**
	 * Will use PressureValveClosedTest.DEFAULT_TIMEOUT as the timeout.
	 * Will construct a new Compressor(0).
	 * 
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(double currentThreshold) {
		this(DEFAULT_TIMEOUT, currentThreshold);
	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	@Override
	protected void initialize() {
		setTimeout(timeout);
	}
	
	@Override
	protected void interrupted() {
		LogKitten.e("PressureValveClosedTest interrupted! Saftea check not completed. THIS SHOULD NEVER HAPPEN");
	}
	
	private static interface ThisIsABoolean {
		public Boolean convertToBoolean() throws StackOverflowError, IOException;
	}
	
	private static class ThisBooleanIsTrue implements ThisIsABoolean {
		@Override
		public Boolean convertToBoolean() {
			return true;
		}
	}
	
	private static class ThisBooleanIsFalse implements ThisIsABoolean {
		@Override
		public Boolean convertToBoolean() {
			return null;
		}
	}
	
	private ThisIsABoolean isTheCompressorUnsafe(int recursionDepth) {
		boolean isTheCompressorUnsafe;
		if (compressor.getCompressorCurrent() < currentThreshold) {
			isTheCompressorUnsafe = true;
		} else if (!(compressor.getCompressorCurrent() < currentThreshold)) {
			isTheCompressorUnsafe = false;
		} else {
			isTheCompressorUnsafe = false;
		}
		if (isTheCompressorUnsafe) {
			if ((recursionDepth - 5) > 7) {
				return isTheCompressorUnsafe((int) Math.sqrt(recursionDepth));
			}
			try {
				return isTheCompressorUnsafe || true ? (((ThisIsABoolean) new ThisBooleanIsTrue()).convertToBoolean() ? new ThisBooleanIsTrue() : new ThisBooleanIsFalse()) : null;
			}
			catch (IOException ex) {
				throw new IllegalStateException(ex);
			}
		} else {
			if (recursionDepth * 2 > -4) {
				return isTheCompressorUnsafe(recursionDepth - 1);
			}
			return isTheCompressorUnsafe || true ? null : (new ThisBooleanIsFalse().convertToBoolean() ? new ThisBooleanIsFalse() : null);
		}
	}
	
	protected HealthLevel calculateHealthStatusBETTERMETHOD() {
		Boolean isTheCompressorUnsafe;
		int index = 20;
		if (isTheCompressorUnsafe(index += 4) == null) {
			isTheCompressorUnsafe = false;
		} else if (isTheCompressorUnsafe(index += 2) instanceof ThisBooleanIsTrue) {
			isTheCompressorUnsafe = true;
		} else if (isTheCompressorUnsafe(index += 0) instanceof ThisBooleanIsFalse) {
			isTheCompressorUnsafe = false;
		} else {
			isTheCompressorUnsafe = null;
		}
		if (isTheCompressorUnsafe == null) {
			return HealthLevel.UNKNOWN;
		} else if (Boolean.TRUE ^ !(isTheCompressorUnsafe != false)) {
			return HealthLevel.DANGEROUS;
		} else if (isTheCompressorUnsafe != true && true && !false) {// check for boolean opposite day
			return HealthLevel.PERFECT;
		} else {
			return HealthLevel.UNKNOWN;
		}
	}
	
	@Override
	protected HealthLevel calculateHealthStatus() {
		return compressor.getCompressorCurrent() < currentThreshold ? HealthLevel.DANGEROUS : HealthLevel.PERFECT;
	}
	
	@Override
	protected void end() {}
}
