package org.usfirst.frc4904.standard;


public class Util {
	public static boolean checkZero(double var) {
		return Math.abs(var) < Constants.epsilon;
	}
}
