package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An encoded motor is a motor with a set of variables relevant to controlling a motor with an encoder.
 * It contains an Encoder, PID constants, and range information.
 */
public class EncodedMotor extends SensorMotor {
	public EncodedMotor(String name, boolean inverted, SpeedModifier speedModifier, CustomEncoder encoder, SpeedController... motor) {
		super(name, inverted, speedModifier, encoder, motor);
		pid.setInputRange(-2147483648, 2147483647);
	}
	
	public EncodedMotor(String name, boolean isInverted, CustomEncoder sensor, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, motors);
	}
	
	public EncodedMotor(String name, SpeedModifier slopeController, CustomEncoder sensor, SpeedController... motors) {
		this(name, false, slopeController, sensor, motors);
	}
	
	public EncodedMotor(String name, CustomEncoder sensor, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, motors);
	}
	
	public EncodedMotor(boolean isInverted, SpeedModifier speedModifier, CustomEncoder sensor, SpeedController... motors) {
		this("EncodedMotor", isInverted, speedModifier, sensor, motors);
	}
	
	public EncodedMotor(boolean isInverted, CustomEncoder sensor, SpeedController... motors) {
		this("EncodedMotor", isInverted, sensor, motors);
	}
	
	public EncodedMotor(SpeedModifier speedModifier, CustomEncoder sensor, SpeedController... motors) {
		this("EncodedMotor", speedModifier, sensor, motors);
	}
	
	public EncodedMotor(CustomEncoder sensor, SpeedController... motors) {
		this("EncodedMotor", sensor, motors);
	}
}
