package org.usfirst.frc4904.standard.custom.sensors;


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
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}
	
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}
	
	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
	}
	
	public void setReverseDirection(boolean reverseDirection) {
		this.reverseDirection = reverseDirection;
	}
	
	public double pidGet() {
		if (pidSource == PIDSourceType.kDisplacement) {
			return getDistance();
		} else if (pidSource == PIDSourceType.kRate) {
			return getRate();
		}
		return 0.0;
	}
	
	/**
	 * Returns the raw number of ticks
	 */
	public int get() {
		return super.read(0); // TODO: what mode numbers will be position and direction?
	}
	
	/**
	 * Returns the scaled distance rotated by the encoder.
	 */
	public double getDistance() {
		if (reverseDirection) {
			return distancePerPulse * (double) super.read(0) * -1.0;
		} else {
			return distancePerPulse * (double) super.read(0);
		}
	}
	
	/**
	 * Returns the most recent direction of movement
	 * (based on the speed)
	 */
	public boolean getDirection() {
		return !reverseDirection == (super.read(1) >= 0);
	}
	
	/**
	 * Returns the rate of rotation
	 */
	public double getRate() {
		if (reverseDirection) {
			return super.read(1) * -1.0;
		} else {
			return super.read(1);
		}
	}
	
	/**
	 * Returns true when stopped
	 */
	public boolean getStopped() {
		return Math.abs(this.getRate()) <= 0.0001;
	}
	
	/**
	 * Resets the distance traveled for the encoder
	 */
	public void reset() {
		super.write(new byte[] {0x72, 0x65, 0x73, 0x65, 0x74, 0x65, 0x6e, 63}); // resetenc
		super.read();
	}
}
