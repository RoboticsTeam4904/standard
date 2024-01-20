package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;

// to be replaced by Commandd
public class CreateAndDisown extends Command {
    private final Supplier<Command> commandDealer;
    private Command currentActiveCommand = null;
    
    public CreateAndDisown(String name, Supplier<Command> commandDealer) {
        this.commandDealer = commandDealer;
        setName(name);
        // do we need to add requirements? probably not, this ends immediately anyway
        // var reqs = this.commandDealer.get().getRequirements(); addRequirements(reqs.toArray(new Subsystem[reqs.size()]));
    }
    /**
     * Command that takes in a command factory and runs a new command from that
     * factory each time it is scheduled. This avoids the problems of command
     * factory code running on "construct" rather than on "initialize."
     * 
     * @param commandDealer The Command factory
     */
    public CreateAndDisown(Supplier<Command> commandDealer) {
        this("Unnamed CreateAndDisown", commandDealer);
    }
    @Override
    public void initialize() {
        currentActiveCommand = commandDealer.get();
        currentActiveCommand.schedule();
        setName("C&D: " + currentActiveCommand.getName());
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
        if (currentActiveCommand != null && wasInturrupted) {
            currentActiveCommand.cancel();
            // no need to worry about calling .end() on currentActiveCommand, as if the ending was caused by isFinished() -> true, then the scheduler will deal with calling .end() on the active command.
        }
        currentActiveCommand = null;
    }
}
