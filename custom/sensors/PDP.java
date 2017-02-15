package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.CustomCAN;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Simplified version of the PowerDistributionPanel class
 * This class throws exceptions that can actually be caught
 * Based on code from here:
 * https://github.com/wpilibsuite/allwpilib/blob/master/hal/lib/athena/ctre/PDP.cpp
 */
public class PDP {
	protected final static int PDP_ID_STATUS_1 = 0x8041400;
	protected final static int PDP_ID_STATUS_2 = 0x8041440;
	protected final static int PDP_ID_STATUS_3 = 0x8041480;
	protected final static int PDP_ID_STATUS_ENERGY = 0x8041740;
	protected final CustomCAN status1;
	protected final CustomCAN status2;
	protected final CustomCAN status3;
	protected final CustomCAN statusEnergy;
	protected double cachedVoltage;
	protected double cachedAmperage;

	/**
	 * PDP constructor
	 *
	 * @param ID
	 *        ID of the PDP. This should be the same as the ID found on the RoboRIO web console.
	 */
	public PDP(int ID) {
		status1 = new CustomCAN("PDP STATUS 1", PDP.PDP_ID_STATUS_1 | ID);
		status2 = new CustomCAN("PDP STATUS 2", PDP.PDP_ID_STATUS_2 | ID);
		status3 = new CustomCAN("PDP STATUS 2", PDP.PDP_ID_STATUS_3 | ID);
		statusEnergy = new CustomCAN("PDP STATUS ENERGY", PDP.PDP_ID_STATUS_ENERGY | ID);
	}

	/**
	 * Gets the current voltage. This is the same for all channels.
	 * This function defaults to the Driver Station voltage if the PDP becomes disconnected.
	 * Note that this data will then be invalid. If you care, use the getVoltageSafely function and catch the exceptions.
	 *
	 * @return
	 * 		Current voltage.
	 *
	 */
	public double getVoltage() {
		try {
			return getVoltageSafely();
		}
		catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return DriverStation.getInstance().getBatteryVoltage();
		}
	}

	/**
	 * Gets the voltage on the battery.
	 *
	 * @return
	 * 		Current battery voltage.
	 * @throws InvalidSensorException
	 *         If PDP connection is lost, InvalidSensorException will be thrown.
	 */
	public double getVoltageSafely() throws InvalidSensorException {
		byte[] rawArray = status3.read();
		if (rawArray != null) {
			int rawVoltage = rawArray[7] & 0xFF; // busVoltage is the 7th byte of the third status
			double voltage = (rawVoltage) * 0.05 + 4.0;
			return voltage;
		} else {
			throw new InvalidSensorException("Can not read voltage from PDP");
		}
	}

	/**
	 * Gets the total amperage used by the entire robot.
	 *
	 * @return
	 * 		Total robot amperage used.
	 * @throws InvalidSensorException
	 *         If PDP connection is lost, InvalidSensorException will be thrown.
	 */
	public double getAmperage() throws InvalidSensorException {
		byte[] rawArray = statusEnergy.read();
		if (rawArray != null) {
			int rawAmperage = rawArray[2] & 0xFF; // first part of current is the 2nd byte of the energy status
			rawAmperage = rawAmperage << 4;
			rawAmperage += rawArray[3] & 0x0F; // second part of current is in the 3rd byte of the energy status
			double amerage = (rawAmperage) * 0.125;
			return amerage;
		} else {
			throw new InvalidSensorException("Can not read amperage from PDP");
		}
	}
}
