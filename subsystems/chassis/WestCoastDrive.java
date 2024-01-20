//TODO: fix
// package org.usfirst.frc4904.standard.subsystems.chassis;

// import java.util.List;
// import java.util.Map;
// import java.util.function.Supplier;

// import org.usfirst.frc4904.robot.RobotMap;
// import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem;

// import com.kauailabs.navx.frc.AHRS;
// import com.pathplanner.lib.path.PathConstraints;
// // TODO: .PathPlanner not a thing?
// // import com.pathplanner.lib.PathPlanner;
// import com.pathplanner.lib.path.PathPlannerTrajectory;
// // TODO: Also not a thing?
// // import com.pathplanner.lib.auto.PIDConstants;
// import com.pathplanner.lib.auto.AutoBuilder;

// import edu.wpi.first.math.Pair;
// import edu.wpi.first.math.controller.DifferentialDriveWheelVoltages;
// import edu.wpi.first.math.controller.RamseteController;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
// import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
// import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
// import edu.wpi.first.math.trajectory.Trajectory;
// import edu.wpi.first.wpilibj.RobotController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class WestCoastDrive extends SubsystemBase {
//     public final TalonMotorSubsystem leftMotors;
//     public final TalonMotorSubsystem rightMotors;
//     // protected final PIDConstants pidConsts;
//     protected final DifferentialDriveKinematics kinematics;
//     public final DifferentialDriveOdometry odometry;   // OPTIM this can be replaced with a kalman filter?
//     protected final AHRS gyro;    // OPTIM this can be replaced by something more general
//     protected final double mps_to_rpm;
//     protected final double m_to_motorrots;

//     private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
//         RobotMap.PID.Drive.kS, 
//         RobotMap.PID.Drive.kV,
//         RobotMap.PID.Drive.kA
//         );

//     /**
//      * Represents a west coast drive chassis as a subsystem
//      * 
//      * @param trackWidthMeters          Track width, from horizontal center to center of the wheel, meters
//      * @param motorToWheelGearRatio     Number of motor rotations for one wheel rotation. > 1 for gearing the motors down, eg. 13.88/1
//      * @param wheelDiameterMeters
//      * @param leftMotorSubsystem        SmartMotorSubsystem for the left wheels. Usually a TalonMotorSubsystem with two talons.
//      * @param rightMotorSubsystem       SmartMotorSubsystem for the right wheels. Usually a TalonMotorSubsystem with two talons.
//      */
//     public WestCoastDrive(
//         double trackWidthMeters, double motorToWheelGearRatio, double wheelDiameterMeters,
//         double drive_kP, double drive_kI, double drive_kD,
//         AHRS navx, TalonMotorSubsystem leftMotorSubsystem, TalonMotorSubsystem rightMotorSubsystem) {
//         leftMotors = leftMotorSubsystem;
//         rightMotors = rightMotorSubsystem;
//         gyro = navx;
//         mps_to_rpm = (Math.PI * wheelDiameterMeters) * motorToWheelGearRatio * 60;
//         m_to_motorrots = 1/wheelDiameterMeters*motorToWheelGearRatio;
//         // pidConsts = new PIDConstants(drive_kP, drive_kI, drive_kD);
//         kinematics = new DifferentialDriveKinematics(trackWidthMeters);  // 2023 robot has track width ~19.5 inches, 5 in wheel diameter
//         odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), getLeftDistance(), getRightDistance());

//         // OPTIM should probably allow specification of f, max_accumulation, and peakOutput in constructor
//         // leftMotors. configPIDF(drive_kP, drive_kI, drive_kD, 0, 100, 1, null);
//         // rightMotors.configPIDF(drive_kP, drive_kI, drive_kD, 0, 100, 1, null);
//         zeroEncoders();
//     }

