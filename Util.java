package org.usfirst.frc4904.standard;


/**
 * Common utilities
 */
public class Util {
	/**
	 * A constant for dealing with floating point errors.
	 * Add/subtract this from floats when doing equality comparisons.
	 */
	public static final double EPSILON = 0.0000001;
	
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
		return Math.abs(var) < Util.EPSILON;
	}
}
