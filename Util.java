package org.usfirst.frc4904.standard;


import edu.wpi.first.wpilibj.util.BoundaryException;

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
	 * 		Whether or not it is within Double.MIN_VALUE of zero
	 */
	public static boolean isZero(double var) {
		return Math.abs(var) < Double.MIN_VALUE;
	}

	public static class Range {
		private final double min;
		private final double max;

		public Range(double min, double max) {
			if (min > max) {
				throw new BoundaryException("Range min " + min + " greater than max " + max);
			}
			this.min = min;
			this.max = max;
		}

		public double getRange() {
			return max - min;
		}

		public boolean contains(double value) {
			return value >= min && value <= max;
		}

		public double getMin() {
			return min;
		}

		public double getMax() {
			return max;
		}

		public double getCenter() {
			return (min + max) / 2.0;
		}

		/**
		 * Scales a value (between -1 and 1) to the range.
		 * Example: (new Range(0,6)).scaleValue(0.5) == 4.5
		 *
		 * @param value
		 *        between -1 and 1 (will be limited to [-1, 1])
		 * @return the scaled value
		 */
		public double scaleValue(double value) {
			return limitValue(getCenter() + value * (getRange() / 2.0));
		}

		/**
		 * Limits a value to the range.
		 * Example: (new Range(0,6)).limitValue(7) == 6
		 *
		 * @param value
		 *        the value to be limited
		 * @return the limited value
		 */
		public double limitValue(double value) {
			return Math.max(Math.min(value, max), min);
		}
	}
}
