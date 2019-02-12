package org.usfirst.frc4904.standard.commands.systemchecks;


import org.usfirst.frc4904.standard.commands.systemchecks.StatusMessage.SystemStatus;
import edu.wpi.first.wpilibj.Compressor;

/**
 * System check on compressors
 */
public class CompressorCheck extends SystemCheck {
	protected final Compressor[] compressors;

	/**
	 * @param name
	 *                    name of check
	 * @param timeout
	 *                    duration of check
	 * @param compressors
	 *                    compressors to check
	 */
	public CompressorCheck(String name, double timeout, Compressor... compressors) {
		super(name, timeout, compressors);
		this.compressors = compressors;
	}

	/**
	 * @param name
	 *                    name of check
	 * @param compressors
	 *                    compressors to check
	 */
	public CompressorCheck(String name, Compressor... compressors) {
		this(name, DEFAULT_TIMEOUT, compressors);
	}

	/**
	 * @param timeout
	 *                    duration of check
	 * @param compressors
	 *                    compressors to check
	 */
	public CompressorCheck(double timeout, Compressor... compressors) {
		this("CompressorCheck", timeout, compressors);
	}

	/**
	 * @param compressors
	 *                    compressors to check
	 */
	public CompressorCheck(Compressor... compressors) {
		this("CompressorCheck", compressors);
	}

	public void execute() {
		for (Compressor compressor : compressors) {
			if (compressor.getCompressorNotConnectedFault() || compressor.getCompressorNotConnectedStickyFault()
				|| !compressor.enabled()) {
				updateStatusFail(compressor.getName(), new Exception("COMPRESSOR NOT CONNECTED"));
			} else if (compressor.getCompressorShortedFault() || compressor.getCompressorShortedStickyFault()) {
				updateStatusFail(compressor.getName(), new Exception("COMPRESSOR SHORTED"));
			} else if (compressor.getCompressorCurrentTooHighFault() || compressor.getCompressorCurrentTooHighStickyFault()) {
				updateStatusFail(compressor.getName(), new Exception("CURRENT TOO HIGH"));
			} else if (compressor.getPressureSwitchValue()) { // TODO: Test if this works as intended
				updateStatusFail(compressor.getName(), new Exception("LOW PRESSURE"));
			}
		}
	}
}