package org.usfirst.frc4904.standard.subsystems.motor;

import java.util.function.DoubleSupplier;
import java.util.stream.Stream;

import org.usfirst.frc4904.robot.RobotMap.PID;
import org.usfirst.frc4904.standard.custom.motorcontrollers.TalonMotorController;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * A group of Talon motors (either TalonFX or TalonSRX) with the modern motor controller features. 
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
 * - Brake mode on a TalonSRX running a brushed motor (eg. 775) will probably do nothing
 * - When voltage has a physical meaning (eg. from a Ramsete controller), use voltageCompensation=0
 * - .neutralOutput() clears follow mode, as do other control modes (eg. MotionMagic, MotionProfiled). For example, I believe the trigger of a limit switch causes follow motors to enter brake mode and thus exit follow mode.
 * - Minimum neutral deadband is 0.001 (according to the docs, https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband) 
 * - Configure normallyClosed limit switches manually on the underlying motorControllers. We usually use normallyOpen limit switches, so you probably don't need to do this.
 */
public class TalonMotorSubsystem extends BrakeableMotorSubsystem<TalonMotorController> {
  private final int configTimeoutMs = 50;  // milliseconds until the Talon gives up trying to configure
  private final int pid_idx = 0; // TODO: add support for auxillary pid
  private final int follow_motors_remote_filter_id = 0; // which filter (0 or 1) will be used to configure reading from the integrated encoder on the lead motor
  public final TalonMotorController leadMotor;
  public final TalonMotorController[] followMotors;

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
	 * @param neutralMode               Whether the motor should brake or coast
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
  public TalonMotorSubsystem(String name, SpeedModifier speedModifier, NeutralMode neutralMode, double neutralDeadbandPercent,
                             Boolean respectLeadMotorLimitSwitches, double voltageCompensation,
                             TalonMotorController leadMotor, TalonMotorController... followMotors) {
		super(name, speedModifier, Stream.concat(Stream.of(leadMotor), Stream.of(followMotors)).toArray(TalonMotorController[]::new));  // java has no spread operator, so you have to concat. best way i could find is to do it in a stream. please make this not bad if you know how 

    this.leadMotor = leadMotor;
    this.followMotors = followMotors;

    // limit switch configuration
    if (respectLeadMotorLimitSwitches) {
      // when extending to SparkMAX: you have to sparkmax.getForward/ReverseLimitSwitch.enable() or something. may need custom polling/plugin logic. https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax#getReverseLimitSwitch(com.revrobotics.SparkMaxLimitSwitch.Type)

      /* notes on limit switches
       * - FeedbackConnector is a given falcon's own feedback connector (the little white 4-pin plug next to the talonfx bump)
       * - the LimitSwitchSource enum has both Talon and TalonSRX, but they have the same value internally (as of 2023) so they should be indistinguishable?
       */
      leadMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, configTimeoutMs);
      leadMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, configTimeoutMs);
      leadMotor.overrideLimitSwitchesEnable(true);

      for (var motor : followMotors) {
        motor.configForwardLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalon, LimitSwitchNormal.NormallyOpen, leadMotor.getDeviceID(), configTimeoutMs);
        motor.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalon, LimitSwitchNormal.NormallyOpen, leadMotor.getDeviceID(), configTimeoutMs);
        motor.overrideLimitSwitchesEnable(true);
      }
    }

    // feedback sensor configuration (for PID)
    leadMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, pid_idx, configTimeoutMs);
    // for (var fm : followMotors) { // don't think this is needed because we are using follow mode. only needed for aux output
    //   fm.configRemoteFeedbackFilter(leadMotor.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor /* enum internals has TalonFX = TalonSRX as of 2023 */, follow_motors_remote_filter_id, configTimeoutMs);
    //   fm.configSelectedFeedbackSensor(follow_motors_remote_filter_id == 0 ? FeedbackDevice.RemoteSensor0 : FeedbackDevice.RemoteSensor1 /* enum internals has TalonFX_SelectedSensor = TalonSRX_SelectedSensor as of 2023 */, pid_idx, configTimeoutMs);
    //   // TODO: change sensor polarity? https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java%20Talon%20FX%20(Falcon%20500)/VelocityClosedLoop_AuxStraightIntegratedSensor/src/main/java/frc/robot/Robot.java#L306
    // }

    // other configuration (neutral mode, neutral deadband, voltagecomp)
    for (var motor : motors) {
      motor.setNeutralMode(neutralMode);
      motor.configNeutralDeadband(neutralDeadbandPercent, configTimeoutMs);
      if (voltageCompensation > 0) {
        motor.configVoltageCompSaturation(voltageCompensation, configTimeoutMs);
        motor.enableVoltageCompensation(true);
      } else {
        motor.enableVoltageCompensation(false);
      }
    }
    setFollowMode();
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
	 * @param neutralMode               Whether the motor should brake or coast
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
  public TalonMotorSubsystem(String name, NeutralMode neutralMode, double voltageCompensation,
                             TalonMotorController leadMotor, TalonMotorController... followMotors) {
		this(name, new IdentityModifier(), neutralMode, 0.001, false, voltageCompensation, leadMotor, followMotors);
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
    leadMotor.config_kP(pid_idx, p, configTimeoutMs);
    leadMotor.config_kI(pid_idx, i, configTimeoutMs);
    leadMotor.config_kD(pid_idx, d, configTimeoutMs);
    leadMotor.config_kF(pid_idx, f, configTimeoutMs);
    leadMotor.configClosedLoopPeriod(pid_idx, 10, configTimeoutMs); // fast enough for 100Hz per second
    // TODO: integral zone and closedLoopPeakOUtput? 
    // other things in the example: motionmagic config and statusframeperiod (for updating sensor status to the aux motor?)
  }
  /**
   * 
   * @param setpointSupplier  a function that returns a double, units = encoder ticks per 100ms
   * 
   * @return
   */
  public Command c_setVelocityControl(DoubleSupplier setpointSupplier) {
    return this.run(() -> leadMotor.set(ControlMode.Velocity, setpointSupplier.getAsDouble()));
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
}

