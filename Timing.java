package org.usfirst.frc4904.standard;


public class Timing {
	/**
	 * Delta time since last time in seconds.
	 * 
	 * @param lastTime
	 *        Last update in seconds
	 * @return
	 * 		Delta time since lastTime in seconds.
	 */
	public static double getDeltaT(double lastTime) {
		return ((double) System.currentTimeMillis() / 1000.0) - lastTime;
	}
}
