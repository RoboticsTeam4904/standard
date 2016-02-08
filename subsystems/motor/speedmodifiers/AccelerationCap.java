package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.custom.sensors.PDP;

public class AccelerationCap implements SpeedModifier {
	private double currentSpeed;
	private long lastUpdate;
	private final PDP pdp;
	private final double softStopVoltage;
	private final double hardStopVoltage;
	
	public AccelerationCap(PDP pdp, double softStopVoltage, double hardStopVoltage) {
		this.pdp = pdp;
		this.softStopVoltage = softStopVoltage;
		this.hardStopVoltage = hardStopVoltage;
	}
	
	public AccelerationCap(PDP pdp) {
		this(pdp, 11.0, 10.0);
	}
	
	public double modify(double inputSpeed) {
		double outputSpeed;
		if (Math.abs(inputSpeed) > Math.abs(currentSpeed) && pdp.getVoltage() < softStopVoltage) {
			if (pdp.getVoltage() < hardStopVoltage) {
				outputSpeed = currentSpeed - 0.3 * currentSpeed;
			} else {
				outputSpeed = currentSpeed;
			}
		} else if (Math.abs(inputSpeed) > Math.abs(currentSpeed)) {
			outputSpeed = currentSpeed + ((double) (System.currentTimeMillis() - lastUpdate) / (double) 64) * (inputSpeed - currentSpeed);
			lastUpdate = System.currentTimeMillis();
		} else {
			outputSpeed = inputSpeed;
		}
		return outputSpeed;
	}
}
