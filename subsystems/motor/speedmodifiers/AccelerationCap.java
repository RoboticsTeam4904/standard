package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PDP;

/**
 * A SpeedModifier that does brownout protection and voltage ramping.
 * This is designed to reduce power consumption (via voltage ramping)
 * and prevent RoboRIO/router brownouts.
 */
public class AccelerationCap implements SpeedModifier {
	public final static double MAXIMUM_MOTOR_INCREASE_PER_SECOND = 2.4;
	public final static double MAXIMUM_MOTOR_DECREASE_PER_SECOND = 4.8;
	public final static double ANTI_BROWNOUT_BACKOFF = 0.1; // How much to throttle a motor down to avoid brownout
	public final static double DEFAULT_HARD_STOP_VOLTAGE = 9.0;
	protected final static double TIMEOUT_SECONDS = 0.5; // If we do not get a value for this long, set the motor to zero (this is designed to handle the case where the robot is disabled with the motors still running_
	protected long lastUpdate; // in milliseconds
	protected final PDP pdp;
	protected final double hardStopVoltage;
	protected double currentSpeed;

	protected enum VOLTAGE_DROP_THRESHOLDS {
		NO_DROP(0.5, 0.0), MEDIUM_DROP(2.5, 0.5), HIGH_DROP(7.5, 1.0);
		double voltageDrop;
		double softStopDelta;

		private VOLTAGE_DROP_THRESHOLDS(double voltageDrop, double softStopDelta) {
			this.voltageDrop = voltageDrop;
			this.softStopDelta = softStopDelta;
		}
	}

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
	public AccelerationCap(PDP pdp, double hardStopVoltage) {
		this.pdp = pdp;
		this.hardStopVoltage = hardStopVoltage;
		currentSpeed = 0;
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
		this(pdp, AccelerationCap.DEFAULT_HARD_STOP_VOLTAGE);
	}

	protected double calculate(double inputSpeed) {
		double deltaTime = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		lastUpdate = System.currentTimeMillis();
		if (deltaTime > AccelerationCap.TIMEOUT_SECONDS) {
			return 0;
		}
		if (Math.abs(inputSpeed) < Math.abs(currentSpeed) && Math.signum(inputSpeed) == Math.signum(currentSpeed)) {
			// Ramp down (faster) for the sake of the gearboxes
			if (Math.abs(currentSpeed - inputSpeed) < AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime) {
				return inputSpeed;
			}
			if (inputSpeed > currentSpeed) {
				return currentSpeed + AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime;
			}
			if (inputSpeed < currentSpeed) {
				return currentSpeed - AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime;
			}
		}
		double rampedSpeed = inputSpeed;
		// Ramping
		if (Math.abs(currentSpeed - inputSpeed) > AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime) {
			if (inputSpeed > currentSpeed) {
				rampedSpeed = currentSpeed + AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
			} else if (inputSpeed < currentSpeed) {
				rampedSpeed = currentSpeed - AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
			}
		}
		// After ramping, apply brown-out protection
		double currentVoltage = pdp.getVoltage(); // Allow fallback to DS voltage
		if (currentVoltage < hardStopVoltage) { // If we are below hardStopVoltage, start backing off
			rampedSpeed = currentSpeed - AccelerationCap.ANTI_BROWNOUT_BACKOFF * Math.signum(currentSpeed);
			if (Math.abs(rampedSpeed) <= AccelerationCap.ANTI_BROWNOUT_BACKOFF) {
				return 0;
			}
			return rampedSpeed;
		}
		// Even if we are still above the hard stop voltage, try to avoid going below next tick
		try {
			double voltageDrop = pdp.getAmperage() * pdp.getBatteryResistanceSafely();
			if (voltageDrop < VOLTAGE_DROP_THRESHOLDS.NO_DROP.voltageDrop) {
				return rampedSpeed;
			} else if (voltageDrop < VOLTAGE_DROP_THRESHOLDS.MEDIUM_DROP.voltageDrop
				&& currentVoltage < hardStopVoltage + VOLTAGE_DROP_THRESHOLDS.MEDIUM_DROP.softStopDelta) {
				return currentSpeed;
			} else if (voltageDrop < VOLTAGE_DROP_THRESHOLDS.HIGH_DROP.voltageDrop
				&& currentVoltage < hardStopVoltage + VOLTAGE_DROP_THRESHOLDS.HIGH_DROP.softStopDelta) {
				return currentSpeed;
			}
		}
		catch (InvalidSensorException e) { // Can't get data from PDP, we just hope drivers do not drive too hard
			LogKitten.ex(e);
		}
		return rampedSpeed;
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
		currentSpeed = calculate(inputSpeed);
		LogKitten.d("AccelerationCap outputed: " + currentSpeed);
		return currentSpeed;
	}
}
