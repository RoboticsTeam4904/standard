package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PIDSourceType;

public class CANEncoder extends CANSensor implements CustomEncoder {
	private PIDSourceType pidSource;
	private double rateScale = 1.0;
	private double distanceScale;
	
	public CANEncoder(String name, int id, int modes, boolean reverseDirection) {
		super(name, id, modes);
	}
	
	public CANEncoder(String name, int id, int modes) {
		this(name, id, modes, false);
	}
	
	public CANEncoder(int id, int modes, boolean reverseDirection) {
		this("CANEncoder", id, modes, reverseDirection);
	}
	
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}
	
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}
	
	public double pidGet() {
		if (pidSource == PIDSourceType.kDisplacement) {
			return get();
		} else if (pidSource == PIDSourceType.kRate) {
			return getRate();
		}
		return 0.0;
	}
	
	public int get() {
		return super.read(1); // TODO: what mode numbers will be position and direction?
	}
	
	public boolean getDirection() {
		return super.read(2) >= 0;
	}
	
	public double getRate() {
		return super.read(2);
	}
}
