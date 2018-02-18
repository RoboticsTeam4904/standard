package org.usfirst.frc4904.standard;

public class CANKitten extends Kitten {
	public CANKitten(String category) {
		super(category);
	}
	
	public static void logCAN(char kittenLevel, String message) {
		switch(kittenLevel) {
			case 'f':
				LogKitten.f(message);
				break;
			case 'e':
				LogKitten.e(message);
				break;
			case 'w':
				LogKitten.w(message);
				break;
			case 'v':
				LogKitten.v(message);
				break;
			case 'd':
				LogKitten.d(message);
				break;
		}
	}
	
	public static void logCAN(char kittenLevel, String message, Boolean override) {
		switch(kittenLevel) {
			case 'f':
				LogKitten.f(message, override);
				break;
			case 'e':
				LogKitten.e(message, override);
				break;
			case 'w':
				LogKitten.w(message, override);
				break;
			case 'v':
				LogKitten.v(message, override);
				break;
			case 'd':
				LogKitten.d(message, override);
				break;
		}
	}
	
	public static void logCANError(Exception ex) {
		LogKitten.ex(ex);
	}
	
	public static void logCANError(Exception ex, Boolean override) {
		LogKitten.ex(ex, override);
	}
}
