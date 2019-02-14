package org.usfirst.frc4904.standard.commands.systemchecks.motor;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.commands.systemchecks.SubsystemCheck;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * SystemCheck of motor health
 */
public class MotorCheck extends SubsystemCheck {
	protected static final double DEFAULT_SPEED = 0.5;
	protected static final Util.Range outputCurrentRange = new Util.Range(0.1, 0.3); // TODO: Test this range
	protected static final Util.Range voltageRange = new Util.Range(11.5, 14); // TODO: Test this range
	protected double speed;
	protected final Motor[] motors;

	/**
	 * SystemCheck of motor health
	 * 
	 * @param name
	 *                Name of check
	 * @param timeout
	 *                Duration of check
	 * @param speed
	 *                Speed of motors
	 * @param motors
	 *                Motors to test
	 */
	public MotorCheck(String name, double timeout, double speed, Motor... motors) {
		super(name, timeout, motors);
		this.motors = motors;
		this.speed = speed;
	}

	/**
	 * SystemCheck of motor health
	 * 
	 * @param name
	 *               Name of check
	 * @param speed
	 *               Speed of motors
	 * @param motors
	 *               Motors to test
	 */
	public MotorCheck(String name, double speed, Motor... motors) {
		this(name, DEFAULT_TIMEOUT, speed, motors);
	}

	/**
	 * SystemCheck of motor health
	 * 
	 * @param timeout
	 *                Duration of check
	 * @param speed
	 *                Speed of motors
	 * @param motors
	 *                Motors to test
	 */
	public MotorCheck(double timeout, double speed, Motor... motors) {
		this("MotorCheck", timeout, speed, motors);
	}

	/**
	 * SystemCheck of motor health
	 * 
	 * @param speed
	 *               Speed of motors
	 * @param motors
	 *               Motors to test
	 */
	public MotorCheck(double speed, Motor... motors) {
		this(DEFAULT_TIMEOUT, speed, motors);
	}

	/**
	 * SystemCheck of motor health
	 * 
	 * @param name
	 *               Name of check
	 * @param motors
	 *               Motors to test
	 */
	public MotorCheck(String name, Motor... motors) {
		this(name, DEFAULT_SPEED, motors);
	}

	/**
	 * SystemCheck of motor health
	 * 
	 * @param motors
	 *               Motors to test
	 */
	public MotorCheck(Motor... motors) {
		this("MotorCheck", motors);
	}

	/**
	 * Sets motor to aforementioned speed
	 */
	public void initialize() {
		for (Motor motor : motors) {
			motor.set(speed);
		}
	}

	/**
	 * Checks if setting motors returns errors or if speedcontroller currents are not in acceptable range
	 */
	public void execute() {
		for (Motor motor : motors) {
			try {
				motor.set(speed);
			}
			catch (Exception e) {
				updateStatusFail(motor.getName(), e);
			}
			VICheck(motor);
		}
	}

	/**
	 * Checks voltage input and output current of a motor's speedcontrollers
	 */
	public void VICheck(Motor motor) {
		for (SpeedController controller : motor.getSpeedControllers()) {
			double current;
			double voltage;
			if (controller instanceof CANSparkMax) {
				current = ((CANSparkMax) controller).getOutputCurrent();
				voltage = ((CANSparkMax) controller).getBusVoltage();
			} else if (controller instanceof TalonSRX) {
				current = ((TalonSRX) controller).getOutputCurrent();
				voltage = ((TalonSRX) controller).getBusVoltage();
			} else {
				current = outputCurrentRange.getMax();
				voltage = voltageRange.getMax();
			}
			if (!outputCurrentRange.contains(current)) {
				updateStatusFail(motor.getName(), new Exception("MOTOR CURRENT NOT IN ACCEPTABLE RANGE"));
			}
			if (!voltageRange.contains(voltage)) {
				updateStatusFail(motor.getName(), new Exception("MOTOR VOLTAGE NOT IN ACCEPTABLE RANGE"));
			}
		}
	}
}