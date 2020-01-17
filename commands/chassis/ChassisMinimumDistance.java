package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;

public class ChassisMinimumDistance extends ChassisConstant {
	protected CustomEncoder[] encoders;
	protected final ChassisConstant fallbackCommand;
	protected double distance;
	protected double[] initialDistances;

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. Control is purely bang (as opposed to bang-bang/pid). The chassis
	 * will move at the speed until it passes the point, then will stop.
	 *
	 * @param name
	 * @param chassis
	 * @param distance        distance to move in encoder ticks
	 * @param speed           the speed to move at
	 * @param fallbackCommand If the sensor fails for some reason, this command will
	 *                        be cancelled, then the fallbackCommand will start
	 * @param encoders
	 */
	public ChassisMinimumDistance(String name, Chassis chassis, double distance, double speed,
			ChassisConstant fallbackCommand, CustomEncoder... encoders) {
		super(name, chassis, 0.0, speed, 0.0, Double.MAX_VALUE);
		this.encoders = encoders;
		this.distance = distance;
		this.fallbackCommand = fallbackCommand;
		initialDistances = new double[encoders.length];
	}

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. Control is purely bang (as opposed to bang-bang/pid). The chassis
	 * will move at the speed until it passes the point, then will stop.
	 *
	 * @param chassis
	 * @param distance        distance to move in encoder ticks
	 * @param speed           the speed to move at
	 * @param fallbackCommand If the sensor fails for some reason, this command will
	 *                        be cancelled, then the fallbackCommand will start
	 * @param encoders
	 */
	public ChassisMinimumDistance(Chassis chassis, double distance, double speed, ChassisConstant fallbackCommand,
			CustomEncoder... encoders) {
		this("Chassis Minimum Distance", chassis, distance, speed, fallbackCommand, encoders);
	}

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. Control is purely bang (as opposed to bang-bang/pid). The chassis
	 * will move at the speed until it passes the point, then will stop.
	 *
	 * @param name
	 * @param chassis
	 * @param distance distance to move in encoder ticks
	 * @param speed    the speed to move at
	 * @param encoders
	 */
	public ChassisMinimumDistance(String name, Chassis chassis, double distance, double speed,
			CustomEncoder... encoders) {
		this(name, chassis, distance, speed, null, encoders);
	}

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. Control is purely bang (as opposed to bang-bang/pid). The chassis
	 * will move at the speed until it passes the point, then will stop.
	 *
	 * @param chassis
	 * @param distance distance to move in encoder ticks
	 * @param speed    the speed to move at
	 * @param encoders
	 */
	public ChassisMinimumDistance(Chassis chassis, double distance, double speed, CustomEncoder... encoders) {
		this(chassis, distance, speed, null, encoders);
	}

	@Override
	public void initialize() {
		super.initialize();
		for (int i = 0; i < encoders.length; i++) {
			try {
				initialDistances[i] = encoders[i].getDistanceSafely();
			} catch (InvalidSensorException e) {
				cancel();
				return;
			}
		}
	}

	@Override
	public boolean isFinished() {
		double distanceSum = 0;
		for (int i = 0; i < encoders.length; i++) {
			try {
				distanceSum += encoders[i].getDistanceSafely() - initialDistances[i];
				LogKitten.d("Encoder " + i + " reads " + encoders[i].getDistanceSafely() + " out of " + distance);
			} catch (InvalidSensorException e) {
				cancel();
				if (fallbackCommand != null) {
					fallbackCommand.schedule();
				}
				return true;
			}
		}

		return distanceSum / (double) encoders.length >= distance;
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		if (interrupted) {
			LogKitten.w("Interrupted! " + getName() + " is in undefined location.");
		} else {
			LogKitten.v("Finished traveling " + distance + " units (as set by setDistancePerPulse)");
		}
	}
}