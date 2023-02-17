package org.usfirst.frc4904.standard.subsystems.motor;

import java.util.stream.Stream;

import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;

public class TalonMotorSubsystem extends BrakeableMotorSubsystem<TalonMotorController> {
  private final int configTimeoutMs = 50;  // milliseconds until the Talon gives up trying to configure
  public final TalonMotorController leadMotor;
  public final TalonMotorController[] followMotors;

	// TODO: voltage ramping (closed and open)

  // TODO: limit switches
  // TODO: add voltage/slew limit to drivetrain motors because we don't want the pid to actively try to stop the motor (negative power) when the driver just lets go of the controls.

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
	 * @param neutralDeadbandPercent    Percent output range around zero to enable
	 *                                  neutralOutput (brake/coast mode) instead.
	 *                                  For more info, see
	 *                                  https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband
   * @param respectLeadMotorLimitSwitches Whether to enable the forward and
   *                                      reverse limit switches wired to the
   *                                      lead motor. This is the easiest way to
   *                                      use forward and reverse normally open
   *                                      limit switches (or just forward and
   *                                      just reverse, as not plugging a limit
   *                                      switch in is equivalent to a normally
   *                                      open switch being unpressed). NOTE
   *                                      This configuration is relatively
   *                                      limited, consider configuring limit
   *                                      switches manually on the
   *                                      TalonMotorController for advanced
   *                                      configuration (eg. normally closed
   *                                      switches).
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

      for (var motor : followMotors) {
        motor.configForwardLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalon, LimitSwitchNormal.NormallyOpen, leadMotor.getDeviceID(), configTimeoutMs);
        motor.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalon, LimitSwitchNormal.NormallyOpen, leadMotor.getDeviceID(), configTimeoutMs);
      }
    }

    // other configuration (neutral mode, neutral deadband, voltagecomp)
    for (var motor : motors) {
      motor.setNeutralMode(neutralMode);
      motor.configNeutralDeadband(neutralDeadbandPercent, configTimeoutMs);
      if (voltageCompensation > 0) {
        motor.configVoltageCompSaturation(voltageCompensation, configTimeoutMs);
        motor.enableVoltageCompensation(true);
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
	 * @param neutralDeadbandPercent    Percent output range around zero to enable
	 *                                  neutralOutput (brake/coast mode) instead.
	 *                                  For more info, see
	 *                                  https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband
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
  public TalonMotorSubsystem(String name, NeutralMode neutralMode, double neutralDeadbandPercent,
                             double voltageCompensation,
                             TalonMotorController leadMotor, TalonMotorController... followMotors) {
		this(name, new IdentityModifier(), neutralMode, neutralDeadbandPercent, false, voltageCompensation, leadMotor, followMotors);
	}

  /**
   * Make all follow motors follow the lead motor.
   */
  private void setFollowMode() {
    for (var motor : this.followMotors) {
      motor.follow(leadMotor);
    }
  }
  
  // don't override disable() or stop() because we *should* indeed use the base implementation of disabling/stopping each motor controller individually. Otherwise the following motors will try to follow a disabled motor, which may cause unexpected behavior (although realistically, it likely just gets set to zero and neutrallized by the deadband).

  @Override
  public void set(double power) {
    setFollowMode();  // make sure all motors are following the lead as we expect. Possible OPTIMIZATION: store we set the output type to something else on the follow motors (eg. Neutral), and only re-set follow mode if we did. 
    leadMotor.set(power);
  }

  // @Override
  // public double get() {
  //   return prevPower;
  //   // if we actually need this, we'll need to store prevPower and add an edge case in setVoltage and update it in set()
  //   // or maybe just get the leadMotorController.get()?
  // }

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

