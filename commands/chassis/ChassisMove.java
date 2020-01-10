package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.commands.motor.MotorSet;
import org.usfirst.frc4904.standard.custom.ChassisController;
import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.VelocitySensorMotor;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * 
 * This command moves the chassis. It move based on the driver class. Note that
 * it supports all types of chassis. The chassis is used to calculate the motor
 * movement. The command works by creating a movement command for each motor.
 * This is the best way to handle this because it allows each motor to be a full
 * subsystem. ``````¶0````1¶1_```````````````````````````````````````
 * ```````¶¶¶0_`_¶¶¶0011100¶¶¶¶¶¶¶001_````````````````````
 * ````````¶¶¶¶¶00¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶0_````````````````
 * `````1_``¶¶00¶0000000000000000000000¶¶¶¶0_`````````````
 * `````_¶¶_`0¶000000000000000000000000000¶¶¶¶¶1``````````
 * ```````¶¶¶00¶00000000000000000000000000000¶¶¶_`````````
 * ````````_¶¶00000000000000000000¶¶00000000000¶¶`````````
 * `````_0011¶¶¶¶¶000000000000¶¶00¶¶0¶¶00000000¶¶_````````
 * ```````_¶¶¶¶¶¶¶00000000000¶¶¶¶0¶¶¶¶¶00000000¶¶1````````
 * ``````````1¶¶¶¶¶000000¶¶0¶¶¶¶¶¶¶¶¶¶¶¶0000000¶¶¶````````
 * ```````````¶¶¶0¶000¶00¶0¶¶`_____`__1¶0¶¶00¶00¶¶````````
 * ```````````¶¶¶¶¶00¶00¶10¶0``_1111_`_¶¶0000¶0¶¶¶````````
 * ``````````1¶¶¶¶¶00¶0¶¶_¶¶1`_¶_1_0_`1¶¶_0¶0¶¶0¶¶````````
 * ````````1¶¶¶¶¶¶¶0¶¶0¶0_0¶``100111``_¶1_0¶0¶¶_1¶````````
 * ```````1¶¶¶¶00¶¶¶¶¶¶¶010¶``1111111_0¶11¶¶¶¶¶_10````````
 * ```````0¶¶¶¶__10¶¶¶¶¶100¶¶¶0111110¶¶¶1__¶¶¶¶`__````````
 * ```````¶¶¶¶0`__0¶¶0¶¶_¶¶¶_11````_0¶¶0`_1¶¶¶¶```````````
 * ```````¶¶¶00`__0¶¶_00`_0_``````````1_``¶0¶¶_```````````
 * ``````1¶1``¶¶``1¶¶_11``````````````````¶`¶¶````````````
 * ``````1_``¶0_¶1`0¶_`_``````````_``````1_`¶1````````````
 * ``````````_`1¶00¶¶_````_````__`1`````__`_¶`````````````
 * ````````````¶1`0¶¶_`````````_11_`````_``_``````````````
 * `````````¶¶¶¶000¶¶_1```````_____```_1``````````````````
 * `````````¶¶¶¶¶¶¶¶¶¶¶¶0_``````_````_1111__``````````````
 * `````````¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶01_`````_11____1111_```````````
 * `````````¶¶0¶0¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1101_______11¶_```````````
 * ``````_¶¶¶0000000¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶0¶0¶¶¶1````````````
 * `````0¶¶0000000¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1`````````````
 * ````0¶0000000¶¶0_````_011_10¶110¶01_1¶¶¶0````_100¶001_`
 * ```1¶0000000¶0_``__`````````_`````````0¶_``_00¶¶010¶001
 * ```¶¶00000¶¶1``_01``_11____``1_``_`````¶¶0100¶1```_00¶1
 * ``1¶¶00000¶_``_¶_`_101_``_`__````__````_0000001100¶¶¶0`
 * ``¶¶¶0000¶1_`_¶``__0_``````_1````_1_````1¶¶¶0¶¶¶¶¶¶0```
 * `_¶¶¶¶00¶0___01_10¶_``__````1`````11___`1¶¶¶01_````````
 * `1¶¶¶¶¶0¶0`__01¶¶¶0````1_```11``___1_1__11¶000`````````
 * `1¶¶¶¶¶¶¶1_1_01__`01```_1```_1__1_11___1_``00¶1````````
 * ``¶¶¶¶¶¶¶0`__10__000````1____1____1___1_```10¶0_```````
 * ``0¶¶¶¶¶¶¶1___0000000```11___1__`_0111_```000¶01```````
 * ```¶¶¶00000¶¶¶¶¶¶¶¶¶01___1___00_1¶¶¶`_``1¶¶10¶¶0```````
 * ```1010000¶000¶¶0100_11__1011000¶¶0¶1_10¶¶¶_0¶¶00``````
 * 10¶000000000¶0________0¶000000¶¶0000¶¶¶¶000_0¶0¶00`````
 * ¶¶¶¶¶¶0000¶¶¶¶_`___`_0¶¶¶¶¶¶¶00000000000000_0¶00¶01````
 * ¶¶¶¶¶0¶¶¶¶¶¶¶¶¶_``_1¶¶¶00000000000000000000_0¶000¶01```
 * 1__```1¶¶¶¶¶¶¶¶¶00¶¶¶¶00000000000000000000¶_0¶0000¶0_``
 * ```````¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶00000000000000000000010¶00000¶¶_`
 * ```````0¶¶¶¶¶¶¶¶¶¶¶¶¶¶00000000000000000000¶10¶¶0¶¶¶¶¶0`
 * ````````¶¶¶¶¶¶¶¶¶¶0¶¶¶00000000000000000000010¶¶¶0011```
 * ````````1¶¶¶¶¶¶¶¶¶¶0¶¶¶0000000000000000000¶100__1_`````
 * `````````¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶000000000000000000¶11``_1``````
 * `````````1¶¶¶¶¶¶¶¶¶¶¶0¶¶¶00000000000000000¶11___1_`````
 * ``````````¶¶¶¶¶¶0¶0¶¶¶¶¶¶¶0000000000000000¶11__``1_````
 * ``````````¶¶¶¶¶¶¶0¶¶¶0¶¶¶¶¶000000000000000¶1__````__```
 * ``````````¶¶¶¶¶¶¶¶0¶¶¶¶¶¶¶¶¶0000000000000000__`````11``
 * `````````_¶¶¶¶¶¶¶¶¶000¶¶¶¶¶¶¶¶000000000000011_``_1¶¶¶0`
 * `````````_¶¶¶¶¶¶0¶¶000000¶¶¶¶¶¶¶000000000000100¶¶¶¶0_`_
 * `````````1¶¶¶¶¶0¶¶¶000000000¶¶¶¶¶¶000000000¶00¶¶01`````
 * `````````¶¶¶¶¶0¶0¶¶¶0000000000000¶0¶00000000011_``````_
 * ````````1¶¶0¶¶¶0¶¶¶¶¶¶¶000000000000000000000¶11___11111
 * ````````¶¶¶¶0¶¶¶¶¶00¶¶¶¶¶¶000000000000000000¶011111111_
 * ```````_¶¶¶¶¶¶¶¶¶0000000¶0¶00000000000000000¶01_1111111
 * ```````0¶¶¶¶¶¶¶¶¶000000000000000000000000000¶01___`````
 * ```````¶¶¶¶¶¶0¶¶¶000000000000000000000000000¶01___1````
 * ``````_¶¶¶¶¶¶¶¶¶00000000000000000000000000000011_111```
 * ``````0¶¶0¶¶¶0¶¶0000000000000000000000000000¶01`1_11_``
 * ``````¶¶¶¶¶¶0¶¶¶0000000000000000000000000000001`_0_11_`
 * ``````¶¶¶¶¶¶¶¶¶00000000000000000000000000000¶01``_0_11`
 * ``````¶¶¶¶0¶¶¶¶00000000000000000000000000000001```_1_11
 */
