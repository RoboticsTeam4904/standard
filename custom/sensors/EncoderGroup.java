package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderGroup implements CustomEncoder {
	private final CustomEncoder[] encoders;
	private PIDSourceType pidSource;
	private boolean reverseDirection;
	private double distancePerPulse;
	
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
	public int get() {
		int average = 0;
		for (CustomEncoder encoder : encoders) {
			average += encoder.get();
		}
		return average / encoders.length;
	}
	
	@Override
	public double getDistance() {
		double average = 0.0;
		for (CustomEncoder encoder : encoders) {
			average += encoder.getDistance();
		}
		return average / encoders.length;
	}
	
	@Override
	public boolean getDirection() {
		return (getRate() > 0);
	}
	
	@Override
	public boolean getStopped() {
		return Util.isZero(getRate());
	}
	
	@Override
	public double getRate() {
		double average = 0.0;
		for (CustomEncoder encoder : encoders) {
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
