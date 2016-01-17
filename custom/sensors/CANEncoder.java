package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PIDSourceType;

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
	
	public int get() {
		return super.read(0); // TODO: what mode numbers will be position and direction?
	}
	
	public double getDistance() {
		if (reverseDirection) {
			return distancePerPulse * (double) super.read(0) * -1.0;
		} else {
			return distancePerPulse * (double) super.read(0);
		}
	}
	
	public boolean getDirection() {
		return !reverseDirection == (super.read(1) >= 0);
	}
	
	public double getRate() {
		if (reverseDirection) {
			return super.read(1) * -1.0;
		} else {
			return super.read(1);
		}
	}
}
