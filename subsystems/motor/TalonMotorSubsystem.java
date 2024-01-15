// package org.usfirst.frc4904.standard.subsystems.motor;

// import java.util.function.DoubleSupplier;
// import java.util.stream.Stream;

// import org.usfirst.frc4904.standard.custom.motorcontrollers.CANTalonFX;
// import org.usfirst.frc4904.standard.custom.motorcontrollers.TalonMotorController;
// import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
// import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;

// import com.ctre.phoenix6.signals.ControlModeValue;
// import com.ctre.phoenix6.motorcontrol.DemandType;
// import com.ctre.phoenix6.motorcontrol.FeedbackDevice;
// // import com.ctre.phoenix6.motorcontrol.LimitSwitchNormal;
// // import com.ctre.phoenix6.motorcontrol.LimitSwitchSource;
// import com.ctre.phoenix6.signals.NeutralModeValue;
// // import com.ctre.phoenix6.motorcontrol.RemoteLimitSwitchSource;

// import edu.wpi.first.wpilibj.RobotController;
// import edu.wpi.first.wpilibj2.command.Command;

// /**
//  * A group of Talon motors (either TalonFX or TalonSRX) with the modern motor controller features. 
//  * 
//  * Provided constructors:
//  * - (name, speedmodifier=identity, neutralMode, deadband=0.01, limitswitches=false, voltageCompensation, leadMotor, followMotors)
//  * - (name, SpeedModifier, neutralMode, neutralDeadbandPercent, respectLeadMotorLimitSwitches, voltageCompensation, leadMotor, followMotors)
//  * 
//  * Features included:
//  * - Neutral mode
//  * - Neutral deadband percent
//  * - Hardwired, normally open limit switches 
//  * - Voltage compensation
//  * - Follow mode
//  * 
//  * Gotchas:
//  * - Brake mode on a TalonSRX running a brushed motor (eg. 775) will probably do nothing
//  * - When voltage has a physical meaning (eg. from a Ramsete controller), use voltageCompensation=0
//  * - .neutralOutput() clears follow mode, as do other control modes (eg. MotionMagic, MotionProfiled). For example, I believe the trigger of a limit switch causes follow motors to enter brake mode and thus exit follow mode.
//  * - Minimum neutral deadband is 0.001 (according to the docs, https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband) 
//  * - Configure normallyClosed limit switches manually on the underlying motorControllers. We usually use normallyOpen limit switches, so you probably don't need to do this.
//  */
// public class TalonMotorSubsystem extends SmartMotorSubsystem<TalonMotorController> {
//   private static final int ENCODER_COUNTS_PER_REV = 2048;
//   private static final double RPM_TO_ENCODERCOUNTSPER100MS = ENCODER_COUNTS_PER_REV/60/10;
//   private final int configTimeoutMs = 5;  // milliseconds until the Talon gives up trying to configure
//   private static final int DEFAULT_PID_SLOT = 0; // TO DO: add support for auxillary pid
//   private final int follow_motors_remote_filter_id = 0; // DONOT REMOVE, USED IN COMMENTED CODE BELOW; which filter (0 or 1) will be used to configure reading from the integrated encoder on the lead motor
//   private final double voltageComp;
//   private boolean pid_configured = false; // flag for command factories to check whether PID was configured
//   public final TalonMotorController leadMotor;
//   public final TalonMotorController[] followMotors;

//   // TO DO: stator current limits? also makes brake mode stronger? https://www.chiefdelphi.com/t/programming-current-limiting-for-talonfx-java/371860
//   // TO DO: peak/nominal outputs
//   // TO DO: add voltage/slew limit to drivetrain motors because we don't want the pid to actively try to stop the motor (negative power) when the driver just lets go of the controls. diff ones for closed and open
//   // TO DO: control speed near soft limits, so that you can't go full throttle near the soft limit? impl as a speed modifier??

