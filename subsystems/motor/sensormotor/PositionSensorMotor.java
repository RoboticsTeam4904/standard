package org.usfirst.frc4904.standard.subsystems.motor.sensormotor;


import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A generic interface for a motor that is
 * aware of its position (e.g. with an
 * encoder or a potentiometer).
 *
 */
public interface PositionSensorMotor extends SpeedController {
	double getPosition();
	
	PIDController getPositionPID();
}
