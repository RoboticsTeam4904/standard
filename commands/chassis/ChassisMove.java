package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorSensorSet;
import org.usfirst.frc4904.standard.commands.motor.MotorSet;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.sensormotor.EncodedMotor;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command moves the chassis.
 * It move based on the driver class.
 * Note that it supports all types
 * of chassis. The chassis is used
 * to calculate the motor movement.
 * The command works by creating
 * a movement command for each
 * motor. This is the best way to
 * handle this because it allows
 * each motor to be a full subsystem.
 *
 */
public class ChassisMove extends CommandGroup {
	private final MotorSet[] motorSpins;
	private double[] motorSpeeds;
	private final Chassis chassis;
	private final ChassisController controller;
	private final double xScale;
	private final double yScale;
	private final double turnScale;
	
	// private final LogKitten logger;
	/**
	 * Constructor.
	 * 
	 * @param chassis
	 *        The robot's chassis.
	 * @param controller
	 *        The currently selected driver.
	 * @param xScale
	 *        The scale factor for the x axis.
	 * @param yScale
	 *        The scale factor for the y axis.
	 * @param turnScale
	 *        The scale factor for the turning.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, double xScale, double yScale, double turnScale) {
		super("ChassisMove");
		requires(chassis);
		this.chassis = chassis;
		this.controller = controller;
		Motor[] motors = this.chassis.getMotors();
		this.motorSpins = new MotorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			motorSpins[i] = new MotorSet(motors[i]);
			addParallel(motorSpins[i]);
		}
		this.xScale = xScale;
		this.yScale = yScale;
		this.turnScale = turnScale;
		LogKitten.v("ChassisMove created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
	}
	
	public ChassisMove(Chassis chassis, ChassisController controller) {
		this(chassis, controller, 1.0, 1.0, 1.0);
	}
	
	/**
	 * Constructor supporting encoded motors.
	 * If this is done, the motors will be
	 * controlled as encoded motors, i.e.
	 * they will try to maintain a more
	 * precise speed.
	 * If motors do not have encoders,
	 * they will simply be treated as
	 * normal motors.
	 * 
	 * @param chassis
	 * @param controller
	 * @param xScale
	 * @param yScale
	 * @param turnScale
	 * @param encode
	 *        True to enable encoders, false to disable.
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, double xScale, double yScale, double turnScale, boolean encode) {
		super("ChassisMoveEncodeded");
		requires(chassis);
		this.chassis = chassis;
		this.controller = controller;
		Motor[] motors = this.chassis.getMotors();
		this.motorSpins = new MotorSensorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] instanceof EncodedMotor && encode) {
				EncodedMotor motor = (EncodedMotor) motors[i];
				motorSpins[i] = new MotorSensorSet(motor);
			} else {
				motorSpins[i] = new MotorSet(motors[i]);
			}
			addParallel(motorSpins[i]);
		}
		this.xScale = xScale;
		this.yScale = yScale;
		this.turnScale = turnScale;
		LogKitten.v("ChassisMove created for " + Integer.toString(chassis.getNumberWheels()) + " wheels");
	}
	
	public ChassisMove(Chassis chassis, ChassisController controller, boolean encode) {
		this(chassis, controller, 1.0, 1.0, 1.0, encode);
	}
	
	protected void initialize() {
		LogKitten.v("ChassisMove initialized");
	}
	
	protected void execute() {
		chassis.move2dc(controller.getX(), controller.getY(), controller.getTurnSpeed());
		motorSpeeds = chassis.getMotorSpeeds();
		String motorSpeedsString = "";
		for (int i = 0; i < motorSpins.length; i++) {
			motorSpins[i].set(motorSpeeds[i]);
			motorSpeedsString += Double.toString(motorSpeeds[i]) + " ";
		}
		LogKitten.d("ChassisMove executing");
		LogKitten.d("Motor speeds: " + motorSpeedsString);
	}
	
	protected void end() {
		LogKitten.v("ChassisMove ended");
	}
	
	protected void interrupted() {
		LogKitten.w("ChassisMove interrupted");
	}
	
	protected boolean isFinished() {
		return false;
	}
}
