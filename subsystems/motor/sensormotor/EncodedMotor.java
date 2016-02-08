package org.usfirst.frc4904.standard.subsystems.motor.sensormotor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An encoded motor is a motor with a set
 * of variables relevant to controlling
 * a motor with an encoder. It contains
 * an Encoder, PID constants, and range
 * information.
 *
 */
public class EncodedMotor extends SensorMotor {
	public EncodedMotor(String name, boolean inverted, SpeedModifier speedModifier, CustomEncoder encoder, SpeedController... motor) {
		super(name, inverted, speedModifier, encoder, motor);
	}
}