//   /**
//    * Motor Subsystem for a group of Talon motor controllers (Falcons, 775s).
//    * Uses Talon-specific APIs like follow mode and motionProfile control mode to
//    * offload work from the RoboRIO.
//    *
//    * You probably want to set inverted mode on the TalonMotorController using
//    * FollowLeader or OpposeLeader
//    *
//    * @param name
//    * @param speedModifier             
// 	 * @param neutralMode               Whether the motor should brake or coast
// 	 *                                  when the the output is near zero, or
// 	 *                                  .disable() or .stopMotor() are called.
// 	 * @param neutralDeadbandPercent    Power output range around zero to enable
// 	 *                                  neutralOutput (brake/coast mode) instead.
// 	 *                                  Can be in the range [0.001, 0.25], CTRE
// 	 *                                  default is 0.04. For more info, see
// 	 *                                  https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband
//    * @param respectLeadMotorLimitSwitches Whether to enable the forward and
//    *                                      reverse limit switches wired to the
//    *                                      lead motor. This is the easiest way to
//    *                                      use forward and reverse normally open
//    *                                      limit switches (or just forward or
//    *                                      just reverse, as not plugging a limit
//    *                                      switch in is equivalent to a normally
//    *                                      open switch being unpressed). NOTE
//    *                                      This configuration is relatively
//    *                                      limited, consider configuring limit
//    *                                      switches manually on the
//    *                                      MotorController for advanced
//    *                                      configuration (eg. normally closed
//    *                                      switches). NOTE passing false will not
//    *                                      disable limit switches; just unplug
//    *                                      them instead. 
//    * @param voltageCompensation       0 to disable, 10 is a good default.
//    *                                  Rescales output so that power=1.0
//    *                                  corresponds to
//    *                                  voltage=voltageCompensation. This way,
//    *                                  setting a power will lead to consistent
//    *                                  output even when other components are
//    *                                  running. Basically nerf all motors so that
//    *                                  they have a consistent output. when the
//    *                                  battery is low.
//    * @param leadMotor
//    * @param followMotors
//    */
//   public TalonMotorSubsystem(String name, SpeedModifier speedModifier, NeutralModeValue neutralMode, double neutralDeadbandPercent,
//                              Boolean respectLeadMotorLimitSwitches, double voltageCompensation,
//                              CANTalonFX leadMotor, TalonMotorController... followMotors) {
// 		super(name, speedModifier, Stream.concat(Stream.of(leadMotor), Stream.of(followMotors)).toArray(TalonMotorController[]::new));  // java has no spread operator, so you have to concat. best way i could find is to do it in a stream. please make this not bad if you know how 

//     this.voltageComp = voltageCompensation;
//     this.leadMotor = leadMotor;
//     this.followMotors = followMotors;

//     // limit switch configuration
//     // TODO: fix it
//     // if (respectLeadMotorLimitSwitches) {
//     //   // when extending to SparkMAX: you have to sparkmax.getForward/ReverseLimitSwitch.enable() or something. may need custom polling/plugin logic. https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax#getReverseLimitSwitch(com.revrobotics.SparkMaxLimitSwitch.Type)

//     //   /* notes on limit switches
//     //    * - FeedbackConnector is a given falcon's own feedback connector (the little white 4-pin plug next to the talonfx bump)
//     //    * - the LimitSwitchSource enum has both Talon and TalonSRX, but they have the same value internally (as of 2023) so they should be indistinguishable?
//     //    */
//     //   leadMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, configTimeoutMs);
//     //   leadMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, configTimeoutMs);
//     //   leadMotor.overrideLimitSwitchesEnable(true);

//     //   for (var motor : followMotors) {
//     //     motor.configForwardLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalon, LimitSwitchNormal.NormallyOpen, leadMotor.getDeviceID(), configTimeoutMs);
//     //     motor.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalon, LimitSwitchNormal.NormallyOpen, leadMotor.getDeviceID(), configTimeoutMs);
//     //     motor.overrideLimitSwitchesEnable(true);
//     //   }
//     // }

//     // feedback sensor configuration (for PID and general encoder stuff)
//     leadMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, DEFAULT_PID_SLOT, configTimeoutMs);
//     for (var m : followMotors) {
//       m.configRemoteFeedbackFilter(leadMotor, 0, configTimeoutMs);
//       m.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, DEFAULT_PID_SLOT, configTimeoutMs);
//     }
//     // make sure to update the static final ENCODER_COUNTS_PER_REV if you are using different encoders. Better yet, add a feedbackSensor argument to this method

