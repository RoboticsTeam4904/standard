package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;

// import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PDP;

/**
 * A SpeedModifier that does brownout protection and voltage ramping. This is
 * designed to reduce power consumption (via voltage ramping) and prevent
 * RoboRIO/router brownouts.
 */
public class AccelerationCap implements SpeedModifier {
	public final static double MAXIMUM_MOTOR_INCREASE_PER_SECOND = 2.0;
	public final static double ANTI_BROWNOUT_BACKOFF_PER_SECOND = 2.4; // How much to throttle a motor down to avoid
																		// brownout
	public final static double DEFAULT_HARD_STOP_VOLTAGE = 7.0;
	protected final static double TIMEOUT_SECONDS = 0.5; // If we do not get a value for this long, set the motor to
															// zero (this is designed to handle the case where the robot
															// is disabled with the motors still running_
	protected final static double TICKS_PER_PDP_DATA = 1.25; // PDP update speed (25ms) / Scheduler loop time (20ms)
	protected final static double AVAILABLE_VOLTAGE_TO_RAMPING_SCALE = 0.55; // Experimentally determined (but estimated
																				// as full speed above 11.8 volts)
	protected long lastUpdate; // in milliseconds
	protected final static double REASONABLE_RESTING_CURRENT = 10.0;
	protected final PDP pdp;
	protected final double hardStopVoltage;
	protected double currentSpeed;
	protected double voltage;
	protected double lastVoltage;

	/**
	 * A SpeedModifier that does brownout protection and voltage ramping. This is
	 * designed to reduce power consumption (via voltage ramping) and prevent
	 * RoboRIO/router brownouts.
	 *
	 * @param pdp             The robot's power distribution panel. This is used to
	 *                        monitor the battery voltage.
	 * @param softStopVoltage Voltage to stop increasing motor speed at.
	 * @param hardStopVoltage Voltage to begin decreasing motor speed at.
	 */
	public AccelerationCap(PDP pdp, double hardStopVoltage) {
		this.pdp = pdp;
		this.hardStopVoltage = hardStopVoltage;
		currentSpeed = 0;
		voltage = pdp.getVoltage();
		lastVoltage = voltage;
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * A SpeedModifier that does brownout protection and voltage ramping. This is
	 * designed to reduce power consumption (via voltage ramping) and prevent
	 * RoboRIO/router brownouts.
	 *
	 * Default hard stop voltage is 7.0 volts.
	 *
	 * @param pdp The robot's power distribution panel. This is used to monitor the
	 *            battery voltage.
	 */
	public AccelerationCap(PDP pdp) {
		this(pdp, AccelerationCap.DEFAULT_HARD_STOP_VOLTAGE);
	}

	protected double calculate(double inputSpeed) {
		double deltaTime = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		lastUpdate = System.currentTimeMillis();
		double newVoltage;
		try {
			newVoltage = pdp.getVoltageSafely();
		} catch (InvalidSensorException e) {
			// Apply more naive ramping, then return
			if (Math.abs(currentSpeed - inputSpeed) > AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime) {
				if (inputSpeed > currentSpeed) {
					return currentSpeed + AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
				} else if (inputSpeed < currentSpeed) {
					return currentSpeed - AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
				}
			}
			return inputSpeed;
		}
		if (!new Util.Range(voltage - PDP.PDP_VOLTAGE_PRECISION, voltage + PDP.PDP_VOLTAGE_PRECISION)
				.contains(newVoltage)) {
			lastVoltage = voltage;
			voltage = newVoltage;
		}
		// If we have not called this function in a while, we were probably disabled, so
		// we should just output zero
		if (deltaTime > AccelerationCap.TIMEOUT_SECONDS) {
			return 0;
		}
		// After doing updates, check for low battery voltage first
		double currentVoltage = newVoltage;
		if (currentVoltage < hardStopVoltage) { // If we are below hardStopVoltage, stop motors
			// LogKitten.w("Low voltage, AccelerationCap stopping motors");
			return 0;
		}
		if (Math.abs(inputSpeed) < Math.abs(currentSpeed) && Math.signum(inputSpeed) == Math.signum(currentSpeed)) {
			return inputSpeed;
		}
		double rampedSpeed = inputSpeed;
		// Ramping
		if (Math.abs(currentSpeed - inputSpeed) > AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime) {
			if (inputSpeed > currentSpeed) {
				rampedSpeed = currentSpeed + AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime
						* AccelerationCap.AVAILABLE_VOLTAGE_TO_RAMPING_SCALE * (currentVoltage - hardStopVoltage);
			} else if (inputSpeed < currentSpeed) {
				rampedSpeed = currentSpeed - AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime
						* AccelerationCap.AVAILABLE_VOLTAGE_TO_RAMPING_SCALE * (currentVoltage - hardStopVoltage);
			}
		}
		// After ramping, apply brown-out protection
		// Even if we are still above the hard stop voltage, try to avoid going below
		// next tick
		if (currentVoltage < hardStopVoltage + (lastVoltage - voltage) * AccelerationCap.TICKS_PER_PDP_DATA / 2.0) {
			// LogKitten.w("Preventative capping to "
			// 		+ (currentSpeed
			// 				- AccelerationCap.ANTI_BROWNOUT_BACKOFF_PER_SECOND * Math.signum(currentSpeed) * deltaTime)
			// 		+ " from " + inputSpeed);
			return currentSpeed
					- AccelerationCap.ANTI_BROWNOUT_BACKOFF_PER_SECOND * Math.signum(currentSpeed) * deltaTime;
		}
		return rampedSpeed;
	}

	/**
	 * Modify the input speed and get the new output. AccelerationCap does voltage
	 * ramping, which means that motor speed changes take place over 1/16th of a
	 * second rather than instantly. This decreases power consumption, but minimally
	 * affects performance.
	 *
	 * AccelerationCap also prevents brownouts by slowing motors as voltage
	 * decreases.
	 */
	@Override
	public double modify(double inputSpeed) {
		currentSpeed = calculate(inputSpeed);
		// LogKitten.d("AccelerationCap outputed: " + currentSpeed);
		return currentSpeed;
	}
}