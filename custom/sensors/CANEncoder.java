package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Encoder over CAN
 * Implements CustomEncoder generic encoder class
 *
 */
public class CANEncoder extends CANSensor implements CustomEncoder {
	private PIDSourceType pidSource;
	private double distancePerPulse;
	private boolean reverseDirection;
	/**
	 * Sequence of bytes used to reset an encoder
	 * Spells out "resetenc" in ASCII
	 */
	private static final byte[] RESET_ENCODER_BYTE_SEQUENCE = "resetenc".getBytes();

	public CANEncoder(String name, int id, boolean reverseDirection, double distancePerPulse) {
		super(name, id);
		this.reverseDirection = reverseDirection;
		this.distancePerPulse = distancePerPulse;
		setPIDSourceType(PIDSourceType.kDisplacement);
	}

	public CANEncoder(String name, int id, boolean reverseDirection) {
		this(name, id, reverseDirection, 1.0);
	}

	public CANEncoder(String name, int id, double distancePerPulse) {
		this(name, id, false, distancePerPulse);
	}

	public CANEncoder(int id, boolean reverseDirection, double distancePerPulse) {
		this("CANEncoder", id, reverseDirection, distancePerPulse);
	}

	public CANEncoder(String name, int id) {
		this(name, id, false, 1.0);
	}

	public CANEncoder(int id, boolean reverseDirection) {
		this("CANEncoder", id, reverseDirection, 1.0);
	}

	public CANEncoder(int id, double distancePerPulse) {
		this("CANEncoder", id, false, distancePerPulse);
	}

	public CANEncoder(int id) {
		this("CANEncoder", id, false, 1.0);
	}

	/**
	 * Sets PID mode
	 * PIDSourceType is either PIDSourceType.kDisplacement
	 * or PIDSourceType.kRate.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSource;
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
	public double pidGetSafely() throws InvalidSensorException {
		if (pidSource == PIDSourceType.kDisplacement) {
			return getDistance();
		}
		return getRate();
	}

	/**
	 * Returns the raw number of ticks
	 */
	@Override
	public int getSafely() throws InvalidSensorException {
		return super.readSensor()[0];
	}

	/**
	 * Returns the scaled distance rotated by the encoder.
	 */
	@Override
	public double getDistanceSafely() throws InvalidSensorException {
		if (reverseDirection) {
			return distancePerPulse * super.readSensor()[0] * -1.0;
		} else {
			return distancePerPulse * super.readSensor()[0];
		}
	}

	/**
	 * Returns the most recent direction of movement
	 * (based on the speed)
	 */
	@Override
	public boolean getDirectionSafely() throws InvalidSensorException {
		return !reverseDirection == (super.readSensor()[1] >= 0);
	}

	/**
	 * Returns the rate of rotation
	 */
	@Override
	public double getRateSafely() throws InvalidSensorException {
		if (reverseDirection) {
			return distancePerPulse * super.readSensor()[1] * -1.0;
		} else {
			return distancePerPulse * super.readSensor()[1];
		}
	}

	/**
	 * Returns true when stopped
	 */
	@Override
	public boolean getStoppedSafely() throws InvalidSensorException {
		return Util.isZero(getRate());
	}

	/**
	 * Resets the distance traveled for the encoder
	 */
	@Override
	public void reset() {
		super.write(CANEncoder.RESET_ENCODER_BYTE_SEQUENCE); // resetenc
	}

	@Override
	public double pidGet() {
		try {
			return pidGetSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}

	@Override
	public int get() {
		try {
			return getSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}

	@Override
	public double getDistance() {
		try {
			return getDistanceSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}

	@Override
	public boolean getDirection() {
		try {
			return getDirectionSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return false;
		}
	}

	@Override
	public boolean getStopped() {
		try {
			return getStoppedSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return false;
		}
	}

	@Override
	public double getRate() {
		try {
			return getRateSafely();
		}
		catch (Exception e) {
			LogKitten.ex(e);
			return 0;
		}
	}
}
