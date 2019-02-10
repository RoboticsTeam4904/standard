package org.usfirst.frc4904.standard.custom.sensors;

import java.awt.Color;

/**
 * A sensor that gives colors of whatever its looking at.
 */
public interface ColorSensor {
	int getR();

	int getG();

	int getB();

	default Color getColor() {
		return new Color(getR(), getG(), getB());
	}
}
