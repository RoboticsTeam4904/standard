package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.custom.Nameable;

/**
 * A sensor that provides distance values (of type `double`).
 */
public interface DistanceSensor extends Nameable {
	double getDistance();

	double getDistanceSafely() throws InvalidSensorException;
}
