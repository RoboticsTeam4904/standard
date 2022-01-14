// THIS FILE IS TESTED post wpilibj2

package org.usfirst.frc4904.standard.custom.sensors;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.CANMessageUnavailableException;
import org.usfirst.frc4904.standard.custom.CustomCAN;
import edu.wpi.first.wpilibj.RobotController;

/**
 * Simplified version of the PowerDistributionPanel class This class throws
 * exceptions that can actually be caught Based on code from here:
 * https://github.com/wpilibsuite/allwpilib/blob/master/hal/lib/athena/ctre/PDP.cpp
 */
public class PDP {
	public static final double PDP_CURRENT_PRECISION = 0.01;
	public static final double PDP_VOLTAGE_PRECISION = 0.125;
	protected final static double DEFAULT_VOLTAGE = 11.5;
	protected final static double DEFAULT_RESISTANCE = 80.0;
	protected final static int PDP_ID_STATUS_1 = 0x8041400;
	protected final static int PDP_ID_STATUS_2 = 0x8041440;
	protected final static int PDP_ID_STATUS_3 = 0x8041480;
	protected final static int PDP_ID_STATUS_ENERGY = 0x8041740;
	protected final CustomCAN status1;
	protected final CustomCAN status2;
	protected final CustomCAN status3;
	protected final CustomCAN statusEnergy;
	protected double cachedVoltage;
	protected double cachedCurrent;
	protected double[] cachedChannelCurrents = new double[16];
	protected double cachedResistance;
	protected double cachedEnergy;
	protected double cachedPower;
	protected long lastRead;
	private static final long MAX_AGE = 100; // How long to keep the last CAN message before throwing an error
												// (milliseconds)

	/**
	 * PDP constructor
	 *
	 * @param ID ID of the PDP. This should be the same as the ID found on the
	 *           RoboRIO web console.
	 */
	public PDP(int ID) {
		status1 = new CustomCAN("PDP STATUS 1", PDP.PDP_ID_STATUS_1 | ID);
		status2 = new CustomCAN("PDP STATUS 2", PDP.PDP_ID_STATUS_2 | ID);
		status3 = new CustomCAN("PDP STATUS 2", PDP.PDP_ID_STATUS_3 | ID);
		statusEnergy = new CustomCAN("PDP STATUS ENERGY", PDP.PDP_ID_STATUS_ENERGY | ID);
		cachedVoltage = PDP.DEFAULT_VOLTAGE;
		cachedCurrent = 0;
		cachedChannelCurrents = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		cachedResistance = PDP.DEFAULT_RESISTANCE;
		cachedEnergy = 0;
		cachedPower = 0;
	}

	/**
	 * PDP constructor
	 *
	 * This defaults to the PDP at ID 0.
	 */
	public PDP() {
		this(0);
	}

	private void readStatus(int status) throws InvalidSensorException {
		byte[] rawArray = null;
		int numberCurrents = 6;
		try {
			if (status == 1) {
				rawArray = status1.readSafely();
			} else if (status == 2) {
				rawArray = status2.readSafely();
			} else if (status == 3) {
				rawArray = status3.readSafely();
				numberCurrents = 4;
			} else {
				LogKitten.w("Trying to read PDP status " + status + ", which does not exist!");
				return;
			}
		} catch (CANMessageUnavailableException e) {
		}
		if (rawArray != null) {
			double[] tempCurrents = new double[numberCurrents];
			tempCurrents[0] = ((rawArray[0] & 0xFF) << 2 | ((rawArray[1] & 0xC0) >> 6)) * 0.125;
			tempCurrents[1] = (((rawArray[1] & 0x3F) << 4) | ((rawArray[2] & 0xF0) >> 4)) * 0.125;
			tempCurrents[2] = (((rawArray[2] & 0x0F) << 6) | ((rawArray[3] & 0x3F) >> 2)) * 0.125;
			tempCurrents[3] = (((rawArray[3] & 0xC0) << 8) | ((rawArray[4] & 0xFF))) * 0.125;
			if (numberCurrents == 6) {
				tempCurrents[4] = (((rawArray[5] & 0xFF) << 2) | ((rawArray[6] & 0xC0) >> 6)) * 0.125;
				tempCurrents[5] = (((rawArray[6] & 0x3F) << 4) | ((rawArray[7] & 0xF0) >> 4)) * 0.125;
			} else {
				cachedResistance = (rawArray[5] & 0xFF) / 1000.0; // in milliOhms
				cachedVoltage = (rawArray[6] & 0xFF) * 0.05 + 4.0;
			}
			for (int i = 0; i < numberCurrents; i++) {
				if (tempCurrents[i] < 128) { // deals with occasional issue with PDP reporting 1000+ amps (this is not a
												// bug in this code, it was observed in PowerDistributionPanel as well
					cachedChannelCurrents[i + (status - 1) * 6] = tempCurrents[i];
				}
			}
			lastRead = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - lastRead > PDP.MAX_AGE) {
			throw new InvalidSensorException("Can not read voltage from PDP");
		}
	}

