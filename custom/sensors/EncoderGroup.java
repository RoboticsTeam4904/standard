package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderGroup implements CustomEncoder {
	private final CustomEncoder[] encoders;
	private PIDSourceType pidSource;

	public EncoderGroup(CustomEncoder... encoders) {
		this.encoders = encoders;
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
		this.pidSource = pidSource;
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
	
	@Override
	public void setDistancePerPulse(double distancePerPulse) {
		for (CustomEncoder encoder : encoders) {
			encoder.setDistancePerPulse(distancePerPulse);
		}
	}
	
	@Override
	public void setReverseDirection(boolean reverseDirection) {
		for (CustomEncoder encoder : encoders) {
			encoder.setReverseDirection(reverseDirection);
		}
	}
	
	@Override
	public void reset() {
		for (CustomEncoder encoder : encoders) {
			encoder.reset();
		}
	}
}
