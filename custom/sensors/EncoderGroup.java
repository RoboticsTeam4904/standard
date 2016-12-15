package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Amalgamates the data of several encoders for the purpose
 * of controlling a single motion controller.
 *
 * @warning The amalgamation will be the average. Verify
 *          before using this class that all the encoders will be rotating
 *          in the same direction with the same rate (before setDistancePerTick).
 */
public class EncoderGroup implements CustomEncoder {
	private final CustomEncoder[] encoders;
	private PIDSourceType pidSource;
	private boolean reverseDirection;
	private double distancePerPulse;
	private final double tolerance;
	private int errorCount;
	private final int MAX_ENCODER_ERRORS = 20;
	
	/**
	 * Amalgamates the data of several encoders for the purpose
	 * of controlling a single motion controller.
	 *
	 * @warning The amalgamation will be the average. Verify
	 *          before using this class that all the encoders will be rotating
	 *          in the same direction with the same rate (before setDistancePerTick).
	 *
	 * @param encoders
	 *        The encoders to amalgamate.
	 */
	public EncoderGroup(double tolerance, CustomEncoder... encoders) {
		this.encoders = encoders;
		this.tolerance = tolerance;
		pidSource = PIDSourceType.kDisplacement;
		reverseDirection = false;
		errorCount = 0;
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}
	
	@Override
	public double pidGet() throws InvalidSensorException {
		if (pidSource == PIDSourceType.kDisplacement) {
			return getDistance();
		}
		return getRate();
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		if (pidSource != null) {
			this.pidSource = pidSource;
		}
	}
	
	@Override
	public int get() throws InvalidSensorException {
		int average = encoders[0].get();
		for (int i = 1; i < encoders.length; i++) {
			if (new Util.Range(average - tolerance, average + tolerance).contains(encoders[i].get())) {
				errorCount++;
				if (errorCount > MAX_ENCODER_ERRORS) {
					errorCount = 0;
					LogKitten.e("Encoders in group too different: " + average + " " + encoders[i].get());
					throw new InvalidSensorException("Encoders in group too different: " + average + " " + encoders[i].get());
				}
			}
			errorCount = 0;
			average = (average * i + encoders[i].get()) / (i + 1);
		}
		return average / encoders.length;
	}
	
	@Override
	public double getDistance() throws InvalidSensorException {
		double average = encoders[0].getDistance();
		for (int i = 1; i < encoders.length; i++) {
			if (new Util.Range(average - tolerance, average + tolerance).contains(encoders[i].getDistance())) {
				errorCount++;
				if (errorCount > MAX_ENCODER_ERRORS) {
					errorCount = 0;
					LogKitten.e("Encoders in group too different: " + average + " " + encoders[i].getDistance());
					throw new InvalidSensorException("Encoders in group too different: " + average + " " + encoders[i].getDistance());
				}
			}
			errorCount = 0;
			average = (average * i + encoders[i].getDistance()) / (i + 1);
		}
		return average / encoders.length;
	}
	
	@Override
	public boolean getDirection() throws InvalidSensorException {
		return (getRate() > 0);
	}
	
	@Override
	public boolean getStopped() throws InvalidSensorException {
		return Util.isZero(getRate());
	}
	
	@Override
	public double getRate() throws InvalidSensorException {
		double average = encoders[0].getRate();
		for (int i = 1; i < encoders.length; i++) {
			if (new Util.Range(average - tolerance, average + tolerance).contains(encoders[i].getRate())) {
				errorCount++;
				if (errorCount > MAX_ENCODER_ERRORS) {
					errorCount = 0;
					LogKitten.e("Encoders in group too different: " + average + " " + encoders[i].getRate());
					throw new InvalidSensorException("Encoders in group too different: " + average + " " + encoders[i].getRate());
				}
			}
			errorCount = 0;
			average = (average * i + encoders[i].getRate()) / (i + 1);
		}
		return average / encoders.length;
	}
	
	/**
	 * Get whether this entire encoder is inverted.
	 *
	 * @return isInverted
	 *         The state of inversion, true is inverted.
	 */
	@Override
	public boolean getReverseDirection() {
		return reverseDirection;
	}
	
	/**
	 * Sets the direction inversion of all encoder substituents.
	 * This respects the original inversion state of each encoder when constructed,
	 * and will only invert encoders if this.getReverseDirection() != the input.
	 *
	 * @param reverseDirection
	 *        The state of inversion, true is inverted.
	 */
	@Override
	public void setReverseDirection(boolean reverseDirection) {
		if (getReverseDirection() != reverseDirection) {
			for (CustomEncoder encoder : encoders) {
				encoder.setReverseDirection(!encoder.getReverseDirection());
			}
		}
		this.reverseDirection = reverseDirection;
	}
	
	@Override
	public double getDistancePerPulse() {
		return distancePerPulse;
	}
	
	@Override
	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
		for (CustomEncoder encoder : encoders) {
			encoder.setDistancePerPulse(distancePerPulse);
		}
	}
	
	@Override
	public void reset() {
		for (CustomEncoder encoder : encoders) {
			encoder.reset();
		}
		errorCount = 0;
	}
}