	private void readEnergy() throws InvalidSensorException {
		byte[] rawArray = null;
		try {
			rawArray = statusEnergy.readSafely();
		} catch (CANMessageUnavailableException e) {
		}
		if (rawArray != null) {
			cachedCurrent = (((rawArray[1] & 0xFF) << 4) | ((rawArray[2] & 0xF0) >> 4)) * 0.125;
			cachedPower = (((rawArray[1] & 0x0F) << 12) | (rawArray[2] << 4) | ((rawArray[4] & 0xF0) >> 4)) * 0.125;
			cachedEnergy = (((rawArray[4] & 0x0F) << 24) | (rawArray[5] << 16) | (rawArray[6] << 8) | (rawArray[7]))
					* 0.000125 * rawArray[0];
			lastRead = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - lastRead > PDP.MAX_AGE) {
			throw new InvalidSensorException("Can not read energy from PDP");
		}
	}

	/**
	 * Gets the current voltage. This is the same for all channels. This function
	 * defaults to the Driver Station voltage if the PDP becomes disconnected. Note
	 * that this data will then be invalid. If you care, use the getVoltageSafely
	 * function and catch the exceptions.
	 *
	 * @return Current voltage.
	 *
	 */
	public double getVoltage() {
		try {
			return getVoltageSafely();
		} catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return RobotController.getBatteryVoltage();
		}
	}

	/**
	 * Gets the voltage on the battery.
	 *
	 * @return Current battery voltage.
	 * @throws InvalidSensorException If PDP connection is lost,
	 *                                InvalidSensorException will be thrown.
	 */
	public double getVoltageSafely() throws InvalidSensorException {
		readStatus(3);
		return cachedVoltage;
	}

	public double getBatteryResistance() {
		try {
			return getBatteryResistanceSafely();
		} catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return cachedResistance;
		}
	}

	public double getBatteryResistanceSafely() throws InvalidSensorException {
		readStatus(3);
		return cachedResistance;
	}

	public double getTotalCurrent() {
		try {
			return getTotalCurrentSafely();
		} catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return cachedCurrent;
		}
	}

	/**
	 * Gets the total current used by channels 0-15 of the PDP.
	 *
	 * @return Total robot current used.
	 * @throws InvalidSensorException If PDP connection is lost,
	 *                                InvalidSensorException will be thrown.
	 */
	public double getTotalCurrentSafely() throws InvalidSensorException {
		readEnergy();
		return cachedCurrent;
	}

	/**
	 * Gets the total power used by channels 0-15 of the PDP.
	 * 
	 * @return Total robot power
	 */
	public double getTotalPower() {
		try {
			return getTotalPowerSafely();
		} catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return cachedPower;
		}
	}

	/**
	 * Gets the total power used by channels 0-15 of the PDP, throwing an exception
	 * when the PDP is disconnected.
	 * 
	 * @return Total robot power
	 * @throws InvalidSensorException If PDP connection is lost,
	 *                                InvalidSensorException will be thrown.
	 */
	public double getTotalPowerSafely() throws InvalidSensorException {
		readEnergy();
		return cachedPower;
	}

	/**
	 * Gets the total energy used by channels 0-15 of the PDP.
	 * 
	 * @return Total robot energy used
	 */
	public double getTotalEnergy() {
		try {
			return getTotalEnergySafely();
		} catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return cachedEnergy;
		}
	}

	/**
	 * Gets the total energy used by channels 0-15 of the PDP, throwing an exception
	 * when the PDP is disconnected.
	 * 
	 * @return Total robot energy used
	 * @throws InvalidSensorException If PDP connection is lost,
	 *                                InvalidSensorException will be thrown.
	 */
	public double getTotalEnergySafely() throws InvalidSensorException {
		readEnergy();
		return cachedEnergy;
	}

	/**
	 * Gets the current used by a single channel.
	 *
	 * @param channel the channel to read the current for
	 * @return the current from that channel
	 * @throws InvalidSensorException if the PDP is not sending data
	 */
	public double getCurrentSafely(int channel) throws InvalidSensorException {
		if (channel < 0) {
			return 0.0;
		} else if (channel <= 5) {
			readStatus(1);
		} else if (channel <= 11) {
			readStatus(2);
		} else if (channel <= 15) {
			readStatus(3);
		} else {
			LogKitten.w("Trying to read PDP channel " + channel + ", which does not exist!");
			return 0.0;
		}
		return cachedChannelCurrents[channel];
	}

	/**
	 * Gets the current used by a single channel.
	 *
	 * @param channel the channel to read the current for
	 * @return the current from that channel
	 */
	public double getCurrent(int channel) {
		try {
			return getCurrentSafely(channel);
		} catch (InvalidSensorException e) {
			LogKitten.ex(e);
			return cachedChannelCurrents[channel];
		}
	}
}