//     // odometry methods
//     public double  getLeftDistance() { return  leftMotors.getSensorPositionRotations()/m_to_motorrots; }
//     public double getRightDistance() { return rightMotors.getSensorPositionRotations()/m_to_motorrots; }
//     private void zeroEncoders() { leftMotors.zeroSensors(); rightMotors.zeroSensors(); }
//     public void resetPoseMeters(Pose2d metersPose) {
//         zeroEncoders();
//         // doesn't matter what the encoders start at, odometry will use delta of odometry.update() from odometry.reset()
//         odometry.resetPosition(gyro.getRotation2d(), getLeftDistance(), getRightDistance(), metersPose);
//     }
//     public DifferentialDriveWheelSpeeds getWheelSpeeds() {
//         return new DifferentialDriveWheelSpeeds(
//              leftMotors.getSensorVelocityRPM()/m_to_motorrots/60,
//             rightMotors.getSensorVelocityRPM()/m_to_motorrots/60
//         );
//     }
//     public Pose2d getPoseMeters() {
//         return odometry.getPoseMeters();
//     }
//     @Override
//     public void periodic() {
//         odometry.update(gyro.getRotation2d(), getLeftDistance(), getRightDistance());
//     }
    
//     /// drive methods

//     /**
//      * Convention: +x forwards, +y right, +z down
//      * 
//      * @param xSpeed
//      * @param ySpeed
//      * @param turnSpeed
//      */
//     @Deprecated
//     public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
//         if (ySpeed != 0) throw new IllegalArgumentException("West Coast Drive cannot move laterally!");
//         setChassisVelocity(new ChassisSpeeds(xSpeed, ySpeed, turnSpeed));
//     }
//     @Deprecated
//     public void movePolar(double speed, double heading, double turnSpeed) {
//         if (heading != 0) throw new IllegalArgumentException("West Coast Drive cannot move at a non-zero heading!");
//         setChassisVelocity(new ChassisSpeeds(speed, 0, turnSpeed));
//     }

//     @Deprecated
//     public void testFeedForward(double speed) {
//         var ff = this.feedforward.calculate(speed);
//         // this.setWheelVoltages(new DifferentialDriveWheelVoltages(ff, ff));
//         // COMP: why does this do one side forward, one side backwards? should be inverted correctly??
//         this.leftMotors.setVoltage(ff);
//         this.rightMotors.setVoltage(ff);
//     }

//     public void setWheelVelocities(DifferentialDriveWheelSpeeds wheelSpeeds) {
//         this.leftMotors .setRPM(wheelSpeeds.leftMetersPerSecond  * mps_to_rpm);
//         this.rightMotors.setRPM(wheelSpeeds.rightMetersPerSecond * mps_to_rpm);
//     }
//     public void setChassisVelocity(ChassisSpeeds chassisSpeeds) {
//         final var wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
//         setWheelVelocities(wheelSpeeds);
//     }

//     public void setWheelVoltages(DifferentialDriveWheelVoltages wheelVoltages) {
//         this.setWheelVoltages(wheelVoltages.left, wheelVoltages.right);
//     }
//     public void setWheelVoltages(double leftV, double rightV) {
//         this.leftMotors.setVoltage(-leftV);
//         this.rightMotors.setVoltage(rightV);
//     }






//     // FIXME: use reasonable driver controls
//     // public void setChassisVoltage(ChassisSpeeds sketchyVoltages) {
//     //     final double SKETCHY_CORRECTION = 1;  
//     //     final var wheelVoltages = kinematics.toWheelSpeeds(sketchyVoltages);
//     //     // we do a little trolling
//     //     setWheelVoltages(wheelVoltages.leftMetersPerSecond * SKETCHY_CORRECTION, wheelVoltages.rightMetersPerSecond * SKETCHY_CORRECTION);
//     // }
//     // public Command c_controlChassisWithVoltage(Supplier<ChassisSpeeds> chassisSpeedVoltsSupplier) {
//     //     var cmd = this.run(() -> {
//     //         var volts = chassisSpeedVoltsSupplier.get();
//     //         setChassisVoltage(volts);
//     //     });    // this.run() runs repeatedly
//     //     cmd.addRequirements(leftMotors);
//     //     cmd.addRequirements(rightMotors);
//     //     return cmd;
//     // }
//     //     /**
//     //  * A forever command that pulls left and right wheel voltages from a
//     //  * function.
//     //  */
//     // public Command c_controlChassisSpeedAndTurn(Supplier<Pair<Double, Double>> chasssisSpeedAndTurnSupplier) {
//     //     var cmd = c_controlChassisWithVoltage(() -> {
//     //         Pair<Double, Double> speedAndTurn = chasssisSpeedAndTurnSupplier.get();
//     //         return new ChassisSpeeds(speedAndTurn.getFirst()*RobotController.getBatteryVoltage(), 0, speedAndTurn.getSecond());
//     //     });
//     //     return cmd;
//     // }







