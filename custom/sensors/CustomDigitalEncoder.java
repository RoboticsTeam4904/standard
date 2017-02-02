package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

/**
 * A RoboRIO encoder that implements
 * the generic encoder class.
 *
 */
public class CustomDigitalEncoder extends Encoder implements CustomEncoder {
	private double distancePerPulse;
	private boolean reverseDirection;

	public CustomDigitalEncoder(DigitalSource aSource, DigitalSource bSource) {
		super(aSource, bSource);
	}

	public CustomDigitalEncoder(DigitalSource aSource, DigitalSource bSource, boolean reverseDirection) {
		super(aSource, bSource, reverseDirection);
	}

	public CustomDigitalEncoder(DigitalSource aSource, DigitalSource bSource, boolean reverseDirection,
		CounterBase.EncodingType encodingType) {
		super(aSource, bSource, reverseDirection, encodingType);
	}

	public CustomDigitalEncoder(DigitalSource aSource, DigitalSource bSource, DigitalSource indexSource) {
		super(aSource, bSource, indexSource);
	}

	public CustomDigitalEncoder(DigitalSource aSource, DigitalSource bSource, DigitalSource indexSource,
		boolean reverseDirection) {
		super(aSource, bSource, indexSource, reverseDirection);
	}

	public CustomDigitalEncoder(int aChannel, int bChannel) {
		super(aChannel, bChannel);
	}

	public CustomDigitalEncoder(int aChannel, int bChannel, boolean reverseDirection) {
		super(aChannel, bChannel, reverseDirection);
	}

	public CustomDigitalEncoder(int aChannel, int bChannel, boolean reverseDirection, CounterBase.EncodingType encodingType) {
		super(aChannel, bChannel, reverseDirection, encodingType);
	}

	public CustomDigitalEncoder(int aChannel, int bChannel, int indexChannel) {
		super(aChannel, bChannel, indexChannel);
	}

	public CustomDigitalEncoder(int aChannel, int bChannel, int indexChannel, boolean reverseDirection) {
		super(aChannel, bChannel, indexChannel, reverseDirection);
	}

	@Override
	public double getDistancePerPulse() {
		return distancePerPulse;
	}

	@Override
	public void setDistancePerPulse(double distancePerPulse) {
		super.setDistancePerPulse(distancePerPulse);
		this.distancePerPulse = distancePerPulse;
	}

	@Override
	public boolean getReverseDirection() {
		return reverseDirection;
	}

	@Override
	public void setReverseDirection(boolean reverseDirection) {
		super.setReverseDirection(reverseDirection);
		this.reverseDirection = reverseDirection;
	}

	@Override
	public double pidGetSafely() {
		return pidGet();
	}

	@Override
	public int getSafely() {
		return get();
	}

	@Override
	public double getDistanceSafely() {
		return getDistance();
	}

	@Override
	public boolean getDirectionSafely() {
		return getDirection();
	}

	@Override
	public boolean getStoppedSafely() {
		return getStopped();
	}

	@Override
	public double getRateSafely() {
		return getRate();
	}
}