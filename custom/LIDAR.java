package org.usfirst.frc4904.cmdbased.custom;


import java.util.Arrays;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * The LIDAR class lets other classes retrieve data from an Arduino
 * that reads from a physical Neato XV-11 LIDAR. It needs to be updated
 * to receive new data. It provides methods to read raw data, find
 * distances, and find lines (totes) via a Hough transform.
 */
public class LIDAR {
	private final LogKitten logger;
	private final SerialPort port; // Serial port used to talk to the LIDAR,
	private volatile int[] dists; // Array that contains distances to objects 360 degrees around the LIDAR
	public static final int LIDAR_MOUNT_OFFSET = -100; // mm to right. Cartesian. Because.
	public static final int GRABBER_LENGTH = 700; // Distance from LIDAR to grabber
	public static final int GRABBER_LENGTH_OFFSET = GRABBER_LENGTH + 100; // Go an extra 100 mm (to tell if lines are the grabber or totes)
	public static final int CORRECTED_ANGLE_BREADTH = 16; // How many angles to average when correcting an angle. Should be divisible by 4
	public static final boolean DISABLED = false;
	private static final double[] sinCache = new double[360];
	private static final double[] cosCache = new double[360];
	// Cache values
	static {
		for (int i = 0; i < 360; i++) {
			double radians = i * Math.PI / 180;
			cosCache[i] = Math.cos(radians);
			sinCache[i] = Math.sin(radians);
		}
	}
	
	public LIDAR() {
		dists = new int[360];
		logger = new LogKitten();
		try {
			port = new SerialPort(115200, SerialPort.Port.kMXP);
		}
		catch (Error e) {
			logger.f("Could not connect to LIDAR");
			throw e;
		}
	}
	
	/**
	 * Reads the distance from the LIDAR to wherever its beam hits at a certain angle relative to the chassis.
	 * 
	 * @param angle
	 *        The angle at which to return the distance, as an integer.
	 *        Note that 0 as the input means straight ahead - the function
	 *        shifts the data so 0 is to the right, like in normal polar coordinates.
	 * @return The requested distance as an integer.
	 */
	private int read(int angle) {
		// Make sure angle is within the correct range and shift it.
		// The angle is shifted by 90 so that 0 is to the right (like a normal graph.)
		// It is then modded. Lastly, we add 360 and mod again to account for negative angle inputs.
		angle = ((angle + 90) % 360 + 360) % 360;
		// Write to the port requesting distance at angle.
		port.flush(); // Flush port to make sure we get the data we ask for
		port.writeString(Integer.toString(angle) + "#");
		// Read response.
		while (port.getBytesReceived() < 2) {} // Wait till we receive the data
		String data = port.readString();
		if (data.indexOf('\n') <= 0) { // If there is no data or the only character is a newline
			logger.w("Got nonsensical data (no newline terminator) at angle " + angle);
			return 0;
		}
		data = data.substring(0, data.indexOf('\n') - 1);
		logger.d("Reading LIDAR at angle " + angle + " bytes received " + port.getBytesReceived() + " distance " + data);
		return Integer.parseInt(data); // Return data as integer
	}
	
	/**
	 * Returns the array of distances around the LIDAR. 90 is straight forward. Returns an updated array, so it returns quickly.
	 * 
	 * @return An int array of distances 360 degrees around the LIDAR.
	 */
	public int[] getDists() {
		return dists;
	}
	
	/**
	 * Returns nothing now
	 */
	public int[] getLine() {
		return new int[] {0, 0, 0, 0};
	}
	
	/**
	 * Gets the distance at an angle
	 * 
	 * @param angle
	 * @return
	 */
	public int getCorrectedAngleDist(int angle) {
		int[] avDists = Arrays.copyOfRange(dists, angle - LIDAR.CORRECTED_ANGLE_BREADTH / 2, angle + LIDAR.CORRECTED_ANGLE_BREADTH / 2);
		Arrays.sort(avDists);
		int firstQuartile = avDists[LIDAR.CORRECTED_ANGLE_BREADTH * (1 / 4)];
		int median = avDists[LIDAR.CORRECTED_ANGLE_BREADTH * (2 / 4)];
		int thirdQuartile = avDists[LIDAR.CORRECTED_ANGLE_BREADTH * (3 / 4)];
		int trimean = (firstQuartile + median + thirdQuartile) / 3;
		return trimean;
	}
	
	public void update() {
		if (LIDAR.DISABLED) {
			return;
		}
		logger.d("Updating LIDAR");
		try {
			for (int i = 0; i < 360; i++) { // We only want the area in front of the LIDAR
				int data = read(i);
				if (data != 0) {
					dists[i] = data;
				}
			}
		}
		catch (Exception e) {
			logger.e("Error updating LIDAR: ");
			e.printStackTrace();
		}
	}
}