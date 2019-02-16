package org.usfirst.frc4904.standard.commands.chassis;


import java.util.function.DoubleSupplier;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisTurn extends Command implements ChassisController {
	protected final ChassisMove move;
	protected double initialAngle;
	protected double finalAngle;
	protected DoubleSupplier finalAngleSupplier;
	protected final MotionController motionController;
	protected final Command fallbackCommand;
	protected final IMU imu;
	protected boolean runOnce;

	/**
	 * Constructor
	 * This command rotates the chassis to a position relative to the current angle of the robot
	 *
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param fallbackCommand
	 *                         If the sensor fails for some reason, this command will be cancelled, then the fallbackCommand will start
	 * @param motionController
	 */
	public ChassisTurn(Chassis chassis, double finalAngle, IMU imu, Command fallbackCommand,
		MotionController motionController) {
		move = new ChassisMove(chassis, this);
		this.finalAngle = -((finalAngle + 360) % 360 - 180);
		this.imu = imu;
		this.motionController = motionController;
		this.fallbackCommand = fallbackCommand;
	}

	/**
	 * Constructor
	 * This command rotates the chassis to a position relative to the current angle of the robot
	 *
	 * @param chassis
	 * @param finalAngle
	 * @param imu
	 * @param motionController
	 */
	public ChassisTurn(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
		this(chassis, finalAngle, imu, null, motionController);
	}

	/**
	 * Constructor
	 * This command rotates the chassis to a position relative to the current angle of the robot
	 * It takes in a double supplier rather than a double for the angle
	 * This is useful for when final angle won't be known until the command is intiliazed
	 *
	 * @param chassis
	 * @param finalAngleSupplier
	 * @param imu
	 * @param fallbackCommand
	 *                         If the sensor fails for some reason, this command will be cancelled, then the fallbackCommand will start
	 * @param motionController
	 */
	public ChassisTurn(Chassis chassis, DoubleSupplier finalAngleSupplier, IMU imu, Command fallbackCommand, MotionController motionController) {
		move = new ChassisMove(chassis, this);
		this.finalAngleSupplier = finalAngleSupplier;
		this.imu = imu;
		this.motionController = motionController;
		this.fallbackCommand = fallbackCommand;
	}

	/**
	 * Constructor
	 * This command rotates the chassis to a position relative to the current angle of the robot
	 * It takes in a double supplier rather than a double for the angle
	 * This is useful for when final angle won't be known until the command is intiliazed
	 *
	 * @param chassis
	 * @param finalAngleSupplier
	 * @param imu
	 * @param motionController
	 */
	public ChassisTurn(Chassis chassis, DoubleSupplier finalAngleSupplier, IMU imu, MotionController motionController) {
		this(chassis, finalAngleSupplier, imu, null, motionController);
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
		}
		catch (InvalidSensorException e) {
			move.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.start();
			}
			return 0;
		}
	}

	@Override
	protected void initialize() {
		if (finalAngleSupplier != null) {
			finalAngle = finalAngleSupplier.getAsDouble();
		}
		move.start();
		initialAngle = imu.getYaw();
		try {
			motionController.resetSafely();
		}
		catch (InvalidSensorException e) {
			move.cancel();
			cancel();
			if (fallbackCommand != null) {
				fallbackCommand.start();
			}
			return;
		}
	}

	@Override
	protected void execute() {
		motionController.setSetpoint(((finalAngle + initialAngle) + 360) % 360 - 180);
		if (!motionController.isEnabled()) {
			motionController.enable();
		}
	}

	@Override
	protected boolean isFinished() {
		if (move.isRunning() && !runOnce) {
			runOnce = true;
		}
		return (motionController.onTarget() || !move.isRunning()) && runOnce;
	}

	@Override
	protected void end() {
		motionController.disable();
		move.cancel();
		// Please just die in a hole and never come back
		if (fallbackCommand != null && fallbackCommand.isRunning()) {
			fallbackCommand.cancel();
		}
		runOnce = false;
	}

	@Override
	protected void interrupted() {
		end();
	}
}