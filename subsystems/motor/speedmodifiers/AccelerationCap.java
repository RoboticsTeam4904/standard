package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A SpeedModifier that does brownout protection and voltage ramping.
 * This is designed to reduce power consumption (via voltage ramping)
 * and prevent RoboRIO/router brownouts.
 */
public class AccelerationCap implements SpeedModifier {
	public final static double MINIMUM_OPERATING_VOLTAGE = 7.0;
	public final static double MAXIMUM_MOTOR_INCREASE_PER_SECOND = 1.6;
	public final static double MAXIMUM_MOTOR_DECREASE_PER_SECOND = 6.4;
	public final static double ANTI_BROWNOUT_BACKOFF = 0.25; // How much to throttle a motor down to avoid brownout
	public final static double DEFAULT_HARD_STOP_VOLTAGE = 9.0;
	// Motor constants
	public final static double CIM_AMPS_PER_PERCENT = 12.83;
	public final static double CIM_BASE_AMPS = 2.7;
	public final static double PRO_775_AMPS_PER_PERCENT = 13.33;
	public final static double PRO_775_BASE_AMPS = 0.7;
	public final static double BAG_AMPS_PER_PERCENT = 5.12;
	public final static double BAG_BASE_AMPS = 1.8;
	protected long lastUpdate; // in milliseconds
	protected final PDP pdp;
	protected SpeedController motor;
	protected final double hardStopVoltage;
	protected final double motorAmpsPerPercent;
	protected final double motorBaseAmperage;
	protected double outputSpeed;

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
	public AccelerationCap(PDP pdp, double hardStopVoltage, double motorAmpsPerPercent, double motorBaseAmperage) {
		this.pdp = pdp;
		this.hardStopVoltage = hardStopVoltage;
		this.motorAmpsPerPercent = motorAmpsPerPercent;
		this.motorBaseAmperage = motorBaseAmperage;
		outputSpeed = 0;
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * A SpeedModifier that does brownout protection and voltage ramping.
	 * This is designed to reduce power consumption (via voltage ramping)
	 * and prevent RoboRIO/router brownouts.
	 *
	 * Default hard stop voltage is 9.0 volts.
	 *
	 * @param pdp
	 *        The robot's power distribution panel.
	 *        This is used to monitor the battery voltage.
	 */
	public AccelerationCap(PDP pdp) {
		this(pdp, AccelerationCap.DEFAULT_HARD_STOP_VOLTAGE, AccelerationCap.PRO_775_AMPS_PER_PERCENT,
			AccelerationCap.PRO_775_BASE_AMPS);
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
		double currentSpeed = outputSpeed;
		outputSpeed = inputSpeed;
		double deltaTime = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		lastUpdate = System.currentTimeMillis();
		if (Math.abs(inputSpeed) < Math.abs(currentSpeed) && Math.signum(inputSpeed) == Math.signum(currentSpeed)) {
			// Ramp down (faster) for the sake of the gearboxes
			if (Math.abs(currentSpeed - inputSpeed) < AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime) {
				outputSpeed = inputSpeed;
			} else if (inputSpeed > currentSpeed) {
				outputSpeed = currentSpeed + AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime;
			} else if (inputSpeed < currentSpeed) {
				outputSpeed = currentSpeed - AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime;
			}
		} else {
			// Brown-out protection
			try {
				double currentVoltage = pdp.getVoltageSafely();
				if (currentVoltage < hardStopVoltage) { // If we are below hardStopVoltage, start backing off
					outputSpeed = currentSpeed - AccelerationCap.ANTI_BROWNOUT_BACKOFF * Math.signum(currentSpeed);
					if (Math.abs(outputSpeed) <= AccelerationCap.ANTI_BROWNOUT_BACKOFF) {
						outputSpeed = 0;
					}
				} else {
					double currentCurrent = pdp.getAmperage();
					double batteryResistance = pdp.getBatteryResistanceSafely();
					double baseVoltage = currentCurrent * batteryResistance + currentVoltage; // Battery voltage without drop due to power
					double nextCurrent = currentCurrent
						+ AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime * motorAmpsPerPercent; // Simulate increasing motor speed
					LogKitten.wtf(currentCurrent + " " + nextCurrent + " " + baseVoltage + " " + batteryResistance);
					if (baseVoltage - nextCurrent * batteryResistance < hardStopVoltage) { // If we will go below hardStopVoltage, prevent increase
						outputSpeed = currentSpeed;
					}
				}
			}
			catch (InvalidSensorException e) { // Can't get data from PDP
				LogKitten.ex(e);
			}
			// Ramping
			if (outputSpeed == inputSpeed) { // If we did not do any anti-brownout, just ramp
				LogKitten.wtf("Ramping");
				if (Math.abs(currentSpeed - inputSpeed) < AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime) {
					outputSpeed = inputSpeed;
				} else if (inputSpeed > currentSpeed) {
					outputSpeed = currentSpeed + AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
				} else if (inputSpeed < currentSpeed) {
					outputSpeed = currentSpeed - AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
				}
			}
		}
		if (outputSpeed != inputSpeed) {
			LogKitten.wtf(outputSpeed);
		}
		return outputSpeed;
	}
}
