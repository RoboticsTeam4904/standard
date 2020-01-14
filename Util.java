package org.usfirst.frc4904.standard;

import edu.wpi.first.hal.util.BoundaryException;

/**
 * Common utilities
 */
public class Util {
	/**
	 * Returns true if {@code value} is less than {@code epsilon}. This is useful
	 * for floating point numbers, whose arithmetic operations tend to introduce
	 * small errors.
	 *
	 * @param value   The floating point number to be compared
	 * @param epsilon The maximum magnitude of var such that it can be considered
	 *                zero
	 * @return Whether or not {@code value} is less than {@code epsilon}
	 */
	public static boolean isZero(double value, double epsilon) {
		return Math.abs(value) < epsilon;
	}

	/**
	 * Returns true if {@code value} is less than {@code epsilon}. This is useful
	 * for floating point numbers, whose arithmetic operations tend to introduce
	 * small errors.
	 *
	 * @param value The floating point number to be compared
	 * @return Whether or not {@code value} is effectively zero
	 */
	public static boolean isZero(double value) {
		return isZero(value, Math.sqrt(Math.ulp(1.0)));
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
		 * Scales a value (between -1 and 1) to the range. Example: (new
		 * Range(0,6)).scaleValue(0.5) == 4.5
		 *
		 * @param value between -1 and 1 (will be limited to [-1, 1])
		 * @return the scaled value
		 */
		public double scaleValue(double value) {
			return limitValue(getCenter() + value * (getRange() / 2.0));
		}

		/**
		 * Limits a value to the range. Example: (new Range(0,6)).limitValue(7) == 6
		 *
		 * @param value the value to be limited
		 * @return the limited value
		 */
		public double limitValue(double value) {
			return Math.max(Math.min(value, max), min);
		}
	}
}
