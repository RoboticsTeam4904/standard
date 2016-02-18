package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CANTalonEncoder implements CustomEncoder {
	protected final CANTalon talon;
	protected PIDSourceType pidSource;
	protected double distancePerPulse;
	protected boolean reverseDirection;
	protected boolean rateMode;
	
	public CANTalonEncoder(String name, CANTalon talon, boolean reverseDirection, double distancePerPulse, boolean rateMode) {
		this.talon = talon;
		this.reverseDirection = reverseDirection;
		this.distancePerPulse = distancePerPulse;
		this.rateMode = rateMode;
		setPIDSourceType(PIDSourceType.kDisplacement);
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}
	
	@Override
	public double pidGet() {
		if (rateMode) {
			return getRate();
		}
		return getDistance();
	}
	
	@Override
	public int get() {
		return talon.getEncPosition();
	}
	
	@Override
	public double getDistance() {
		if (reverseDirection) {
			return distancePerPulse * (double) talon.getEncPosition() * -1.0;
		} else {
			return distancePerPulse * (double) talon.getEncPosition();
		}
	}
	
	@Override
	public boolean getDirection() {
		return !reverseDirection == (talon.getEncVelocity() >= 0);
	}
	
	@Override
	public boolean getStopped() {
		return Util.isZero(getRate());
	}
	
	@Override
	public double getRate() {
		if (reverseDirection) {
			return talon.getEncVelocity() * -10.0 * distancePerPulse;
			// getEncVelocity must be converted from ticks to 100ms to ticks per second, so *10.
		} else {
			return talon.getEncVelocity() * 10.0 * distancePerPulse;
		}
	}
	
	@Override
	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
	}
	
	@Override
	public void setReverseDirection(boolean reverseDirection) {
		this.reverseDirection = reverseDirection;
	}
	
	@Override
	public void reset() {
		talon.setEncPosition(0);
	}
}