//     /// command factories
    
//     /**
//      * Load a PathPlanner .path file, generate it with maxVel and maxAccl, and
//      * run the commands specified by eventMap at the stopping points.
//      * 
//      * Usage example:
//      * <pre>
//      * // in RobotMap
//      * 
//      * public static HashMap<String, Command> eventMap;
//      * public static IntakeSubsytem intakeSubsystem;
//      * public static WestCoastDrive drivetrain;
//      * public static Command autoCommand;
//      * // ...
//      * RobotMap.eventMap = Map.ofEntries(
//      *   entry("logEvent", Commands.runOnce(() -> LogKitten.wtf("auton log event hit!"))),
//      *   entry("extendIntake", RobotMap.Component.intakeSubsystem.c_extend())
//      * );
//      * RobotMap.autoCommand = RobotMap.Component.drivetrain.c_buildPathPlannerAuto(0, 0, 0, 2, 0.1, "center_auton", 6, 3, eventMap);
//      * // call the auto factory during startup because it can take a second to generate the trajectory.
//      * // make sure to replace 0s with constants from drivetrain characterization!
//      * 
//      * // in autonomousInitialize
//      * RobotMap.autoCommand.schedule();
//      * </pre>
//      *
//      * @param ffks           Motor feedforward static gain. From sysid. If unknown,
//      *                       0 is an okay default.
//      * @param ffkv           Motor feedforward velocity gain. From sysid. If
//      *                       unknown, 0 is an okay default.
//      * @param ffka           Motor feedforward acceleration gain. 0 is usually okay.
//      * @param ramsete_b      Ramsete controller b value. 2 is a good default.
//      * @param ramsete_zeta   Ramsete controller zeta value. 0.1 is a good default.
//      *                       Reduce this value if the drivetrain speed is
//      *                       oscillating (speeds up and slows down, moves jerkily,
//      *                       sounds like a train).
//      * @param autonGroupName The name of the pathplanner path. NAME => search for
//      *                       src/main/deploy/pathplanner/NAME.path
//      * @param maxVel         Max velocity constraint used in path generation.
//      * @param maxAccl        Max acceleration constraint used in path generation.
//      * @param eventMap       HashMap containing commands for each "event" in the
//      *                       path. PathPlanner will run the commands at those event
//      *                       markers.
//      * @return the command to schedule
//      */
//     @Deprecated // don't use this, it doesn't really work
//     public Command c_buildPathPlannerAuto(
//             double ffks, double ffkv, double ffka,
//             double ramsete_b, double ramsete_zeta,
//             String autonGroupName, double maxVel, double maxAccl, Map<String, Command> eventMap) {
//         List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(autonGroupName, new PathConstraints(maxVel, maxAccl));
//         AutoBuilder autoBuilder = new AutoBuilder(
//             () -> this.getPoseMeters(),
//             (pose) -> this.resetPoseMeters(pose), 
//             new RamseteController(ramsete_b, ramsete_zeta),
//             kinematics,
//             new SimpleMotorFeedforward(ffks, ffkv, ffka),
//             () -> this.getWheelSpeeds(),
//             pidConsts,
//             (leftVolts, rightVolts) -> this.setWheelVoltages(new DifferentialDriveWheelVoltages(leftVolts, rightVolts)),
//             eventMap,
//             this
//         );
//         return autoBuilder.fullAuto(pathGroup);
//     } 
//     /**
//      * A forever command that pulls drive velocities from a function and sends
//      * them to the motor's closed-loop control.
//      * 
//      * For composition reasons, leftRightVelocitySupplier gets called twice per frame. OPTIMIZABLE
//      *
//      * @param leftRightVelocity A function that returns a pair containing the
//      *                          left and right velocities, m/s.
//      * 
//      * @return the command to be scheduled
//      */
//     public Command c_controlWheelVelocities(Supplier<DifferentialDriveWheelSpeeds> leftRightVelocitySupplier) {
//         var cmd = this.run(() -> setWheelVelocities(leftRightVelocitySupplier.get()));    // this.run() runs repeatedly
//         cmd.addRequirements(leftMotors);
//         cmd.addRequirements(rightMotors);
//         return cmd;
//     }