//     // other configuration (neutral mode, neutral deadband, voltagecomp)
//     for (var motor : motors) {
//       motor.setNeutralMode(neutralMode);
//       motor.configNeutralDeadband(neutralDeadbandPercent, configTimeoutMs);
//       if (voltageCompensation > 0) {
//         motor.configVoltageCompSaturation(voltageCompensation, configTimeoutMs);
//         motor.enableVoltageCompensation(true);
//       } else {
//         motor.enableVoltageCompensation(false);
//       }
//     }
// 	}
//   /**
//    * Motor Subsystem for a group of Talon motor controllers (Falcons, 775s).
//    * Uses Talon-specific APIs like follow mode and motionProfile control mode to
//    * offload work from the RoboRIO.
//    *
//    * You probably want to set inverted mode on the TalonMotorController using
//    * FollowLeader or OpposeLeader
//    *
//    * Consider using the advanced constructor for limit switches and voltage
//    * compensation, and using the .cfg...() methods for voltage ramping and
//    * nominal output.
//    * 
//    * When using SparkMaxes:
//    * - can limit switches be enabled/disabled? can you have a sparkmax respect a talon limit switch? https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax
//    *
//    * @param name
// 	 * @param neutralMode               Whether the motor should brake or coast
// 	 *                                  when the the output is near zero, or
// 	 *                                  .disable() or .stopMotor() are called.
//    * @param voltageCompensation       0 to disable, 10 is a good default.
//    *                                  Rescales output so that power=1.0
//    *                                  corresponds to
//    *                                  voltage=voltageCompensation. This way,
//    *                                  setting a power will lead to consistent
//    *                                  output even when other components are
//    *                                  running. Basically nerf all motors so that
//    *                                  they have a consistent output. when the
//    *                                  battery is low.
//    * @param leadMotor
//    * @param followMotors
//    */
//   public TalonMotorSubsystem(String name, NeutralModeValue neutralMode, double voltageCompensation,
//                              CANTalonFX leadMotor, TalonMotorController... followMotors) {
// 		this(name, new IdentityModifier(), neutralMode, 0.001, false, voltageCompensation, leadMotor, followMotors);
// 	}

//   // /**
//   //  * Make all follow motors follow the lead motor.
//   //  */
//   // private void setFollowMode() {
//   //   for (var motor : this.followMotors) {
//   //     motor.follow(leadMotor);
//   //   }
//   // }

//   // TO DO the following methods are not thought out or documented
//   /**
//    * The F value provided here will be overwritten if provided to subsystem.leadMotor.set; note that if you do that, it will bypass the subystem requirements check
//    * 
//    * See docstrings on the methods used in the implementation for physical units
//    * 
//    * @param p  in units of encoder counts per 100ms
//    * @param i  in units of TO DO
//    * @param d  in units of TO DO
//    * @param f  in units of percent output, [-1, 1]
//    * @param accumulator in units of whatever the intergral is in
//    * @param peakOutput in units of percent output, [-1, 1]
//    * @param pid_slot range [0, 3], pass null for default of zero.
//    */
//   @Override @Deprecated
//   public void configPIDF(double p, double i, double d, double f, double max_integral_accumulation, double peakOutput, Integer pid_slot) {
//     if (pid_slot == null) pid_slot = TalonMotorSubsystem.DEFAULT_PID_SLOT;

//     // should follow motors do their own PID? no, right? 

//     // PID constants configuration
//     leadMotor.config_kP(pid_slot, p, configTimeoutMs);
//     leadMotor.config_kI(pid_slot, i, configTimeoutMs);
//     leadMotor.config_kD(pid_slot, d, configTimeoutMs);
//     leadMotor.config_kF(pid_slot, voltageComp == 0 ? f/voltageComp : f/12, configTimeoutMs);
//     leadMotor.configClosedLoopPeriod(pid_slot, 10, configTimeoutMs); // fast enough for 100Hz per second
    
//     leadMotor.configMaxIntegralAccumulator(DEFAULT_DMP_SLOT, max_integral_accumulation, configTimeoutMs);
//     leadMotor.configClosedLoopPeakOutput(DEFAULT_DMP_SLOT, peakOutput, configTimeoutMs);

//     pid_configured = true;
//     // other things in the example: motionmagic config and statusframeperiod (for updating sensor status to the aux motor?)
//   }
//   /**
//    * Assumes that PID and DMP slots correspond (eg. use PID slot 0 for DMP slot 0)
//    */
//   @Override @Deprecated
//   public void configDMP(double minRPM, double cruiseRPM, double accl_RPMps, double maxError_encoderTicks,
//       Integer dmp_slot) {
//     if (dmp_slot == null) dmp_slot = DEFAULT_DMP_SLOT;
//     // TO DO? 
// 		// _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
// 		// _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

