package org.usfirst.frc4904.standard.subsystems.motor;

import java.util.function.DoubleSupplier;
import java.util.stream.Stream;

import org.usfirst.frc4904.standard.custom.CustomCAN;
import org.usfirst.frc4904.standard.custom.motorcontrollers.CustomCANSparkMax;
import org.usfirst.frc4904.standard.custom.motorcontrollers.TalonMotorController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.revrobotics.REVLibError;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * A group of SparkMax motor controllers the modern motor controller features. 
 * 
 * Provided constructors:
 * - (name, speedmodifier=identity, neutralMode, deadband=0.01, limitswitches=false, voltageCompensation, leadMotor, followMotors)
 * - (name, SpeedModifier, neutralMode, neutralDeadbandPercent, respectLeadMotorLimitSwitches, voltageCompensation, leadMotor, followMotors)
 * 
 * Features included:
 * - Neutral mode
 * - Neutral deadband percent
 * - Hardwired, normally open limit switches 
 * - Voltage compensation
 * - Follow mode
 * 
 * Gotchas:
 * - Brake mode on a brushed motor will do nothing.
 * - When voltage has a physical meaning (eg. from a Ramsete controller), use voltageCompensation=0
 * - Configure normallyClosed limit switches manually on the underlying motorControllers. We usually use normallyOpen limit switches, so you probably don't need to do this.
 * - Specifying respectLeadMotorLimitSwitches=false will not disable the limit switches if they were previously enabled. This is to allow you to configure limit switches on the underlying motor controllers before passing them in if necessary.
 */
public class SparkMaxMotorSubsystem extends SmartMotorSubsystem<CustomCANSparkMax> {
  private final int configTimeoutMs = 50;  // milliseconds until the Talon gives up trying to configure
  private final int pid_idx = 0; // TODO: add support for auxillary pid
  private final int follow_motors_remote_filter_id = 0; // DONOT REMOVE, USED IN COMMENTED CODE BELOW; which filter (0 or 1) will be used to configure reading from the integrated encoder on the lead motor
  private SparkMaxPIDController controller = null;
  public final CustomCANSparkMax leadMotor;
  public final CustomCANSparkMax[] followMotors;

  // TODO: stator current limits? also makes brake mode stronger? https://www.chiefdelphi.com/t/programming-current-limiting-for-talonfx-java/371860
  // TODO: peak/nominal outputs
  // TODO: add voltage/slew limit to drivetrain motors because we don't want the pid to actively try to stop the motor (negative power) when the driver just lets go of the controls. diff ones for closed and open
  // TODO: control speed near soft limits, so that you can't go full throttle near the soft limit? impl as a speed modifier??

