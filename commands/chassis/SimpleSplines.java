// Based on software from FIRST and WPI

package org.usfirst.frc4904.standard.commands.chassis;

import org.usfirst.frc4904.standard.subsystems.chassis.SensorDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * This command creates a simple spline to follow a Trajectory using a SensorDrive. Note that while the nextCommand to run after the robot finishes driving and the initialPos are both configuraable, they will almost always be set automatically.
 */
public class SimpleSplines extends SequentialCommandGroup {
  /**
   * @param robotDrive the SensorDrive used to follow the trajectory.
   * @param trajectory the Trajectory to follow.
   * @param nextCommand the command to run immediately following the spline completion. In most cases, this should be setting the chassis voltages all to 0.
   * @param initialPos the initial pose to reset the robot's odometry to.
   */
  public SimpleSplines(SensorDrive robotDrive, Trajectory trajectory, Command nextCommand, Pose2d initialPos){
    super(new InstantCommand(() -> robotDrive.resetOdometry(initialPos)),
      new RamseteCommand(
        trajectory,
        robotDrive::getPose,
        new RamseteController(robotDrive.getAutoConstants().kRamseteB, robotDrive.getAutoConstants().kRamseteZeta),
        new SimpleMotorFeedforward(robotDrive.getDriveConstants().ksVolts,
        robotDrive.getDriveConstants().kvVoltSecondsPerMeter,
        robotDrive.getDriveConstants().kaVoltSecondsSquaredPerMeter),
        robotDrive.getDriveConstants().kDriveKinematics,
        robotDrive::getWheelSpeeds,
        new PIDController(robotDrive.getDriveConstants().kPDriveVel, 0, 0),
        new PIDController(robotDrive.getDriveConstants().kPDriveVel, 0, 0),
        robotDrive::tankDriveVolts, robotDrive.getDriveBase().getMotors()), nextCommand);
    addRequirements(robotDrive.getDriveBase());
  } 

  /**
   * @param robotDrive the SensorDrive used to follow the trajectory.
   * @param trajectory the Trajectory to follow.
   * @param initialPos the initial pose to reset the robot's odometry to.
   */
  public SimpleSplines(SensorDrive robotDrive, Trajectory trajectory, Pose2d initialPos){
    this(robotDrive, trajectory, new InstantCommand(() -> robotDrive.tankDriveVolts(0, 0)), initialPos);
  }

  /**
   * @param robotDrive the SensorDrive used to follow the trajectory.
   * @param trajectory the Trajectory to follow.
   * @param nextCommand the command to run immediately following the spline completion. In most cases, this should be setting the chassis voltages all to 0.
   */
  public SimpleSplines(SensorDrive robotDrive, Trajectory trajectory, Command nextCommand){
    this(robotDrive, trajectory, nextCommand, trajectory.getStates().get(0).poseMeters);
  }

  /**
   * @param robotDrive the SensorDrive used to follow the trajectory.
   * @param trajectory the Trajectory to follow.
   */
  public SimpleSplines(SensorDrive robotDrive, Trajectory trajectory){
    this(robotDrive, trajectory, new InstantCommand(() -> robotDrive.tankDriveVolts(0, 0)));
  }

  /**
   * Class to store autonomous constants used for Ramsete Pathing.
   */
  public static class SplineAutoConstants {
    public double kMaxSpeedMetersPerSecond;
		public double kMaxAccelerationMetersPerSecondSquared;
		public double kRamseteB;
		public double kRamseteZeta;

    public SplineAutoConstants(double kMaxSpeedMetersPerSecond, double kMaxAccelerationMetersPerSecondSquared, double kRamseteB, double kRamseteZeta) {
      this.kMaxSpeedMetersPerSecond = kMaxSpeedMetersPerSecond;
      this.kMaxAccelerationMetersPerSecondSquared = kMaxAccelerationMetersPerSecondSquared;
      this.kRamseteB = kRamseteB;
      this.kRamseteZeta = kRamseteZeta;
    }
  }

  /**
   * Class to store drive constants used for Ramsete pathing.
   */
  public static class SplineDriveConstants {
    public double ksVolts;
		public double kvVoltSecondsPerMeter;
    public double kaVoltSecondsSquaredPerMeter;
    public double kTrackwidthMeters;
		public DifferentialDriveKinematics kDriveKinematics;
    public double kPDriveVel;
    
    public SplineDriveConstants(double ksVolts, double kvVoltSecondsPerMeter, double kaVoltSecondsSquaredPerMeter, double kTrackwidthMeters, double kPDriveVel){
      this.ksVolts = ksVolts;
      this.kvVoltSecondsPerMeter = kvVoltSecondsPerMeter;
      this.kaVoltSecondsSquaredPerMeter = kaVoltSecondsSquaredPerMeter;
      this.kTrackwidthMeters = kTrackwidthMeters;
      this.kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
      this.kPDriveVel = kPDriveVel;
    }
  }
}
