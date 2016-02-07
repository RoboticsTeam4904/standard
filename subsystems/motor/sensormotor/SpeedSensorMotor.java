package org.usfirst.frc4904.standard.subsystems.motor.sensormotor;


import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A generic interface for a motor that
 * is aware of its speed (e.g. with an
 * encoder).
 *
 */
public interface SpeedSensorMotor extends SpeedController {
	double getRate();
	
	PIDController getRatePID();
}
