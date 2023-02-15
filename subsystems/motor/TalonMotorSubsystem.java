package org.usfirst.frc4904.standard.subsystems.motor;

import java.util.stream.Stream;

import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

public class TalonMotorSubsystem extends BrakeableMotorSubsystem {
  public final TalonMotorController leadMotor;
  public final TalonMotorController[] followMotors;

  /**
   * Motor Subsystem for a group of Talon motor controllers (Falcons, 775s).
   * Uses Talon-specific APIs like follow mode and motionProfile control mode to
   * offload work from the RoboRIO.
   *
   * You probably want to set inverted mode on the TalonMotorController using
   * FollowLeader or OpposeLeader
   * @param name
   * @param speedModifier
   * @param leadMotor
   * @param followMotors
   */
  public TalonMotorSubsystem(String name, SpeedModifier speedModifier, TalonMotorController leadMotor, TalonMotorController... followMotors) {
		super(name, speedModifier, Stream.concat(Stream.of(leadMotor), Stream.of(followMotors)).toArray(TalonMotorController[]::new));  // java has no spread operator, so you have to concat. best way i could find is to do it in a stream

    this.leadMotor = leadMotor;
    this.followMotors = followMotors;

    setFollowMode();
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
   * NOTE: CTRE BaseMotorController setVoltage just uses set() under the hood.
   * Follow motors will use the same percent output as calculated by the
   * setVoltage of the lead motor. This is fine, and equivalent to the base
   * implementation (calling setVoltage on each motor) as that will just run the
   * same voltage->power calculation for each motor.
   * 
   * TODO: shouldn't we use voltagecomp instead
   */
  public void setVoltage(double voltage) {
    setFollowMode();
    leadMotor.setVoltage(voltage);
  }

  // no need to override setPower because the base class just uses set

  // TODO: does setting brake mode on leader set it on follower? if so, then override setBrakeModeOnNeutral, setCoastModeOnNeutral, and neutralOutput
}

