package org.usfirst.frc4904.standard.custom.sensors;

import java.awt.Color;

public interface ColorSensor {
	int getR();

	int getG();

	int getB();

	default Color getColor() {
		return new Color(getR(), getG(), getB());
	}
}
