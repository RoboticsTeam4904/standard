package org.usfirst.frc4904.standard.humaninput;


import java.util.ArrayList;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A generic human interface class.
 * This is designed to be used to
 * bind commands to controllers.
 * bindCommands should only be called
 * during teleop init.
 *
 */
public abstract class HumanInput extends Command {
	protected final String name;
	private final ArrayList<SubsystemDefaultOverrider> subsystemDefaultOverriders = new ArrayList<SubsystemDefaultOverrider>();
	
	public HumanInput(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected void initialize() {
		bindCommands();
	}
	
	@Override
	protected void execute() {
		for (SubsystemDefaultOverrider subsystemDefaultOverrider : subsystemDefaultOverriders) {
			subsystemDefaultOverrider.execute();
		}
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}
	
	@Override
	protected void interrupted() {}
	
	/**
	 * A function where the driver's and operator's controls are bound to commands
	 * Can't be done in the constructor because constructors are called too early
	 */
	public abstract void bindCommands();
	
	/**
	 * 
	 * @param subsystem
	 * @param newDefault
	 * @param oldDefault
	 */
	protected void overrideDefault(Subsystem subsystem, Command newDefault, Command oldDefault) {
		for (SubsystemDefaultOverrider subsystemDefaultOverrider : subsystemDefaultOverriders) {
			if (subsystemDefaultOverrider.getSubsystem() == subsystem) {
				subsystemDefaultOverrider.changeNewDefault(newDefault);
				return;
			}
		}
		subsystemDefaultOverriders.add(new SubsystemDefaultOverrider(subsystem, newDefault, oldDefault));
	}
	
	private class SubsystemDefaultOverrider {
		private final Subsystem subsystem;
		private Command newDefault;
		private final Command oldDefault;
		
		public SubsystemDefaultOverrider(Subsystem subsystem, Command newDefault, Command oldDefault) {
			this.subsystem = subsystem;
			this.newDefault = newDefault;
			this.oldDefault = oldDefault;
		}
		
		public void execute() {
			if (subsystem.getCurrentCommand().getClass() == oldDefault.getClass()) {
				newDefault.start();
			}
		}
		
		public void changeNewDefault(Command newDefault) {
			if (newDefault.isRunning()) {
				newDefault.cancel();
			}
			this.newDefault = newDefault;
		}
		
		public Subsystem getSubsystem() {
			return subsystem;
		}
	}
}