package org.usfirst.frc4904.standard.custom;


import org.usfirst.frc4904.standard.custom.sensors.NavX;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDChassisController implements ChassisController {
	private ChassisController controller;
	private double maxDegreesPerSecond;
	private double targetYaw;
	private double lastUpdate;
	private NavX ahrs;
	private double pidResult;
	public static CustomPID pid;
	
	public PIDChassisController(ChassisController controller, NavX ahrs, double Kp, double Ki, double Kd, double maxDegreesPerSecond) {
		this.controller = controller;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		this.ahrs = ahrs;
		this.ahrs.reset();
		this.ahrs.resetDisplacement();
		this.ahrs.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new CustomPID(Kp, Ki, Kd, this.ahrs);
		pid.setContinuous(true);
		pid.reset();
		pid.enable();
		targetYaw = ahrs.getYaw();
	}
	
	public void reset() {
		targetYaw = ahrs.getYaw();
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
		targetYaw = targetYaw + ((controller.getTurnSpeed() * maxDegreesPerSecond) * ((System.currentTimeMillis() / 1000) - lastUpdate));
		lastUpdate = System.currentTimeMillis() / 1000;
		if (targetYaw > 180) {
			targetYaw = -180 + (Math.abs(targetYaw) - 180);
		} else if (targetYaw < -180) {
			targetYaw = 180 - (Math.abs(targetYaw) - 180);
		}
		pid.setSetpoint(targetYaw);
		return pidResult;
	}
}
