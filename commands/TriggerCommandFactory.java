package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class TriggerCommandFactory extends CommandBase {
    private final Supplier<Command> commandDealer;
    private Command currentActiveCommand = null;
    private final String name;
    
    // basically a Commands.runOnce(commandDealer.get().schedule()) and it's named
    public TriggerCommandFactory(String name, Supplier<Command> commandDealer) {
        this.commandDealer = commandDealer;
        this.name = name;
        setName(name);
        // TODO: do we need to add requirements?
        // var reqs = this.commandDealer.get().getRequirements(); addRequirements(reqs.toArray(new Subsystem[reqs.size()]));
    }
    /**
     * Command that takes in a command factory and runs a new command from that
     * factory each time it is scheduled. This avoids the problems of command
     * factory code running on "construct" rather than on "initialize."
     * 
     * @param commandDealer The Command factory
     */
    public TriggerCommandFactory(Supplier<Command> commandDealer) {
        this("Unnamed TriggerCommandFactory", commandDealer);
    }
    @Override
    public void initialize() {
        currentActiveCommand = commandDealer.get();
        currentActiveCommand.schedule();
    }
    @Override
    public void execute() {
        // no need to do anything here, as the scheduler should execute as needed.
    }
    @Override
    public boolean isFinished() {
        return true;
    }
    public void end(boolean wasInturrupted) {
    }
}
