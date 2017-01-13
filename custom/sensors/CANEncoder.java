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
	
	public CANEncoder(String name, int id, boolean reverseDirection, double distancePerPulse) {
		super(name, id, 2);
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
		return super.read(0); // TODO: what mode numbers will be position and direction?
	}
	
	/**
	 * Returns the scaled distance rotated by the encoder.
	 */
	@Override
	public double getDistanceSafely() throws InvalidSensorException {
		if (reverseDirection) {
			return distancePerPulse * super.read(0) * -1.0;
		} else {
			return distancePerPulse * super.read(0);
		}
	}
	
	/**
	 * Returns the most recent direction of movement
	 * (based on the speed)
	 */
	@Override
	public boolean getDirectionSafely() throws InvalidSensorException {
		return !reverseDirection == (super.read(1) >= 0);
	}
	
	/**
	 * Returns the rate of rotation
	 */
	@Override
	public double getRateSafely() throws InvalidSensorException {
		if (reverseDirection) {
			return distancePerPulse * super.read(1) * -1.0;
		} else {
			return distancePerPulse * super.read(1);
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
		super.write(new byte[] {0x72, 0x65, 0x73, 0x65, 0x74, 0x65, 0x6e, 0x63}); // resetenc
		super.read(); // and stop transmitting resets once the teensy replies
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