//     leadMotor.selectProfileSlot(dmp_slot, dmp_slot);
//     leadMotor.configMotionCruiseVelocity(cruiseRPM*RPM_TO_ENCODERCOUNTSPER100MS, configTimeoutMs);
//     leadMotor.configMotionAcceleration(accl_RPMps*RPM_TO_ENCODERCOUNTSPER100MS, configTimeoutMs);
//     // TO DO: min rpm not used -- not sure if possible or needed
//   }
  
//   // don't override disable() or stop() because we *should* indeed use the base implementation of disabling/stopping each motor controller individually. Otherwise the following motors will try to follow a disabled motor, which may cause unexpected behavior (although realistically, it likely just gets set to zero and neutrallized by the deadband).

//   /**
//    * Consider using .c_controlRPM to stream RPM values to this motor in a command.
//    * 
//    * You must call .configPIDF before using this method.
//    * @param rpm
//    */
//   public void setRPM(double rpm) {
//     for (var m: motors) m.set(ControlModeValue.Velocity, rpm / 60 / 10 * ENCODER_COUNTS_PER_REV);  // velocity units are in encoder counts per 100ms
//   }
//   /**
//    * Consider using .c_controlRPM to stream RPM values to this motor in a command.
//    * 
//    * You must call .configPIDF before using this method.
//    * @param rpm
//    */
//   public void setRPM(double rpm, double feedforwardVolts) {
//     for (var m : motors) m.set(ControlModeValue.Velocity, rpm / 60 / 10 * ENCODER_COUNTS_PER_REV, DemandType.ArbitraryFeedForward, feedforwardVolts/RobotController.getBatteryVoltage());  // velocity units are in encoder counts per 100ms
//   }

//   /**
//    * if using a Ramsete controller, make sure to disable voltage compensation,
//    * because voltages have an actual physical meaning from sysid.
//    *
//    * NOTE: CTRE BaseMotorController setVoltage just uses set() under the hood.
//    * Follow motors will use the same percent output as calculated by the
//    * setVoltage of the lead motor. This is fine, and equivalent to the base
//    * implementation (calling setVoltage on each motor) as that will just run the
//    * same voltage->power calculation for each motor.
//    */
//   @Override
//   public void setVoltage(double voltage) {
//     if (voltageComp > 0) {
//       for (var m : motors) m.setPower(voltage / Math.min(RobotController.getBatteryVoltage(), voltageComp));
//     } else {
//       for (var m : motors) m.setVoltage(voltage);  // CTRE internal code just divides by RobotController.getBatteryVoltage
//     }
//   }
//   /**
//    * You must call .configPIDF before using this method.
//    * 
//    * @param rpm
//    * @return the command to be scheduled.
//    */
//   @Override @Deprecated
//   public Command c_setRPM(double rpm) { return this.runOnce(() -> setRPM(rpm)); }
//   @Override
//   public Command c_controlRPM(DoubleSupplier setpointSupplier, DoubleSupplier feedforwardSupplierVolts) {
//     if (pid_configured == false) throw new IllegalArgumentException(name + " tried to use c_controlRPM without first configPIDF()-ing.");
//     return this.run(() -> setRPM(setpointSupplier.getAsDouble(), feedforwardSupplierVolts.getAsDouble()));
//   }
//   /**
//    * 
//    * @param setpointSupplier  a function that returns a double, rpm
//    * 
//    * @return
//    */
//   @Override
//   public Command c_controlRPM(DoubleSupplier setpointSupplier) {
//     if (pid_configured == false) throw new IllegalArgumentException(name + " tried to use c_controlRPM without first configPIDF()-ing.");
//     return this.run(() -> setRPM(setpointSupplier.getAsDouble()));
//   }
//   @Override
//   public Command c_holdRPM(double setpoint) {
//     if (pid_configured == false) throw new IllegalArgumentException(name + " tried to use c_controlRPM without first configPIDF()-ing.");
//     return this.runOnce(() -> setRPM(setpoint)).andThen(new Command(){});  
// }
//   /**
//    * Command that sets the position setpoint and immedietly ends.
//    */
//   public Command c_setPosition(double setpoint, int dmp_slot) {
//     this.leadMotor.selectProfileSlot(dmp_slot, dmp_slot);
//     //return this.runOnce(() -> setDynamicMotionProfileTargetRotations(setpoint)).andThen(new Command(){}); // this would go forever
//     return new HardwareDMPUntilArrival(this, setpoint);
//   }
//   @Override
//   public Command c_setPosition(double setpoint) { return c_setPosition(setpoint, DEFAULT_DMP_SLOT); }

