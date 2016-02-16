package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorSet;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.SensorMotor;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command moves the chassis.
 * It move based on the driver class.
 * Note that it supports all types of chassis.
 * The chassis is used to calculate the motor movement.
 * The command works by creating a movement command for each motor.
 * This is the best way to handle this because it allows each motor to be a full subsystem.
 */
public class ChassisMove extends CommandGroup {
	private final MotorSet[] motorSpins;
	private double[] motorSpeeds;
	private final Chassis chassis;
	private final ChassisController controller;
	private final double xScale;
	private final double yScale;
	private final double turnScale;
	
	/**
	 * @param chassis
	 *        The robot's Chassis.
	 * @param controller
	 *        A ChassisController to control the Chassis, such as a Driver or autonomous routine.
	 * @param xScale
	 *        The scale factor for the x axis.
	 * @param yScale
	 *        The scale factor for the y axis.
	 * @param turnScale
	 *        The scale factor for the turning.
	 * @param usePID
	 *        Whether to enable PID using any SensorMotors that Chassis has.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, double xScale, double yScale, double turnScale, boolean usePID) {
		super("ChassisMove");
		requires(chassis);
		this.chassis = chassis;
		this.controller = controller;
		Motor[] motors = this.chassis.getMotors();
		motorSpins = new MotorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] instanceof SensorMotor) {
				if (usePID) {
					((SensorMotor) motors[i]).enablePID();
				} else {
					((SensorMotor) motors[i]).disablePID();
				}
			}
			motorSpins[i] = new MotorSet(motors[i]);
			addParallel(motorSpins[i]);
		}
		this.xScale = xScale;
		this.yScale = yScale;
		this.turnScale = turnScale;
		LogKitten.v("ChassisMove created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
	}
	
	/**
	 * @param chassis
	 *        The robot's chassis.
	 * @param controller
	 *        A ChassisController to control the Chassis, such as a Driver or autonomous routine.
	 * @param xScale
	 *        The scale factor for the x axis.
	 * @param yScale
	 *        The scale factor for the y axis.
	 * @param turnScale
	 *        The scale factor for the turning.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, double xScale, double yScale, double turnScale) {
		this(chassis, controller, xScale, yScale, turnScale, false);
	}
	
	/**
	 * @param chassis
	 *        The robot's chassis.
	 * @param controller
	 *        A ChassisController to control the Chassis, such as a Driver or autonomous routine.
	 * @param usePID
	 *        Whether to enable PID using any SensorMotors that Chassis has.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, boolean usePID) {
		this(chassis, controller, 1.0, 1.0, 1.0, usePID);
	}
	
	/**
	 * @param chassis
	 *        The robot's chassis.
	 * @param controller
	 *        A ChassisController to control the Chassis, such as a Driver or autonomous routine.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller) {
		this(chassis, controller, 1.0, 1.0, 1.0, false);
	}
	
	@Override
	protected void initialize() {
		LogKitten.v("ChassisMove initialized");
	}
	
	@Override
	protected void execute() {
		chassis.move2dc(controller.getX() * xScale, controller.getY() * yScale, controller.getTurnSpeed() * turnScale);
		motorSpeeds = chassis.getMotorSpeeds();
		String motorSpeedsString = "";
		for (int i = 0; i < motorSpins.length; i++) {
			LogKitten.d(Double.toString(motorSpeeds[i]));
			motorSpins[i].set(motorSpeeds[i]);
			motorSpeedsString += Double.toString(motorSpeeds[i]) + " ";
		}
		LogKitten.d("ChassisMove executing");
		LogKitten.d("Motor speeds: " + motorSpeedsString);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		LogKitten.v("ChassisMove ended");
	}
	
	@Override
	protected void interrupted() {
		LogKitten.w("ChassisMove interrupted");
	}
}