public class ChassisMove extends ParallelCommandGroup {
	protected final MotorSet[] motorSpins;
	protected double[] motorSpeeds;
	protected final Motor[] motors;
	protected final boolean usePID;
	protected final Chassis chassis;
	protected final ChassisController controller;

	/**
	 *
	 * 
	 * @param chassis    The robot's Chassis.
	 * @param controller A ChassisController to control the Chassis, such as a
	 *                   Driver or autonomous routine.
	 * @param usePID     Whether to enable PID using any SensorMotors that Chassis
	 *                   has.
	 * 
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, boolean usePID, String name) {
		this.controller = controller;
		this.usePID = usePID;
		motors = chassis.getMotors();
		motorSpins = new MotorSet[motors.length];
		for (int i = 0; i < motors.length; i++) {
			motorSpins[i] = new MotorSet(motors[i]);
			addCommands(motorSpins[i]);
		}
		addRequirements(chassis);
		setName(name);
	}

	/**
	 * 
	 * 
	 * @param chassis    The robot's chassis.
	 * @param controller A ChassisController to control the Chassis, such as a
	 *                   Driver or autonomous routine.
	 * @param name
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, String name) {
		this(chassis, controller, false, name);
	}

	/**
	 *
	 * 
	 * @param chassis    The robot's Chassis.
	 * @param controller A ChassisController to control the Chassis, such as a
	 *                   Driver or autonomous routine.
	 * @param usePID     Whether to enable PID using any SensorMotors that Chassis
	 *                   has.
	 * @param name
	 */
	public ChassisMove(Chassis chassis, ChassisController controller, boolean usePID) {
		this(chassis, controller, usePID, "Chassis Move");
	}

