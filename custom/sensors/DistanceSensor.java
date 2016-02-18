package org.usfirst.frc4904.standard.custom.sensors;


import org.usfirst.frc4904.standard.custom.Named;

/**
 * A sensor that provides distance values (of type `double`).
 */
public interface DistanceSensor extends Named {
	double getDistance();
}
