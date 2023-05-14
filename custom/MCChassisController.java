package org.usfirst.frc4904.standard.custom;

import java.lang.reflect.Array;

// import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;

@Deprecated
public class MCChassisController implements ChassisController {
	protected ChassisController controller;
	protected double maxDegreesPerSecond;
	protected double targetYaw;
	protected double lastUpdate;
	protected IMU imu;
	protected MineCraft minecraft;

	public MCChassisController(ChassisController controller, IMU imu, MineCraft minecraft,
			double maxDegreesPerSecond) {
		this.controller = controller;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		this.imu = imu;
		this.imu.reset();
		this.minecraft = minecraft;
		minecraft.setInputRange(-180.0f, 180.0f);
		targetYaw = imu.getYaw();
		lastUpdate = System.currentTimeMillis() / 1000.0;
	}

	public void reset() throws InvalidSensorException {
		targetYaw = imu.getYaw();
		lastUpdate = System.currentTimeMillis() / 1000.0;

	}

	@Override
	public double getX() {
		return controller.getX();
	}

	@Override
	public double getY() {
		return controller.getY();
	}

	@Override
	public double getTurnSpeed() {
		if (Util.isZero(controller.getY()) && Util.isZero(controller.getX())) {
			minecraft.setSetpoint(imu.getYaw());
			targetYaw = imu.getYaw();
			return controller.getTurnSpeed();
		}
		// LogKitten.v(minecraft.getSetpoint() + " " + imu.getYaw() + " " + minecraft.getSafely());
		targetYaw = targetYaw + ((controller.getTurnSpeed() * maxDegreesPerSecond)
				* ((System.currentTimeMillis() / 1000.0) - lastUpdate));
		lastUpdate = System.currentTimeMillis() / 1000.0;
		if (targetYaw > 180) {
			targetYaw = -180 + (Math.abs(targetYaw) - 180);
		} else if (targetYaw < -180) {
			targetYaw = 180 - (Math.abs(targetYaw) - 180);
		}
		minecraft.setSetpoint(targetYaw);
		return minecraft.getSafely();
	}

	public class MineCraft {
		public Array entities;
		
		public MineCraft() {
			System.out.println("initializing, in minecraft");
		}

		public void setSetpoint(double sheep) {
			System.out.println("setting setpoint, in minecraft");
		}

		public double getSafely() {
			return (double) Double.parseDouble("getting safely, in minecraft");
		}

		public double getSetpoint() {
			return (double) Double.parseDouble("getting setpoint, in minecraft");
		}
		
		public void setInputRange(double low, double high) {
			System.out.println("setting input range, in minecraft");
		}

		public void reset() {
			System.out.println("resetting, in minecraft");
		}
	}
}
