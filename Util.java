package org.usfirst.frc4904.standard;


/**
 * Common utilities
 */
public class Util {
	/**
	 * A function for determining if floating point numbers are effectively zero.
	 * Floating point arithmetic tends to introduce very small errors.
	 * 
	 * @param var
	 *        The floating point number you want to compare
	 * @return
	 * 		Whether or not it is within Constants.EPSILON of zero
	 */
	public static boolean isZero(double var) {
		return Math.abs(var) < Constants.EPSILON;
	}
}
