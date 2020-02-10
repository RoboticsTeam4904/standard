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
 * This command creates a simple spline to follow a Trajectory using a SensorDrive. Note that while the nextCommand to run after the robot finishes driving and the initialPos are both configurable, they will almost always be set automatically.
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
    public double MAX_SPEED_MS;
		public double MAX_ACCEL_MSS;
		public double RAMSETE_B;
    public double RAMSETE_ZETA;
    
    /**
     * Constructor for the auto constants.
     * @param MAX_SPEED_MS max speed of the robot. Meters per second.
     * @param MAX_ACCEL_MSS max acceleration of the robot. Meters per second squared.
     * @param RAMSETE_B B coefficient. Should be 2.0 for most contexts.
     * @param kRamseteZeta Zeta coefficient. Should be 0.7 for most contexts.
     */
    public SplineAutoConstants(double MAX_SPEED_MS, double MAX_ACCEL_MSS, double RAMSETE_B, double RAMSETE_ZETA) {
      this.MAX_SPEED_MS = MAX_SPEED_MS;
      this.MAX_ACCEL_MSS = MAX_ACCEL_MSS;
      this.RAMSETE_B = RAMSETE_B;
      this.RAMSETE_ZETA = RAMSETE_ZETA;
    }
  }

  /**
   * Class to store drive constants used for Ramsete pathing.
   */
  public static class SplineDriveConstants {
    public double KS;
		public double KV;
    public double KA;
    public double TRACK_WIDTH;
		public DifferentialDriveKinematics kDriveKinematics;
    public double KP_DRIVE_VEL;
    
    /**
     * Constructor for the drive constants.
     * @param KS ksVolts (from characterization).
     * @param KV kvVoltSecondsPerMeter (from characterization).
     * @param KA kaVoltSecondsSquaredPerMeter (from characterization).
     * @param TRACK_WIDTH kTrackwidthMeters (from characterization).
     * @param KP_DRIVE_VEL kPDriveVel (from characterization).
     */
    public SplineDriveConstants(double KS, double KV, double KA, double TRACK_WIDTH, double KP_DRIVE_VEL){
      this.KS = KS;
      this.KV = KV;
      this.KA = KA;
      this.TRACK_WIDTH = TRACK_WIDTH;
      this.kDriveKinematics = new DifferentialDriveKinematics(TRACK_WIDTH);
      this.KP_DRIVE_VEL = KP_DRIVE_VEL;
    }
  }
}
