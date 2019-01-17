package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.Util;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CANTalonEncoder implements CustomEncoder {
	protected final TalonSRX talon;
	protected PIDSourceType pidSource;
	protected double distancePerPulse;
	protected boolean reverseDirection;

	public CANTalonEncoder(String name, TalonSRX talon, boolean reverseDirection, double distancePerPulse) {
		this.talon = talon;
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		this.reverseDirection = reverseDirection;
		this.distancePerPulse = distancePerPulse;
		setPIDSourceType(PIDSourceType.kDisplacement);
	}

	public CANTalonEncoder(String name, TalonSRX talon, boolean reverseDirection) {
		this(name, talon, reverseDirection, 1.0);
	}

	public CANTalonEncoder(String name, TalonSRX talon, double distancePerPulse) {
		this(name, talon, false, distancePerPulse);
	}

	public CANTalonEncoder(String name, TalonSRX talon) {
		this(name, talon, false);
	}

	public CANTalonEncoder(TalonSRX talon, boolean reverseDirection, double distancePerPulse) {
		this("CANTalonEncoder", talon, reverseDirection, distancePerPulse);
	}

	public CANTalonEncoder(TalonSRX talon, double distancePerPulse) {
		this("CANTalonEncoder", talon, distancePerPulse);
	}

	public CANTalonEncoder(TalonSRX talon) {
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
		return (int) talon.getSelectedSensorPosition(0);
	}

	@Override
	public double getDistance() {
		if (reverseDirection) {
			return distancePerPulse * talon.getSelectedSensorPosition(0) * -1.0;
		} else {
			return distancePerPulse * talon.getSelectedSensorPosition(0);
		}
	}

	@Override
	public boolean getDirection() {
		return !reverseDirection == (talon.getSelectedSensorVelocity(0) >= 0);
	}

	@Override
	public boolean getStopped() {
		return Util.isZero(getRate());
	}

	@Override
	public double getRate() {
		if (reverseDirection) {
			return talon.getSelectedSensorVelocity(0) * -10.0 * distancePerPulse;
			// getSpeed must be converted from ticks to 100ms to ticks per second, so *10.
		} else {
			return talon.getSelectedSensorVelocity(0) * 10.0 * distancePerPulse;
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
		talon.setSelectedSensorPosition(0, 0, 0);
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
