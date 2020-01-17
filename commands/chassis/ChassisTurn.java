package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChassisTurn extends CommandBase implements ChassisController {
	protected final ChassisMove move;
	protected double initialAngle;
	protected final double finalAngle;
	protected final MotionController motionController;
	protected final CommandBase fallbackCommand;
	protected final IMU imu;
	protected boolean runOnce;

	/**
	 * 
	 * Constructor This command rotates the chassis to a position relative to the
	 * current angle of the robot
	 *
	 * @param name
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param fallbackCommand  If the sensor fails for some reason, this command
	 *                         will be cancelled, then the fallbackCommand will
	 *                         start
	 * @param motionController
	 */
	public ChassisTurn(String name, final Chassis chassis, final double finalAngle, final IMU imu,
			final CommandBase fallbackCommand, MotionController motionController) {
		move = new ChassisMove(chassis, this);
		this.finalAngle = -((finalAngle + 360) % 360 - 180);
		this.imu = imu;
		this.motionController = motionController;
		this.fallbackCommand = fallbackCommand;
		setName(name);
	}

	/**
	 * 
	 * Constructor This command rotates the chassis to a position relative to the
	 * current angle of the robot
	 *
	 * @param name
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param motionController
	 */
	public ChassisTurn(String name, Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		this(name, chassis, finalAngle, imu, null, motionController);
	}

	/**
	 * 
	 * Constructor This command rotates the chassis to a position relative to the
	 * current angle of the robot
	 *
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param motionController
	 */
	public ChassisTurn(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		this("Chassis Turn", chassis, finalAngle, imu, null, motionController);
	}

	/**
	 * 
	 * Constructor This command rotates the chassis to a position relative to the
	 * current angle of the robot
	 *
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param fallbackCommand  If the sensor fails for some reason, this command
	 *                         will be cancelled, then the fallbackCommand will
	 *                         start
	 * @param motionController
	 */
	public ChassisTurn(Chassis chassis, double finalAngle, IMU imu, CommandBase fallbackCommand,
			MotionController motionController) {
		this("Chassis Turn", chassis, finalAngle, imu, fallbackCommand, motionController);
	}

	@Override
	public double getX() {
		return 0.0;
	}

	@Override
	public double getY() {
		return 0.0;
	}

	@Override
	public double getTurnSpeed() {
		try {
			return motionController.getSafely();
		} catch (final InvalidSensorException e) {
			move.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.schedule();
			}
			return 0;
		}
	}

	@Override
	public void initialize() {
		move.schedule();
		initialAngle = imu.getYaw();
		try {
			motionController.resetSafely();
		} catch (final InvalidSensorException e) {
			move.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.schedule();
			}
			return;
		}
	}

	@Override
	public void execute() {
		motionController.setSetpoint(((finalAngle + initialAngle) + 360) % 360 - 180);
		if (!motionController.isEnabled()) {
			motionController.enable();
		}
	}

	@Override
	public boolean isFinished() {
		if (move.isScheduled() && !runOnce) {
			runOnce = true;
		}
		return (motionController.onTarget() || !move.isScheduled()) && runOnce;
	}

	@Override
	public void end(boolean interrupted) {
		motionController.disable();
		move.cancel();

		if (fallbackCommand != null && fallbackCommand.isScheduled()) {
			fallbackCommand.cancel();
		}
		runOnce = false;
	}
}