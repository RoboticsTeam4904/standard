package org.usfirst.frc4904.standard;


public class Util {
	public static boolean isZero(double var) {
		return Math.abs(var) < Constants.epsilon;
	}
}
