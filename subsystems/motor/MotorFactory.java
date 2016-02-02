package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.custom.sensors.PDP;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * This class makes it easier to construct fancy layered motors
 * without really long nested constructors.
 *
 */
public class MotorFactory {
	private static Motor tryCastMotor(SpeedController motor) {
		if (motor instanceof Motor) {
			return (Motor) motor;
		}
		return new Motor("Motor", motor);
	}
	
	/**
	 * Basic motor. Why are you using this?
	 * 
	 * @param name
	 * @param controllers
	 * @return
	 */
	public static Motor getMotor(String name, SpeedController controllers) {
		return new Motor(name, controllers);
	}
	
	/**
	 * Motor group.
	 * Automatically names motors for you. If
	 * you try to pass in Motor objects, it
	 * will use those instead of making a new
	 * Motor object with the object inside.
	 * 
	 * @param name
	 * @param controllers
	 * @return
	 */
	public static MotorGroup getMotorGroup(String name, SpeedController... controllers) {
		Motor[] motors = new Motor[controllers.length];
		for (int i = 0; i < controllers.length; i++) {
			motors[i] = tryCastMotor(controllers[i]);
		}
		return new MotorGroup(name, motors);
	}
	
	/**
	 * Makes an encoded motor. Why are you using this?
	 * 
	 * @param name
	 * @param motor
	 * @param encoder
	 * @param P
	 * @param I
	 * @param D
	 * @param maximum
	 * @param minimum
	 * @param distancePerPulse
	 * @return
	 */
	public static EncodedMotor getEncodedMotor(String name, SpeedController motor, CustomEncoder encoder, double P, double I, double D, double maximum, double minimum, double distancePerPulse) {
		return new EncodedMotor(name, motor, encoder, P, I, D, maximum, minimum, distancePerPulse);
	}
	
	/**
	 * Makes an encoded motor. Why are you using this?
	 * 
	 * @param name
	 * @param motor
	 * @param encoder
	 * @param P
	 * @param I
	 * @param D
	 * @param maximum
	 * @param minimum
	 * @param distancePerPulse
	 * @param inverted
	 * @return
	 */
	public static EncodedMotor getEncodedMotor(String name, SpeedController motor, CustomEncoder encoder, double P, double I, double D, double maximum, double minimum, double distancePerPulse, boolean inverted) {
		return new EncodedMotor(name, motor, encoder, P, I, D, maximum, minimum, distancePerPulse, inverted);
	}
	
	/**
	 * Makes an acceleration limited motor.
	 * Why are you using this?
	 * 
	 * @param name
	 * @param motor
	 * @param pdp
	 * @return
	 */
	public static AccelMotor getAccelMotor(String name, SpeedController motor, PDP pdp) {
		return new AccelMotor(name, motor, pdp);
	}
	
	/**
	 * Makes an acceleration limited motor.
	 * Why are you using this?
	 * 
	 * @param name
	 * @param motor
	 * @param pdp
	 * @return
	 */
	public static AccelMotor getAccelMotor(String name, SpeedController motor, boolean inverted, PDP pdp) {
		return new AccelMotor(name, motor, inverted, pdp);
	}
	
	/**
	 * Makes a motor group that is acceleration limited.
	 * The acceleration limits are applied outside of the
	 * motor group in order to ensure that the
	 * underlying motors spin at the same speed.
	 * 
	 * @param name
	 * @param inverted
	 * @param pdp
	 * @param controllers
	 * @return
	 */
	public static AccelMotor getAccelMotorGroup(String name, boolean inverted, PDP pdp, SpeedController... controllers) {
		Motor[] motors = new Motor[controllers.length];
		for (int i = 0; i < controllers.length; i++) {
			motors[i] = tryCastMotor(controllers[i]);
		}
		return new AccelMotor(name, new MotorGroup(name, motors), inverted, pdp);
	}
}
