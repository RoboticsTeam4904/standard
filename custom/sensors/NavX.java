package org.usfirst.frc4904.standard.custom.sensors;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class NavX extends AHRS {
	public NavX() {
		super(SerialPort.Port.kUSB);
		super.zeroYaw();
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
