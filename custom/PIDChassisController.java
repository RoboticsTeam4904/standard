package org.usfirst.frc4904.standard.custom;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.NavX;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDChassisController implements ChassisController {
	private ChassisController controller;
	private double maxDegreesPerSecond;
	private double targetYaw;
	private double lastUpdate;
	private NavX ahrs;
	public static CustomPID pid;
	
	public PIDChassisController(ChassisController controller, NavX ahrs, double Kp, double Ki, double Kd, double maxDegreesPerSecond) {
		this.controller = controller;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		this.ahrs = ahrs;
		this.ahrs.reset();
		this.ahrs.resetDisplacement();
		this.ahrs.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new CustomPID(Kp, Ki, Kd, this.ahrs);
		pid.setInputRange(-180.0f, 180.0f);
		pid.setOutputRange(-1.0f, 1.0f);
		pid.setContinuous(true);
		pid.reset();
		pid.enable();
		targetYaw = ahrs.getYaw();
		lastUpdate = (double) System.currentTimeMillis() / 1000.0;
	}
	
	public void reset() {
		targetYaw = ahrs.getYaw();
		lastUpdate = (double) System.currentTimeMillis() / 1000.0;
		pid.disable();
		pid.reset();
		pid.enable();
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
		if (Math.abs(controller.getY()) < 0.00001 && Math.abs(controller.getX()) < 0.000001) {
			pid.setSetpoint(ahrs.getYaw());
			targetYaw = ahrs.getYaw();
			return controller.getTurnSpeed();
		}
		LogKitten.v(pid.getSetpoint() + " " + ahrs.getYaw(), true);
		targetYaw = targetYaw + ((controller.getTurnSpeed() * maxDegreesPerSecond) * (((double) System.currentTimeMillis() / 1000.0) - lastUpdate));
		lastUpdate = (double) System.currentTimeMillis() / 1000.0;
		if (targetYaw > 180) {
			targetYaw = -180 + (Math.abs(targetYaw) - 180);
		} else if (targetYaw < -180) {
			targetYaw = 180 - (Math.abs(targetYaw) - 180);
		}
		pid.setSetpoint(targetYaw);
		// LogKitten.d("Total error: " + pid.totalError + ", Raw Yaw: " + ahrs.getRawYaw() + ", Error: " + pid.getError(), true);
		return pid.get();
	}
}
