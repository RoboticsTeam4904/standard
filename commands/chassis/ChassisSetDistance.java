package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;

public class ChassisSetDistance extends ChassisConstant {
	protected CustomEncoder[] motorEncoders;
	protected double distance;
	protected double[] initialDistances;
	
	public ChassisSetDistance(Chassis chassis, double distance, double speed, CustomEncoder... motorEncoders) {
		super(chassis, 0.0, speed, 0.0, Double.MAX_VALUE);
		this.motorEncoders = motorEncoders;
		this.distance = distance;
		initialDistances = new double[motorEncoders.length];
		for (int i = 0; i < motorEncoders.length; i++) {
			initialDistances[i] = motorEncoders[i].getDistance();
		}
	}
	
	@Override
	protected boolean isFinished() {
		double distanceSum = 0;
		for (int i = 0; i < motorEncoders.length; i++) {
			distanceSum += motorEncoders[i].getDistance() - initialDistances[i];
			LogKitten.d("Encoder " + i + " reads " + motorEncoders[i].getDistance() + " out of " + distance);
		}
		double distanceAvg = distanceSum / motorEncoders.length;
		return distanceAvg >= distance;
	}

	@Override
	protected void end() {
		LogKitten.v("Finished traveling " + distance + " units (as set by setDistancePerPulse)");
	}
	
	@Override
	protected void interrupted() {
		LogKitten.w("Interrupted! " + getName() + " is in undefined location.");
	}
}