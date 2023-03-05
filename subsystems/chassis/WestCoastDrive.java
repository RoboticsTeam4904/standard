package org.usfirst.frc4904.standard.subsystems.chassis;

import java.util.function.Supplier;

import org.usfirst.frc4904.standard.custom.motorcontrollers.SmartMotorController;
import org.usfirst.frc4904.standard.subsystems.motor.SmartMotorSubsystem;

import edu.wpi.first.math.controller.DifferentialDriveWheelVoltages;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WestCoastDrive<MotorControllerType extends SmartMotorController> extends SubsystemBase {
    private final SmartMotorSubsystem<MotorControllerType> leftMotors;
    private final SmartMotorSubsystem<MotorControllerType> rightMotors;
    private final DifferentialDriveKinematics kinematics;
    private final double mps_to_rpm;
    private final double m_to_motorrots;

    /**
     * Represents a west coast drive chassis as a subsystem
     * 
     * @param trackWidthMeters          Track width, from horizontal center to center of the wheel, meters
     * @param motorToWheelGearRatio     Number of motor rotations for one wheel rotation. > 1 for gearing the motors down, eg. 13.88/1
     * @param wheelDiameterMeters
     * @param leftMotorSubsystem        SmartMotorSubsystem for the left wheels. Usually a TalonMotorSubsystem with two talons.
     * @param rightMotorSubsystem       SmartMotorSubsystem for the right wheels. Usually a TalonMotorSubsystem with two talons.
     */
    public WestCoastDrive(double trackWidthMeters, double motorToWheelGearRatio, double wheelDiameterMeters, SmartMotorSubsystem<MotorControllerType> leftMotorSubsystem, SmartMotorSubsystem<MotorControllerType> rightMotorSubsystem) {
        leftMotors = leftMotorSubsystem;
        rightMotors = rightMotorSubsystem;
        kinematics = new DifferentialDriveKinematics(trackWidthMeters);  // 2023 robot has track width ~19.5 inches, 5 in wheel diameter
        mps_to_rpm = (Math.PI * wheelDiameterMeters) * motorToWheelGearRatio * 60;
        m_to_motorrots = 1/wheelDiameterMeters*motorToWheelGearRatio;
        // TODO: add requirements?
    }
    
    /// methods

    /**
     * Convention: +x forwards, +y right, +z down
     * 
     * @param xSpeed
     * @param ySpeed
     * @param turnSpeed
     */
    @Deprecated
    public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
        if (ySpeed != 0) throw new IllegalArgumentException("West Coast Drive cannot move laterally!");
        setChassisVelocity(new ChassisSpeeds(xSpeed, ySpeed, turnSpeed));
    }
    @Deprecated
    public void movePolar(double speed, double heading, double turnSpeed) {
        if (heading != 0) throw new IllegalArgumentException("West Coast Drive cannot move at a non-zero heading!");
        setChassisVelocity(new ChassisSpeeds(speed, 0, turnSpeed));
    }
    // TODO: error if PIDF has not been configured in the underlying motorsubsystems
    public void setWheelVelocities(DifferentialDriveWheelSpeeds wheelSpeeds) {
        this.leftMotors .setRPM(wheelSpeeds.leftMetersPerSecond  * mps_to_rpm);
        this.rightMotors.setRPM(wheelSpeeds.rightMetersPerSecond * mps_to_rpm);
    }
    public void setChassisVelocity(ChassisSpeeds chassisSpeeds) {
        final var wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
        setWheelVelocities(wheelSpeeds);
    }
    public void setWheelVoltages(DifferentialDriveWheelVoltages wheelVoltages) {
        this.leftMotors.setVoltage(wheelVoltages.left);
        this.rightMotors.setVoltage(wheelVoltages.right);
    }

    /**
     * A forever command that pulls drive velocities from a function and sends
     * them to the motor's closed-loop control.
     * 
     * For composition reasons, leftRightVelocitySupplier gets called twice per frame. OPTIMIZABLE
     *
     * @param leftRightVelocity A function that returns a pair containing the
     *                          left and right velocities, m/s.
     * 
     * @return the command to be scheduled
     */
    public Command c_controlWheelVelocities(Supplier<DifferentialDriveWheelSpeeds> leftRightVelocitySupplier) {
        var cmd = this.run(() -> setWheelVelocities(leftRightVelocitySupplier.get()));    // this.run() runs repeatedly
        cmd.addRequirements(leftMotors);
        cmd.addRequirements(rightMotors);
        return cmd;
    }
    /**
     * A forever command that pulls chassis movement
     * (forward speed and turn * radians/sec) from a * function and
     * sends them to the motor's closed-loop * control.
     */
    public Command c_controlChassisVelocity(Supplier<ChassisSpeeds> chassisSpeedsSupplier) {
        var cmd = this.run(() -> setChassisVelocity(chassisSpeedsSupplier.get()));    // this.run() runs repeatedly
        cmd.addRequirements(leftMotors);
        cmd.addRequirements(rightMotors);
        return cmd;
    }
    /**
     * A forever command that pulls left and right wheel voltages from a
     * function.
     */
    public Command c_controlWheelVoltages(Supplier<DifferentialDriveWheelVoltages> wheelVoltageSupplier) {
        var cmd = this.run(() -> setWheelVoltages(wheelVoltageSupplier.get()));    // this.run() runs repeatedly
        cmd.addRequirements(leftMotors);
        cmd.addRequirements(rightMotors);
        return cmd;
    }


    // convienence commands for feature parity with pre-2023 standard
    /**
     * moveCartesian with (x, y, turn) for timeout seconds.
     * 
     * Replaces ChassisConstant in pre-2023 standard.
     */
    @Deprecated
    public Command c_chassisConstant(double x, double y, double turn, double timeout) {
        return this.run(() -> moveCartesian(x, y, turn)).withTimeout(timeout);
    }
    /**
     * Enters idle mode on underlying motor controllers.
     * 
     * Replaces ChassisIdle in pre-2023 standard.
     */
    public Command c_idle() {
        return Commands.parallel(leftMotors.c_idle(), rightMotors.c_idle());    // TODO do we need to require the chassis subsystem for this? else chassis will read as unrequired, but all of it's subcomponents will be required.
    }

    /**
     * moveCartesian with (x, y, turn) for timeout seconds.
     * 
     * Replaces ChassisMinimumDrive in pre-2023 standard.
     */
    public Command c_chassisMinimumDistance(double distance_meters, double speed_mps) {
        var average_motor_rotations = ((this.leftMotors.getSensorPositionRotations()+this.rightMotors.getSensorPositionRotations())/2);
        return this.run(() -> setChassisVelocity(new ChassisSpeeds(speed_mps, 0, 0)))
            .until(() -> (((this.leftMotors.getSensorPositionRotations()+this.rightMotors.getSensorPositionRotations())/2) - average_motor_rotations > Math.abs(distance_meters * m_to_motorrots) ))
            .andThen(() -> this.c_idle());
    }



    // TODO: write the others. goodfirstissue
}
