package org.usfirst.frc4904.standard.commands.chassis;

import java.util.*;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ChassisMoveDistance implements Command, ChassisController {
	protected final ChassisMove chassisMove;
	protected final MotionController motionController;
	protected final Command fallbackCommand;
	protected final double distance;
	protected final Chassis chassis;
	protected boolean runOnce;

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. The speed is decided by the provided motionController.
	 *
	 * @param chassis
	 * @param distance         distance to move in encoder ticks
	 * @param motionController
	 * @param fallbackCommand  If the sensor fails for some reason, this command
	 *                         will be cancelled, then the fallbackCommand will
	 *                         start
	 * @param encoders
	 */
	public ChassisMoveDistance(Chassis chassis, double distance, MotionController motionController,
			Command fallbackCommand) {
		chassisMove = new ChassisMove(chassis, this, false);
		this.motionController = motionController;
		this.distance = distance;
		this.fallbackCommand = fallbackCommand;
		this.chassis = chassis;
		runOnce = false;
	}

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. The speed is decided by the provided motionController.
	 *
	 * @param chassis
	 * @param distance         distance to move in encoder ticks
	 * @param motionController
	 * @param encoders
	 */
	public ChassisMoveDistance(Chassis chassis, double distance, MotionController motionController) {
		this(chassis, distance, motionController, null);
	}

	@Override
	public Set<Subsystem> getRequirements() {
		HashSet<Subsystem> set = new HashSet<Subsystem>();
		set.add(this.chassis);
		return set;
	}

	@Override
	public void initialize() {
		chassisMove.initialize();
		try {
			motionController.resetSafely();
		} catch (InvalidSensorException e) {
			LogKitten.w("Cancelling ChassisMoveDistance");
			chassisMove.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.initialize();
			}
			return;
		}
		motionController.setSetpoint(motionController.getSensorValue() + distance);
		motionController.enable();
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		double speed;
		try {
			speed = motionController.getSafely();
		} catch (InvalidSensorException e) {
			LogKitten.w("Cancelling ChassisMoveDistance");
			chassisMove.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.initialize();
			}
			speed = 0;
		}
		LogKitten.d("MotionProfileSpeed: " + speed);
		return speed;
	}

	@Override
	public double getTurnSpeed() {
		return 0;
	}

	@Override
	public void end(boolean interrupted) {
		chassisMove.cancel();
		motionController.disable();
		motionController.reset();
		runOnce = false;
	}

	@Override
	public void execute() {
	}

	/*
	 * @Override protected void interrupted() { end(); }
	 */
	@Override
	public boolean isFinished() {
		if (chassisMove.isScheduled() && !runOnce) {
			runOnce = true;
		}
		return (motionController.onTarget() || !chassisMove.isScheduled()) && runOnce;
	}
}
