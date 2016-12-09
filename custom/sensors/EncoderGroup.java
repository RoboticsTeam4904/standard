package org.usfirst.frc4904.standard.custom.sensors;


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
	private final double ENCODER_DIFFERENCE_FRACTION = 0.1;

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
	public EncoderGroup(CustomEncoder... encoders) {
		this.encoders = encoders;
		pidSource = PIDSourceType.kDisplacement;
		reverseDirection = false;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}

	@Override
	public double pidGet() {
		try {
			if (pidSource == PIDSourceType.kDisplacement) {
				return getDistance();
			}
			return getRate();
		}
		catch (InvalidSensorException e) {
			throw new RuntimeInvalidSensorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		if (pidSource != null) {
			this.pidSource = pidSource;
		}
	}

	@Override
	public int get() throws InvalidSensorException {
		int average = 0;
		for (CustomEncoder encoder : encoders) {
			if (Math.abs(average - encoder.get()) > encoder.get() * ENCODER_DIFFERENCE_FRACTION) {
				throw new InvalidSensorException("Encoders in group too different");
			}
			average += encoder.get();
		}
		return average / encoders.length;
	}

	@Override
	public double getDistance() throws InvalidSensorException {
		double average = 0.0;
		for (CustomEncoder encoder : encoders) {
			if (Math.abs(average - encoder.getDistance()) > encoder.getDistance() * ENCODER_DIFFERENCE_FRACTION) {
				throw new InvalidSensorException("Encoders in group too different");
			}
			average += encoder.getDistance();
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
		double average = 0.0;
		for (CustomEncoder encoder : encoders) {
			if (Math.abs(average - encoder.getRate()) > encoder.getRate() * ENCODER_DIFFERENCE_FRACTION) {
				throw new InvalidSensorException("Encoders in group too different");
			}
			average += encoder.getRate();
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
	}
}