  /**
   * Motor Subsystem for a group of Talon motor controllers (Falcons, 775s).
   * Uses Talon-specific APIs like follow mode and motionProfile control mode to
   * offload work from the RoboRIO.
   *
   * You probably want to set inverted mode on the TalonMotorController using
   * FollowLeader or OpposeLeader
   *
   * @param name
   * @param speedModifier             
	 * @param idleMode                  Whether the motor should brake or coast
	 *                                  when the the output is near zero, or
	 *                                  .disable() or .stopMotor() are called.
	 * @param neutralDeadbandPercent    Power output range around zero to enable
	 *                                  neutralOutput (brake/coast mode) instead.
	 *                                  Can be in the range [0.001, 0.25], CTRE
	 *                                  default is 0.04. For more info, see
	 *                                  https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband
   * @param respectLeadMotorLimitSwitches Whether to enable the forward and
   *                                      reverse limit switches wired to the
   *                                      lead motor. This is the easiest way to
   *                                      use forward and reverse normally open
   *                                      limit switches (or just forward or
   *                                      just reverse, as not plugging a limit
   *                                      switch in is equivalent to a normally
   *                                      open switch being unpressed). NOTE
   *                                      This configuration is relatively
   *                                      limited, consider configuring limit
   *                                      switches manually on the
   *                                      MotorController for advanced
   *                                      configuration (eg. normally closed
   *                                      switches). NOTE passing false will not
   *                                      disable limit switches; just unplug
   *                                      them instead. 
   * @param voltageCompensation       0 to disable, 10 is a good default. Set
   *                                  the voltage corresponding to power=1.0
   *                                  This way, setting a power will lead to
   *                                  consistent output even when other
   *                                  components are running. Basically nerf all
   *                                  motors so that they have a consistent
   *                                  output. when the battery is low.
   * @param leadMotor
   * @param followMotors
   */
  public SparkMaxMotorSubsystem(String name, SpeedModifier speedModifier, IdleMode idleMode, double neutralDeadbandPercent,
                                Boolean respectLeadMotorLimitSwitches, double voltageCompensation,
                                CustomCANSparkMax leadMotor, CustomCANSparkMax... followMotors) {
		super(name, speedModifier, Stream.concat(Stream.of(leadMotor), Stream.of(followMotors)).toArray(CustomCANSparkMax[]::new));  // java has no spread operator, so you have to concat. best way i could find is to do it in a stream. please make this not bad if you know how 

    this.leadMotor = leadMotor;
    this.followMotors = followMotors;

    // limit switch configuration
    if (respectLeadMotorLimitSwitches) {
      // when extending to SparkMAX: you have to sparkmax.getForward/ReverseLimitSwitch.enable() or something. may need custom polling/plugin logic. https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax#getReverseLimitSwitch(com.revrobotics.SparkMaxLimitSwitch.Type)

      // TODO: spark max limit switches are untested
      var fwdLimit = leadMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
      var revLimit = leadMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
      REVLibThrowable.guard(fwdLimit.enableLimitSwitch(true));  // REVLibThrowable.guard will throw a runtime exception if the configuration fails.
      REVLibThrowable.guard(revLimit.enableLimitSwitch(true));
      // TODO: do following spark maxes need to be configured to use remote limit switch? or can they just auto-brake w/ a neutral-deadband equivalent? 
    } // no else { disableLimitSwitches() } here because we don't want to overwrite on false; user may be trying to use their own configuration.

    // other configuration (neutral mode, neutral deadband, voltagecomp)
    for (var motor : motors) {
      motor.setIdleMode(idleMode);
      //motor.configNeutralDeadband(neutralDeadbandPercent, configTimeoutMs); // TODO: no neutral deadband setting on sparkmax?
      if (voltageCompensation > 0) {
        motor.enableVoltageCompensation(voltageCompensation);
      } else {
        motor.disableVoltageCompensation();
      }
    }
    setFollowMode();
    // do we need to burnFlash? CD says it can wear out the EEPROM (which has limited write cycles) and also appears to be somewhat buggy. But it otherwise the motor config may get reset in case the motor gets power cycled (eg. during a brownout)?
	}
  /**
   * Motor Subsystem for a group of Talon motor controllers (Falcons, 775s).
   * Uses Talon-specific APIs like follow mode and motionProfile control mode to
   * offload work from the RoboRIO.
   *
   * You probably want to set inverted mode on the TalonMotorController using
   * FollowLeader or OpposeLeader
   *
   * Consider using the advanced constructor for limit switches and voltage
   * compensation, and using the .cfg...() methods for voltage ramping and
   * nominal output.
   * 
   * When using SparkMaxes:
   * - can limit switches be enabled/disabled? can you have a sparkmax respect a talon limit switch? https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax
   *
   * @param name
	 * @param idleMode                  Whether the motor should brake or coast
	 *                                  when the the output is near zero, or
	 *                                  .disable() or .stopMotor() are called.
   * @param voltageCompensation       0 to disable, 10 is a good default. Set
   *                                  the voltage corresponding to power=1.0
   *                                  This way, setting a power will lead to
   *                                  consistent output even when other
   *                                  components are running. Basically nerf all
   *                                  motors so that they have a consistent
   *                                  output when the battery is low.
   * @param leadMotor
   * @param followMotors
   */
  public SparkMaxMotorSubsystem(String name, IdleMode idleMode, double voltageCompensation,
                                CustomCANSparkMax leadMotor, CustomCANSparkMax... followMotors) {
		this(name, new IdentityModifier(), idleMode, 0.001, false, voltageCompensation, leadMotor, followMotors);
	}

