// //ALWAYS FIELD RELATIVE
// package org.usfirst.frc4904.standard.subsystems.chassis;

// import java.util.function.Supplier;

// import org.usfirst.frc4904.robot.Robot;
// import org.usfirst.frc4904.robot.RobotMap;
// import org.usfirst.frc4904.robot.RobotMap.Metrics;
// import org.usfirst.frc4904.robot.RobotMap.PID.Drive;
// import org.usfirst.frc4904.robot.RobotMap.PID.Turn;
// import org.usfirst.frc4904.standard.custom.motioncontrollers.ezControl;
// import org.usfirst.frc4904.standard.custom.motioncontrollers.ezMotion;
// import org.usfirst.frc4904.standard.custom.motorcontrollers.CANTalonFX;
// import org.usfirst.frc4904.standard.custom.motorcontrollers.CustomCANSparkMax;
// import org.usfirst.frc4904.standard.subsystems.motor.SparkMaxMotorSubsystem;
// import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem;

// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.math.Pair;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.kinematics.SwerveModulePosition;
// import edu.wpi.first.math.kinematics.SwerveModuleState;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj.CAN;
// import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import com.ctre.phoenix6.signals.NeutralModeValue;
// import com.revrobotics.AbsoluteEncoder;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.MotorFeedbackSensor;
// import com.revrobotics.SparkAbsoluteEncoder;
// import com.revrobotics.SparkPIDController;
// import com.revrobotics.CANSparkBase.IdleMode;

// public class SwerveModule extends SubsystemBase{
//     public final CANTalonFX driveMotor;
//     public final TalonMotorSubsystem driveSubsystem;
//     public final CustomCANSparkMax turnMotor;
//     public final SparkMaxMotorSubsystem turnSubsystem;
//     public final SparkAbsoluteEncoder encoder;
//     public final SimpleMotorFeedforward driveFeedforward;
//     public final Translation2d modulePosition;
//     public final SparkPIDController turningPIDcontroller;

//     public SwerveModule(
//         CANTalonFX driveMotor,
//         CustomCANSparkMax turnMotor,
//         SparkAbsoluteEncoder encoder,
//         Translation2d modulePosition,
//         String name
//     ) {
//         this.driveMotor = driveMotor; //default is coast for drive, brake for turn. no voltage compensation
//         this.driveSubsystem = new TalonMotorSubsystem("drive-subsystem", NeutralModeValue.Coast, 0, driveMotor);
//         this.turnMotor = turnMotor;
//         this.turnMotor.setSmartCurrentLimit(20);
//         this.turnSubsystem = new SparkMaxMotorSubsystem("turn-subsystem", IdleMode.kBrake, 0, false, 0, turnMotor);
//         this.encoder = encoder;
//         encoder.setPositionConversionFactor(360);
//         this.driveFeedforward = new SimpleMotorFeedforward(RobotMap.PID.Drive.kS, RobotMap.PID.Drive.kV, RobotMap.PID.Drive.kA);
//         this.modulePosition = modulePosition;
//         this.setName(name);
//         this.turningPIDcontroller = turnMotor.getPIDController();
//         turningPIDcontroller.setPositionPIDWrappingEnabled(true); //loop at 360 degrees
//         turningPIDcontroller.setPositionPIDWrappingMaxInput(360);
//         turningPIDcontroller.setPositionPIDWrappingMinInput(0);
//         turningPIDcontroller.setFeedbackDevice((AbsoluteEncoder) encoder);
//         turningPIDcontroller.setP(RobotMap.PID.Turn.kP); //TODO: tune turning PID
//         turningPIDcontroller.setI(RobotMap.PID.Turn.kI);
//         turningPIDcontroller.setD(RobotMap.PID.Turn.kD);
//         //FIXME: set ff?
//         turningPIDcontroller.setOutputRange(-1,1);
//     }
//     public SwerveModulePosition getPosition(){
//         return new SwerveModulePosition(driveMotor.getRotorPosition().getValue()*RobotMap.Metrics.Chassis.WHEEL_DIAMETER_METERS/RobotMap.Metrics.Chassis.GEAR_RATIO_DRIVE,
//         new Rotation2d(getAbsoluteAngle())); //TODO: use circumference of wheel instead?
//     }
        
//     //CAN BE EITHER OPEN OR CLOSED-LOOP CONTROL
//     //takes in target velocities and angles and returns a command that will move the module to that state
//     public void setTargetState(SwerveModuleState target, boolean openloop){ //does NOT take in onarrival command dealer, should dealt with when writing autons
//         SwerveModuleState optimizedTarget = SwerveModuleState.optimize(target, getAbsoluteRotation());

//         if(openloop){
//             driveMotor.setVoltage(driveFeedforward.calculate(optimizedTarget.speedMetersPerSecond));
//         }else{
//             driveMotor.setVoltage(0); //TODO: add feedback control for drive wheels
//         }
//         turningPIDcontroller.setReference(optimizedTarget.angle.getDegrees(), CANSparkMax.ControlType.kPosition);

//     }
//     public SwerveModuleState getState(){
//         return new SwerveModuleState(driveMotor.get(), Rotation2d.fromDegrees(getAbsoluteAngle()));
//     }
//     //TODO: make sure this outputs correctly, as there are a few possible bad outputs it could give (i.e. getabsolutePosition() is in wrong units is in radians or rotations rather than degrees)
//     public double getAbsoluteAngle(){
//         //should output from 0 to 360
//         var pos = encoder.getPosition();
//         return pos; //TODO: make sure this in degrees and from 0-360
//     }
//     public Rotation2d getAbsoluteRotation(){
//         return Rotation2d.fromDegrees(getAbsoluteAngle());
//     }   
// }
