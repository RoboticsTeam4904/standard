package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.Util;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CANTalonEncoder implements CustomEncoder {
	protected final CANTalon talon;
	protected PIDSourceType pidSource;
	protected double distancePerPulse;
	protected boolean reverseDirection;

	public CANTalonEncoder(String name, CANTalon talon, boolean reverseDirection, double distancePerPulse) {
		this.talon = talon;
		this.reverseDirection = reverseDirection;
		this.distancePerPulse = distancePerPulse;
		setPIDSourceType(PIDSourceType.kDisplacement);
	}

	public CANTalonEncoder(String name, CANTalon talon, boolean reverseDirection) {
		this(name, talon, reverseDirection, 1.0);
	}
	
	public CANTalonEncoder(String name, CANTalon talon, double distancePerPulse) {
		this(name, talon, false, distancePerPulse);
	}
	
	public CANTalonEncoder(String name, CANTalon talon) {
		this(name, talon, false);
	}
	
	public CANTalonEncoder(CANTalon talon, boolean reverseDirection, double distancePerPulse) {
		this("CANTalonEncoder", talon, reverseDirection, distancePerPulse);
	}
	
	public CANTalonEncoder(CANTalon talon, double distancePerPulse) {
		this("CANTalonEncoder", talon, distancePerPulse);
	}
	
	public CANTalonEncoder(CANTalon talon) {
		this("CANTalonEncoder", talon);
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
		if (pidSource == PIDSourceType.kDisplacement) {
			return getDistance();
		}
		return getRate();
	}
	
	@Override
	public int get() {
		return talon.getEncPosition();
	}
	
	@Override
	public double getDistance() {
		if (reverseDirection) {
			return distancePerPulse * talon.getEncPosition() * -1.0;
		} else {
			return distancePerPulse * talon.getEncPosition();
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
	public double getDistancePerPulse() {
		return distancePerPulse;
	}
	
	@Override
	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
	}
	
	@Override
	public boolean getReverseDirection() {
		return reverseDirection;
	}
	
	@Override
	public void setReverseDirection(boolean reverseDirection) {
		this.reverseDirection = reverseDirection;
	}
	
	@Override
	public void reset() {
		talon.setEncPosition(0);
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