//   @Override
//   public Command c_controlPosition(DoubleSupplier setpointSupplier, DoubleSupplier feedforwardSupplierVolts) {
//     return this.run(() -> this.leadMotor.set(
//       ControlMode.Position, setpointSupplier.getAsDouble(),
//       DemandType.ArbitraryFeedForward, feedforwardSupplierVolts.getAsDouble()/RobotController.getBatteryVoltage() // hack because WPI_CANTalonFX only takes feedforward in units of %output
//     ));
//   }
//   @Override
//   public Command c_controlPosition(DoubleSupplier setpointSupplier) {
//     return this.run(() -> this.leadMotor.set(ControlMode.Position, setpointSupplier.getAsDouble()));
//   }
//   /**
//    * Hold the position using smart motion (on-the-fly motion-profile generation).
//    */
//   public Command c_holdPosition(double setpoint, int dmp_slot) {
//     this.leadMotor.selectProfileSlot(dmp_slot, dmp_slot);
//     return this.runOnce(() -> setDynamicMotionProfileTargetRotations(setpoint)).andThen(new Command(){});
//   }
//   public Command c_holdPosition(double setpoint) { return c_holdPosition(setpoint, DEFAULT_DMP_SLOT); }
   

//   @Override
//   public void zeroSensors(double rotations) {
//     this.leadMotor.setSelectedSensorPosition(rotations * ENCODER_COUNTS_PER_REV, DEFAULT_PID_SLOT, configTimeoutMs);
//     // should we zero the sensors on follow motors in case they are being used?
//   }
//   @Override
//   public void zeroSensors() {
//     zeroSensors(0);
//   }
//   @Override
//   protected void setDynamicMotionProfileTargetRotations(double rotations) {
//     this.leadMotor.set(ControlMode.MotionMagic, rotations*RPM_TO_ENCODERCOUNTSPER100MS);
//   }
//   @Override
//   public double getSensorPositionRotations() {
//     return this.leadMotor.getSelectedSensorPosition(DEFAULT_DMP_SLOT) / ENCODER_COUNTS_PER_REV;
//   }
//   public double getSensorVelocityRPM() {
//     return this.leadMotor.getSelectedSensorVelocity(DEFAULT_DMP_SLOT) / ENCODER_COUNTS_PER_REV * 10 * 60;
//   }
//   @Override
//   public void configSoftwareLimits(double fwdBoundRotations, double revBoundRotations) {
//     //set each software limits for each follow motor
//     leadMotor.configForwardSoftLimitThreshold((fwdBoundRotations*ENCODER_COUNTS_PER_REV), configTimeoutMs);
//     leadMotor.configReverseSoftLimitThreshold((revBoundRotations*ENCODER_COUNTS_PER_REV), configTimeoutMs);
//     leadMotor.configForwardSoftLimitEnable(true, configTimeoutMs);
//     leadMotor.configReverseSoftLimitEnable(true, configTimeoutMs);
//     leadMotor.overrideSoftLimitsEnable(true);
//     for (var motor : followMotors) {
//       motor.configForwardSoftLimitThreshold((fwdBoundRotations*ENCODER_COUNTS_PER_REV), configTimeoutMs);
//       motor.configReverseSoftLimitThreshold((revBoundRotations*ENCODER_COUNTS_PER_REV), configTimeoutMs);
//       motor.configForwardSoftLimitEnable(true, configTimeoutMs);
//       motor.configReverseSoftLimitEnable(true, configTimeoutMs);
//       motor.overrideSoftLimitsEnable(true);
//     // this.leadMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, DEFAULT_PID_SLOT, configTimeoutMs);  // select which sensor to use for soft limits
//     // this.leadMotor.setSensorPhase(true);
//     }

//   // no need to override setPower because the base class just uses set
//   // don't override setBrakeOnNeutral, setCoastOnNeutral, neutralOutput because we indeed want to set it individually on each motor. Otherwise, the followers might try to follow a disabled/neutral motor which might cause unexpected behavior.
//   }
// }

