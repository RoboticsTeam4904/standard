package org.usfirst.frc4904.standard.subsystems.chassis;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.usfirst.frc4904.standard.custom.motorcontrollers.SmartMotorController;
import org.usfirst.frc4904.standard.subsystems.motor.SmartMotorSubsystem;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WestCoastDrive<SMC extends SmartMotorController> extends SubsystemBase {
    private final SmartMotorSubsystem<SMC> leftMotors;
    private final SmartMotorSubsystem<SMC> rightMotors;
    private final DifferentialDriveKinematics kinematics;
    private final double mps_to_rpm;
    /**
     * Represents a west coast drive chassis as a subsystem
     * 
     * @param trackWidthMeters          Track width, from horizontal center to center of the wheel, meters
     * @param motorToWheelGearRatio     Number of motor rotations for one wheel rotation. > 1 for gearing the motors down, eg. 13.88/1
     * @param wheelDiameterMeters
     * @param leftMotorSubsystem        SmartMotorSubsystem for the left wheels. Usually a TalonMotorSubsystem with two talons.
     * @param rightMotorSubsystem       SmartMotorSubsystem for the right wheels. Usually a TalonMotorSubsystem with two talons.
     */
    WestCoastDrive(double trackWidthMeters, double motorToWheelGearRatio, double wheelDiameterMeters, SmartMotorSubsystem<SMC> leftMotorSubsystem, SmartMotorSubsystem<SMC> rightMotorSubsystem) {
        leftMotors = leftMotorSubsystem;
        rightMotors = rightMotorSubsystem;
        kinematics = new DifferentialDriveKinematics(trackWidthMeters);  // 2023 robot has track width ~19.5 inches, 5 in wheel diameter
        mps_to_rpm = (Math.PI * wheelDiameterMeters) * motorToWheelGearRatio * 60;
        // TODO: add requirements?
    }
    
    /// methods
    // public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
    //     if (xSpeed != 0) throw new IllegalArgumentException("West Coast Drive cannot move laterally!");
    //     movePolar(ySpeed, 0, turnSpeed);
    // }

    // public void movePolar(double speed, double heading, double turnSpeed) {
    //     if (heading != 0) throw new IllegalArgumentException("West Coast Drive cannot move at a non-zero heading!");
    //     kinematics.toWheelSpeeds(ChassisSpeeds);
    // }
    // public void setWheelVelocities(WheelSpeeds wheelSpeeds) {
    //     this.leftMotors.set(wheelSpeeds.left);
    //     this.rightMotors.c_setVelocity()
    //     wheelSpeeds.left
    // }
    // public void setChassisVelocities(ChassisSpeeds chassisSpeeds) {
        
    // }

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
    public Command c_controlWheelVelocities(Supplier<WheelSpeeds> leftRightVelocitySupplier) {
        var command = new ParallelCommandGroup(
            this.leftMotors .c_controlRPM(() -> leftRightVelocitySupplier.get().left  * mps_to_rpm),
            this.rightMotors.c_controlRPM(() -> leftRightVelocitySupplier.get().right * mps_to_rpm)
        );
        command.addRequirements(this);
        return command;
    }
    /**
     * A forever command that pulls chassis movement
     * (forward speed and turn * radians/sec) from a * function and
     * sends them to the motor's closed-loop * control.
     */
    public Command c_controlChassisMovement(Supplier<ChassisSpeeds> chassisSpeedVelocitySupplier) {
        var cmd = this.run(() -> {
            final var wheelRPMs = kinematics.toWheelSpeeds(chassisSpeedVelocitySupplier.get());
            this.leftMotors .setRPM(wheelRPMs.leftMetersPerSecond  * mps_to_rpm);
            this.rightMotors.setRPM(wheelRPMs.rightMetersPerSecond * mps_to_rpm);
        });
        cmd.addRequirements(leftMotors);
        cmd.addRequirements(rightMotors);
        return cmd;
    }
}
