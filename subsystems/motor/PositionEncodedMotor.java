package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An encoded motor is a motor with a set of variables relevant to controlling a motor with an encoder.
 * It contains an Encoder, PID constants, and range information.
 */
public class PositionEncodedMotor extends PositionSensorMotor {
	public PositionEncodedMotor(String name, boolean inverted, SpeedModifier speedModifier, CustomEncoder encoder, SpeedController... motor) {
		super(name, inverted, speedModifier, encoder, motor);
	}
	
	public PositionEncodedMotor(String name, boolean isInverted, CustomEncoder sensor, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, motors);
	}
	
	public PositionEncodedMotor(String name, SpeedModifier slopeController, CustomEncoder sensor, SpeedController... motors) {
		this(name, false, slopeController, sensor, motors);
	}
	
	public PositionEncodedMotor(String name, CustomEncoder sensor, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, motors);
	}
	
	public PositionEncodedMotor(boolean isInverted, SpeedModifier speedModifier, CustomEncoder sensor, SpeedController... motors) {
		this("PositionEncodedMotor", isInverted, speedModifier, sensor, motors);
	}
	
	public PositionEncodedMotor(boolean isInverted, CustomEncoder sensor, SpeedController... motors) {
		this("PositionEncodedMotor", isInverted, sensor, motors);
	}
	
	public PositionEncodedMotor(SpeedModifier speedModifier, CustomEncoder sensor, SpeedController... motors) {
		this("PositionEncodedMotor", speedModifier, sensor, motors);
	}
	
	public PositionEncodedMotor(CustomEncoder sensor, SpeedController... motors) {
		this("PositionEncodedMotor", sensor, motors);
	}
}