//     /**
//      * A forever command that pulls chassis movement
//      * (forward speed and turn * radians/sec) from a * function and
//      * sends them to the motor's closed-loop * control.
//      */
//     public Command c_controlChassisVelocity(Supplier<ChassisSpeeds> chassisSpeedsSupplier) {
//         var cmd = this.run(() -> setChassisVelocity(chassisSpeedsSupplier.get()));    // this.run() runs repeatedly
//         cmd.addRequirements(leftMotors);
//         cmd.addRequirements(rightMotors);
//         return cmd;
//     }
//     /**
//      * A forever command that pulls left and right wheel voltages from a
//      * function.
//      */
//     public Command c_controlWheelVoltages(Supplier<DifferentialDriveWheelVoltages> wheelVoltageSupplier) {
//         var cmd = this.run(() -> {
//                 setWheelVoltages(wheelVoltageSupplier.get());
//                 //System.out.println(wheelVoltageSupplier.get());
//             }
//         );    // this.run() runs repeatedly
//         cmd.addRequirements(leftMotors);
//         cmd.addRequirements(rightMotors);
//         return cmd;
//     }


//     // convienence commands for feature parity with pre-2023 standard
//     /**
//      * moveCartesian with (x, y, turn) for timeout seconds.
//      * 
//      * Replaces ChassisConstant in pre-2023 standard.
//      */
//     @Deprecated
//     public Command c_chassisConstant(double x, double y, double turn, double timeout) {
//         return this.run(() -> moveCartesian(x, y, turn)).withTimeout(timeout);
//     }
//     /**
//      * Enters idle mode on underlying motor controllers.
//      * 
//      * Replaces ChassisIdle in pre-2023 standard.
//      */
//     public Command c_idle() {
//         return Commands.parallel(leftMotors.c_idle(), rightMotors.c_idle());
//     }

//     /**
//      * moveCartesian with (x, y, turn) for timeout seconds.
//      * 
//      * Replaces ChassisMinimumDrive in pre-2023 standard.
//      */
//     @Deprecated // this is bad and only exists for feature parity with pre-2023 standard. use splines instead
//     public Command c_chassisMinimumDistance(double distance_meters, double speed_mps) {
//         return new Command() {
//             double left_motor_initial;
//             double right_motor_initial;
//             @Override
//             public void initialize() {
//                 this.left_motor_initial =  leftMotors .getSensorPositionRotations();
//                 this.right_motor_initial = rightMotors.getSensorPositionRotations();
//             }
//             @Override
//             public void execute() {
//                 setChassisVelocity(new ChassisSpeeds(speed_mps, 0, 0));
//             }
//             @Override
//             public boolean isFinished() {
//                 return (
//                     (leftMotors.getSensorPositionRotations()  -  this.left_motor_initial)/2
//                   + (rightMotors.getSensorPositionRotations() - this.right_motor_initial)/2
//                 ) > Math.abs(distance_meters * m_to_motorrots);
//             }
//         }.andThen(this.c_idle());
//     }

//     @Deprecated // this is bad and only exists for feature parity with pre-2023 standard. use splines instead
//     public Command c_chassisTurn(double angle_degrees, double max_turnspeed) {
//         return new Command() {
//             double left_motor_initial;
//             double right_motor_initial;
//             @Override
//             public void initialize() {
//                 this.left_motor_initial  = leftMotors.getSensorPositionRotations();
//                 this.right_motor_initial = rightMotors.getSensorPositionRotations();        
//             }
//             @Override
//             public void execute() {
//                 setChassisVelocity(new ChassisSpeeds(0, 0, max_turnspeed*Math.signum(angle_degrees)));
//             }
//             @Override
//             public boolean isFinished() {
//                 // when we turn, the wheels trace out a circle with trackwidth as a diameter. this checks that the wheels have traveled the right distance aroun the circle for our angle
//                 return (
//                     Math.abs(leftMotors .getSensorPositionRotations()-this. left_motor_initial)
//                    +Math.abs(rightMotors.getSensorPositionRotations()-this.right_motor_initial)
//                 ) > Math.abs(Math.PI * kinematics.trackWidthMeters * angle_degrees / 180 * m_to_motorrots);
//             }
//         }.andThen(() -> this.c_idle());
//     }
// }