	/**
	 * 
	 * 
	 * @param chassis    The robot's chassis.
	 * @param controller A ChassisController to control the Chassis, such as a
	 *                   Driver or autonomous routine.
	 * 
	 */
	public ChassisMove(Chassis chassis, ChassisController controller) {
		this(chassis, controller, false, "Chassis Move");
	}

	/**
	 * 
	 * VelocitySensorMotors will attempt to very precisely achieve the speed set by
	 * this command when PID is enabled PositionSensorMotors will either attempt to
	 * maintain their previous position, or worse, will try to move to somewhere
	 * between -1.0 and 1.0, which is probably not the correct position regardless
	 * of the scaling.
	 * 
	 */
	@Override
	public void initialize() {
		for (Motor motor : motors) {
			if (motor instanceof VelocitySensorMotor) {

				if (usePID) {
					((VelocitySensorMotor) motor).enableMotionController();
				} else {
					((VelocitySensorMotor) motor).disableMotionController();
				}
			}
		}
		LogKitten.v("ChassisMove initialized");
	}

	@Override
	public void execute() {
		chassis.moveCartesian(controller.getX(), controller.getY(), controller.getTurnSpeed());
		motorSpeeds = chassis.getMotorSpeeds();
		StringBuilder motorSpeedsString = new StringBuilder();
		motorSpeedsString.append("Motor speeds:");
		for (int i = 0; i < motorSpins.length; i++) {
			LogKitten.d(Double.toString(motorSpeeds[i]));
			motorSpins[i].set(motorSpeeds[i]);
			motorSpeedsString.append(' ');
			motorSpeedsString.append(motorSpeeds[i]);
		}
		LogKitten.d("ChassisMove executing");
		LogKitten.d(motorSpeedsString.toString());
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	/**
	 * <p>
	 * It's important to stop motor spins before the ChassisMove command stops.
	 * Otherwise, the spins might briefly (for one tick) use the previously set
	 * values if the ChassisMove command is reused.
	 * </p>
	 */
	protected void stopMotorSpins() {
		for (int i = 0; i < motors.length; i++) {
			motorSpins[i].set(0);
		}
	}

	@Override
	public void end(boolean interrupted) {
		stopMotorSpins();

		if (interrupted) {
			LogKitten.w("ChassisMove interrupted");
		} else {
			LogKitten.v("ChassisMove ended");
		}

	}
}
