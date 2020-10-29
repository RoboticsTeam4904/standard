package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChassisMoveDistance extends CommandBase implements ChassisController {
	protected final ChassisMove chassisMove;
	protected final MotionController motionController;
	protected final Command fallbackCommand;
	protected final double distance;
	protected boolean runOnce;

	/**
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. The speed is decided by the provided motionController.
	 *
	 * @param name
	 * @param chassis
	 * @param distance         distance to move in encoder ticks
	 * @param motionController
	 * @param fallbackCommand  If the sensor fails for some reason, this command
	 *                         will be cancelled, then the fallbackCommand will
	 *                         start
	 * @param encoders
	 */
	public ChassisMoveDistance(String name, Chassis chassis, double distance, MotionController motionController,
			Command fallbackCommand) {
		chassisMove = new ChassisMove(chassis, this, false);
		this.motionController = motionController;
		this.distance = distance;
		this.fallbackCommand = fallbackCommand;
		runOnce = false;
		setName(name);
		addRequirements(chassis);
	}

	/**
	 * 
	 * Constructor. This command moves the chassis forward a known distance via a
	 * set of encoders. The distance is calculated as the average of the provided
	 * encoders. The speed is decided by the provided motionController.
	 *
	 * @param chassis
	 * @param distance         distance to move in encoder ticks
	 * @param motionController
	 * @param encoders
	 * @param name
	 */
	public ChassisMoveDistance(String name, Chassis chassis, double distance, MotionController motionController) {
		this(name, chassis, distance, motionController, null);
	}

	/**
	 * 
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
		this("ChassisMoveDistance", chassis, distance, motionController);
	}

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
		this("ChassisMoveDistance", chassis, distance, motionController, fallbackCommand);
	}

	@Override
	public void initialize() {
		chassisMove.schedule();
		try {
			motionController.resetSafely();
		} catch (InvalidSensorException e) {
			LogKitten.w("Cancelling ChassisMoveDistance with InvalidSensorException");
			chassisMove.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.schedule();
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
			LogKitten.w("Cancelling ChassisMoveDistance with InvalidSensorException");
			chassisMove.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.schedule();
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
	public boolean isFinished() {
		if (chassisMove.isScheduled() && !runOnce) {
			runOnce = true;
		}
		return (motionController.onTarget() || !chassisMove.isScheduled()) && runOnce;
	}
}
