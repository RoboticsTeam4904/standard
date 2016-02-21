package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.PDP;

/**
 * A SpeedModifier that does brownout protection and voltage ramping.
 * This is designed to reduce power consumption (via voltage ramping)
 * and prevent RoboRIO/router brownouts.
 */
public class AccelerationCap implements SpeedModifier {
	protected double currentSpeed;
	protected long lastUpdate; // in milliseconds
	protected final PDP pdp;
	protected final double softStopVoltage;
	protected final double hardStopVoltage;
	
	/**
	 * A SpeedModifier that does brownout protection and voltage ramping.
	 * This is designed to reduce power consumption (via voltage ramping)
	 * and prevent RoboRIO/router brownouts.
	 *
	 * @param pdp
	 *        The robot's power distribution panel.
	 *        This is used to monitor the battery voltage.
	 * @param softStopVoltage
	 *        Voltage to stop increasing motor speed at.
	 * @param hardStopVoltage
	 *        Voltage to begin decreasing motor speed at.
	 */
	public AccelerationCap(PDP pdp, double softStopVoltage, double hardStopVoltage) {
		this.pdp = pdp;
		this.softStopVoltage = softStopVoltage;
		this.hardStopVoltage = hardStopVoltage;
		lastUpdate = System.currentTimeMillis();
	}
	
	/**
	 * A SpeedModifier that does brownout protection and voltage ramping.
	 * This is designed to reduce power consumption (via voltage ramping)
	 * and prevent RoboRIO/router brownouts.
	 *
	 * Default soft stop voltage is 11 volts.
	 * Default hard stop voltage is 10 volts.
	 *
	 * @param pdp
	 *        The robot's power distribution panel.
	 *        This is used to monitor the battery voltage.
	 */
	public AccelerationCap(PDP pdp) {
		this(pdp, 11.0, 10.0);
	}
	
	/**
	 * Modify the input speed and get the new output. AccelerationCap does voltage ramping,
	 * which means that motor speed changes take place over 1/16th of a second rather than
	 * instantly. This decreases power consumption, but minimally affects performance.
	 *
	 * AccelerationCap also prevents brownouts by slowing motors as voltage decreases.
	 */
	@Override
	public double modify(double inputSpeed) {
		double outputSpeed;
		if (Math.abs(inputSpeed) > Math.abs(currentSpeed) && pdp.getVoltage() < softStopVoltage) {
			LogKitten.d("AccelerationCap brownout protecting");
			if (pdp.getVoltage() < hardStopVoltage) {
				outputSpeed = currentSpeed - 0.3 * currentSpeed;
			} else {
				outputSpeed = currentSpeed;
			}
			LogKitten.d("AccelerationCap input: " + Double.toString(inputSpeed) + "\t" + "AccelerationCap output: " + Double.toString(outputSpeed));
		} else if (Math.abs(inputSpeed) > Math.abs(currentSpeed)) {
			LogKitten.d("AccelerationCap voltage ramping");
			outputSpeed = currentSpeed + ((double) (System.currentTimeMillis() - lastUpdate) / 64) * (inputSpeed - currentSpeed);
			if (outputSpeed > inputSpeed) {
				outputSpeed = inputSpeed;
			}
		} else {
			outputSpeed = inputSpeed;
		}
		LogKitten.d("AccelerationCap input: " + Double.toString(inputSpeed) + "\t" + "AccelerationCap output: " + Double.toString(outputSpeed));
		lastUpdate = System.currentTimeMillis();
		currentSpeed = outputSpeed;
		return outputSpeed;
	}
}