  /**
   * Make all follow motors follow the lead motor.
   */
  private void setFollowMode() {
    for (var motor : this.followMotors) {
      motor.follow(leadMotor);
    }
  }

  // TODO the following methods are not thought out or documented
  /**
   * The F value provided here will be overwritten if provided to subsystem.leadMotor.set; note that if you do that, it will bypass the subystem requirements check
   * 
   * See docstrings on the methods used in the implementation for physical units
   */
  public void configPIDF(double p, double i, double d, double f) {
    // set sensor
    var sensor = leadMotor.getEncoder();
    sensor.setInverted(false);
    // docs says you need to sparkMax.setFeedbackDevice but that appears to not exist..
    // is this even needed for brushless? migration docs says it's not: https://docs.revrobotics.com/sparkmax/software-resources/migrating-ctre-to-rev#select-encoder

    // set pid constants
    controller = leadMotor.getPIDController();
    controller.setP(p);
    controller.setI(i);
    controller.setD(d);
    controller.setFF(f);
    // TODO: integral zone and outputRange? 
  }
  /**
   * 
   * @param setpointSupplier  a function that returns a double, units = RPM
   * 
   * @return the command to schedule
   */
  public Command c_controlVelocity(DoubleSupplier setpointSupplier) {
    if (controller == null) throw new IllegalArgumentException(name + " tried to use c_controlVelocity without first configPIDF()-ing.");
    return this.run(() -> controller.setReference(setpointSupplier.getAsDouble(), ControlType.kVelocity));
  }
  // public void zeroSensors() {
  //   // leadMotor.getSensorCollection // ?? doesn't exist; but it's in the CTRE falcon example
  //   // also should we zero the sensors on the follow motors just in case they're being used?
  // }
  
  // don't override disable() or stop() because we *should* indeed use the base implementation of disabling/stopping each motor controller individually. Otherwise the following motors will try to follow a disabled motor, which may cause unexpected behavior (although realistically, it likely just gets set to zero and neutrallized by the deadband).

  @Override
  public void set(double power) {
    setFollowMode();  // make sure all motors are following the lead as we expect. Possible OPTIMIZATION: store we set the output type to something else on the follow motors (eg. Neutral), and only re-set follow mode if we did. 
    leadMotor.set(power);
  }

  @Override
  /**
   * if using a Ramsete controller, make sure to disable voltage compensation,
   * because voltages have an actual physical meaning from sysid.
   *
   * NOTE: CTRE BaseMotorController setVoltage just uses set() under the hood.
   * Follow motors will use the same percent output as calculated by the
   * setVoltage of the lead motor. This is fine, and equivalent to the base
   * implementation (calling setVoltage on each motor) as that will just run the
   * same voltage->power calculation for each motor.
   */
  public void setVoltage(double voltage) {
    setFollowMode();
    leadMotor.setVoltage(voltage);
  }

  // no need to override setPower because the base class just uses set
  // don't override setBrakeOnNeutral, setCoastOnNeutral, neutralOutput because we indeed want to set it individually on each motor. Otherwise, the followers might try to follow a disabled/neutral motor which might cause unexpected behavior.
  // TODO: except do we actually yes need to? since it seems to not persist, see the warning about auto-disable here: https://docs.revrobotics.com/sparkmax/operating-modes/control-interfaces

  // ERROR HANDLING
  static class REVLibThrowable extends IllegalStateException {
    public REVLibThrowable(REVLibError err) {
      super(err.toString());
    }
    public REVLibThrowable(String msg, REVLibError err) {
      super(msg + err.toString());
    }
    public static void guard(REVLibError err) {
      if (err != REVLibError.kOk) throw new REVLibThrowable(err);
    }
  }
}

