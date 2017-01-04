package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.CustomEncoder;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class ChassisMotionProfileDistance extends Command implements ChassisController {
	protected final ChassisMove chassisMove;
	protected final MotionController motionController;
	protected final ChassisController fallbackController;
	protected final double distance;
	protected final CustomEncoder[] encoders;

	/**
	 * Constructor.
	 * This command moves the chassis forward a known distance via a set of encoders.
	 * The distance is calculated as the average of the provided encoders.
	 * The speed is decided by the provided motionController.
	 *
	 * @param chassis
	 * @param distance
	 *        distance to move in encoder ticks
	 * @param motionController
	 * @param fallbackCommand
	 *        If the sensor fails for some reason, this command will be cancelled, then the fallbackCommand will start
	 * @param encoders
	 */
	public ChassisMotionProfileDistance(Chassis chassis, double distance, MotionController motionController, ChassisController fallbackController, CustomEncoder... encoders) {
		chassisMove = new ChassisMove(chassis, this, false);
		this.motionController = motionController;
		this.encoders = encoders;
		this.distance = distance;
		this.fallbackController = fallbackController;
	}
	
	/**
	 * Constructor.
	 * This command moves the chassis forward a known distance via a set of encoders.
	 * The distance is calculated as the average of the provided encoders.
	 * The speed is decided by the provided motionController.
	 *
	 * @param chassis
	 * @param distance
	 *        distance to move in encoder ticks
	 * @param motionController
	 * @param encoders
	 */
	public ChassisMotionProfileDistance(Chassis chassis, double distance, MotionController motionController, CustomEncoder... encoders) {
		this(chassis, distance, motionController, null, encoders);
	}

	@Override
	public void initialize() {
		chassisMove.start();
		motionController.reset();
		for (CustomEncoder encoder : encoders) {
			encoder.reset();
		}
		motionController.setSetpoint(distance);
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		double speed;
		try {
			speed = motionController.get();
		}
		catch (InvalidSensorException e) {
			if (fallbackController != null) {
				speed = fallbackController.getY();
			} else {
				cancel();
				speed = 0;
			}
		}
		LogKitten.d("MotionProfileSpeed: " + speed);
		return speed;
	}

	@Override
	public double getTurnSpeed() {
		return 0;
	}

	@Override
	protected void end() {
		chassisMove.cancel();
	}

	@Override
	protected void execute() {}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return motionController.onTarget() || !chassisMove.isRunning();
	}
}
