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
	
	public static class Range {
		double min;
		double max;
		boolean inclusive;
		
		public Range(double min, double max, boolean inclusive) {
			this.min = min;
			this.max = max;
			this.inclusive = inclusive;
		}
		
		public double getDistance() {
			return max - min;
		}
		
		public boolean contains(double value) {
			if (inclusive) {
				return value >= min && value <= max;
			}
			return value > min && value < max;
		}
		
		public double getMin() {
			return min;
		}
		
		public double getMax() {
			return max;
		}
		
		public double getAverage() {
			return (min + max) / 2.0;
		}
		
		/**
		 * Scales a value (between -1 and 1) to the range.
		 * Example: (new Range(0,6)).scaleValue(0.5) == 4.5
		 * 
		 * @param value
		 *        between -1 and 1
		 * @return the scaled value
		 */
		public double scaleValue(double value) {
			return getAverage() + value * (getDistance() / 2.0);
		}
	}
}
