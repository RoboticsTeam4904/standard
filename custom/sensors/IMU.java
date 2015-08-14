package org.usfirst.frc4904.cmdbased.custom.sensors;


import java.util.Arrays;
import org.usfirst.frc4904.cmdbased.InPipable;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IMU extends NavX implements PIDSource, InPipable {
	private double[] angles; // Angle 0 is perpendicular (yaw), Angle 1 is lateral (pitch), Angle 2 is longitudinal (roll)
	private double[] lastAngles;
	private double[] rate; // Same as above
	private double lastTime;
	private static final double GOING_OVER_PLATFORM_ANGLE = 5;
	private static final double TURN_SCALE = 1.0 / 360.0; // Scale degrees per second to between -1 and 1
	
	public IMU() {
		super();
		angles = new double[3];
		lastAngles = new double[3];
		rate = new double[3];
		Arrays.fill(angles, 0);
		Arrays.fill(lastAngles, 0);
		lastTime = getTime();
		zero();
	}
	
	public double[] readRate() {
		return rate;
	}
	
	public void zero() {
		super.zeroYaw();
	}
	
	public synchronized void update() {
		double time = getTime();
		updateData();
		for (int i = 0; i < 3; i++) {
			rate[i] = ((angles[i] - lastAngles[i]) / (time - lastTime)) * TURN_SCALE;
		}
		lastTime = time;
		lastAngles = angles;
	}
	
	private void updateData() {
		angles[0] = super.getYaw();
		angles[1] = super.getRoll();
		angles[2] = super.getPitch();
		SmartDashboard.putNumber("Yaw", angles[0]);
		SmartDashboard.putNumber("Pitch", angles[1]); // This might be roll
		SmartDashboard.putNumber("Roll", angles[2]); // This might be pitch
	}
	
	public double[] read() {
		return angles;
	}
	
	private double getTime() {
		return System.currentTimeMillis();
	}
	
	public boolean isTipping() {
		return (angles[1] > GOING_OVER_PLATFORM_ANGLE && angles[1] < 360 - GOING_OVER_PLATFORM_ANGLE) || (angles[2] > GOING_OVER_PLATFORM_ANGLE && angles[2] < 360 - GOING_OVER_PLATFORM_ANGLE);
	}
	
	public double pidGet() {
		return angles[0];
	}
	
	public enum PipeModes {
		Euler;
	}
	
	public double[] readPipe() {
		return angles;
	}
	
	public void setPipe(Enum mode) {}
}