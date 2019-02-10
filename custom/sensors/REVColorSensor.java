package org.usfirst.frc4904.standard.custom.sensors;

import java.awt.Color;
import edu.wpi.first.wpilibj.I2C;
import org.usfirst.frc4904.standard.custom.sensors.ColorSensor;
import org.usfirst.frc4904.standard.custom.sensors.DistanceSensor;

public class REVColorSensor implements ColorSensor {
	private static int ENABLE = 0x00;
	private static int ENABLE_FIELDS = 3;
	private static int RDATA = 0x16;
	private static int RDATAH = 0x17;
	private static int GDATA = 0x18;
	private static int GDATAH = 0x19;
	private static int BDATA = 0x20;
	private static int BDATAH = 0x21;

	private final I2C device;

	public REVColorSensor(I2C.Port port, int id) {
		device = new  I2C(port, id);
		device.write(ENABLE, ENABLE_FIELDS);
	}

	private int getColorComponent(int lowAddress, int highAddress) {
		byte[] buf = new byte[1];
		device.read(BDATA, 1, buf);
		byte low = buf[0];
		device.read(BDATAH, 1, buf);
		byte high = buf[0];
		return (int) Math.floor((high << 8 | low) / 256);
	}

	public int getR() {
		return getColorComponent(RDATA, RDATAH);
	}

	public int getG() {
		return getColorComponent(GDATA, GDATAH);
	}

	public int getB() {
		return getColorComponent(BDATA, BDATAH);
	}

	public Color getColor() {
		return new Color(getR(), getG(), getB());
	}
}
