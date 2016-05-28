package org.usfirst.frc4904.standard;


import org.usfirst.frc4904.standard.commands.healthchecks.AbstractHealthCheck;
import org.usfirst.frc4904.standard.commands.healthchecks.CheckHealth;
import org.usfirst.frc4904.standard.custom.CommandSendableChooser;
import org.usfirst.frc4904.standard.custom.TypedNamedSendableChooser;
import org.usfirst.frc4904.standard.humaninput.Driver;
import org.usfirst.frc4904.standard.humaninput.Operator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * IterativeRobot is normally the base class for command based code,
 * but we think certain features will almost always be needed,
 * so we created the CommandRobotBase class.
 * Robot should extend this instead of iterative robot.
 */
public abstract class CommandRobotBase extends IterativeRobot {
	protected Command teleopCommand;
	private Command autonomousCommand;
	protected CheckHealth healthcheckCommand;
	protected CommandSendableChooser autoChooser;
	protected TypedNamedSendableChooser<Driver> driverChooser;
	protected TypedNamedSendableChooser<Operator> operatorChooser;
	
	/**
	 * This displays our choosers.
	 * The default choosers are for autonomous type, driver control, sand operator control.
	 */
	protected final void displayChoosers() {
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		SmartDashboard.putData("Driver control scheme chooser", driverChooser);
		SmartDashboard.putData("Operator control scheme chooser", operatorChooser);
	}
	
	/**
	 * This initializes the entire robot.
	 * It is called by WPILib on robot code launch.
	 * Year-specific code should be written in the initialize function.
	 */
	@Override
	public final void robotInit() {
		// Initialize choosers
		autoChooser = new CommandSendableChooser();
		driverChooser = new TypedNamedSendableChooser<Driver>();
		operatorChooser = new TypedNamedSendableChooser<Operator>();
		// Run user-provided initialize function
		initialize();
		// Start health checks
		if (healthcheckCommand != null) {
			healthcheckCommand.start();
		}
		// Display choosers on SmartDashboard
		displayChoosers();
	}
	
	/**
	 * Function for year-specific code to be run on robot code launch.
	 * setHealthChecks should be called here if needed.
	 */
	public abstract void initialize();
	
	/**
	 * This initializes the teleoperated portion of the robot code.
	 * It is called by WPILib on teleop enable.
	 * Year-specific code should be written in the teleopInitialize() function.
	 */
	@Override
	public final void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		if (driverChooser.getSelected() != null) {
			LogKitten.d("Loading driver " + driverChooser.getSelected().getName());
			driverChooser.getSelected().bindCommands();
		}
		if (operatorChooser.getSelected() != null) {
			LogKitten.d("Loading operator " + operatorChooser.getSelected().getName());
			operatorChooser.getSelected().bindCommands();
		}
		teleopInitialize();
		if (teleopCommand != null) {
			teleopCommand.start();
		}
	}
	
	/**
	 * Function for year-specific code to be run on teleoperated initialize.
	 * teleopCommand should be set in this function.
	 */
	public abstract void teleopInitialize();
	
	/**
	 * This function is called by WPILib periodically during teleop.
	 * Year-specific code should be written in the teleopExecute() function.
	 */
	@Override
	public final void teleopPeriodic() {
		Scheduler.getInstance().run();
		teleopExecute();
		alwaysExecute();
	}
	
	/**
	 * Function for year-specific code to be run during teleoperated time.
	 */
	public abstract void teleopExecute();
	
	/**
	 * This initializes the autonomous portion of the robot code.
	 * It is called by WPILib on auton enable.
	 * Year-specific code should be written in the autonomousInitialize() function.
	 */
	@Override
	public final void autonomousInit() {
		autonomousCommand = autoChooser.getSelected();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		autonomousInitialize();
	}
	
	/**
	 * Function for year-specific code to be run on autonomous initialize.
	 */
	public abstract void autonomousInitialize();
	
	/**
	 * This function is called by WPILib periodically during auton.
	 * Year-specific code should be written in the autonomousExecute() function.
	 */
	@Override
	public final void autonomousPeriodic() {
		Scheduler.getInstance().run();
		autonomousExecute();
		alwaysExecute();
	}
	
	/**
	 * Function for year-specific code to be run during autonomous.
	 */
	public abstract void autonomousExecute();
	
	/**
	 * This function is called by WPILib when the robot is disabled.
	 * Year-specific code should be written in the disabledInitialize() function.
	 */
	@Override
	public final void disabledInit() {
		if (teleopCommand != null) {
			teleopCommand.cancel();
		}
		disabledInitialize();
	}
	
	/**
	 * Function for year-specific code to be run on disabled initialize.
	 */
	public abstract void disabledInitialize();
	
	/**
	 * This function is called by WPILib periodically while disabled.
	 * Year-specific code should be written in the disabledExecute() function.
	 */
	@Override
	public final void disabledPeriodic() {
		Scheduler.getInstance().run();
		disabledExecute();
		alwaysExecute();
	}
	
	/**
	 * Function for year-specific code to be run while disabled.
	 */
	public abstract void disabledExecute();
	
	/**
	 * This function is called by WPILib when the robot is in test mode.
	 * Year-specific-code should be written in the disabledInitialize() function.
	 */
	@Override
	public final void testInit() {
		testInitialize();
	}
	
	/**
	 * Function for year-specific code to be run on disabled initialize.
	 */
	public abstract void testInitialize();
	
	/**
	 * This function is called by WPILib periodically while in test mode.
	 * Year-specific code should be written in the testExecute() function.
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
		testExecute();
		alwaysExecute();
	}
	
	/**
	 * Function for year-specific code to be run while in test mode.
	 */
	public abstract void testExecute();
	
	/**
	 * Function for year-specific code to be run in every robot mode.
	 */
	public abstract void alwaysExecute();
	
	/**
	 * Sets the health checks for the robot.
	 * This should be called in initialize.
	 *
	 * @param healthChecks
	 */
	public final void setHealthChecks(AbstractHealthCheck... healthChecks) {
		healthcheckCommand = new CheckHealth(healthChecks);
	}
	
	/**
	 * @return True if the robot is enabled and is in operator control.
	 */
	public final boolean isEnabledOperatorControl() {
		return isEnabled() && isOperatorControl();
	}
	
	/**
	 *
	 * @return True if the robot is enabled and is in autonomous mode.
	 */
	public final boolean isEnabledAutonomous() {
		return isEnabled() && isAutonomous();
	}
}
