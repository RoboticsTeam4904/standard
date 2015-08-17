package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.custom.CommandSendableChooser;
import org.usfirst.frc4904.cmdbased.custom.TypedNamedSendableChooser;
import org.usfirst.frc4904.cmdbased.humaninterface.Driver;
import org.usfirst.frc4904.cmdbased.humaninterface.Operator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class RobotBase extends IterativeRobot {
	protected Command teleopCommand;
	protected Command autonomousCommand;
	protected CommandSendableChooser autoChooser;
	protected TypedNamedSendableChooser<Driver> driverChooser;
	protected TypedNamedSendableChooser<Operator> operatorChooser;
	
	protected void displayChoosers() {
		// Display choosers on SmartDashboard
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		SmartDashboard.putData("Driver control scheme chooser", driverChooser);
		SmartDashboard.putData("Operator control scheme chooser", operatorChooser);
	}
	
	public void robotInit() {
		// Initialize choosers
		autoChooser = new CommandSendableChooser();
		driverChooser = new TypedNamedSendableChooser<Driver>();
		operatorChooser = new TypedNamedSendableChooser<Operator>();
	}
	
	public boolean isEnabledOperatorControl() {
		return isEnabled() && isOperatorControl();
	}
	
	public boolean isEnabledAutonomous() {
		return isEnabled() && isAutonomous();
	}
}