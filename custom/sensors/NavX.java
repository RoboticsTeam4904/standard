package org.usfirst.frc4904.cmdbased.custom.sensors;


import com.kauailabs.navx_mxp.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class NavX extends AHRS {
	public NavX() {
		super(constructSerialPortSafely());
		super.zeroYaw();
	}
	
	private static SerialPort constructSerialPortSafely() {
		try {
			return new SerialPort(57600, SerialPort.Port.kUSB);
		}
		catch (Error e) {
			System.out.println("FATAL: Could not connect to NavX");
			throw e;
		}
	}
	
	public float getYaw() {
		float yaw = super.getYaw();
		if (yaw < 0) {
			return 360 + yaw;
		} else {
			return yaw;
		}
	}
	
	public float getPitch() {
		float pitch = super.getPitch();
		if (pitch < 0) {
			return 360 + pitch;
		} else {
			return pitch;
		}
	}
	
	public float getRoll() {
		float roll = super.getRoll();
		if (roll < 0) {
			return 360 + roll;
		} else {
			return roll;
		}
	}
}
