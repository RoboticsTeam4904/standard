package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class TriggerCommandFactory extends CommandBase {
    private final Supplier<Command>[] commandDealers;
    private Command[] currentActiveCommands = null;
    private final String name;
    
    public TriggerCommandFactory(String name, Supplier<Command> commandDealer) { this(name, commandDealer, null); }
    public TriggerCommandFactory(String name, Supplier<Command>... commandDealers) {
        this.commandDealers = commandDealers;
        this.currentActiveCommands = new Command[commandDealers.length];
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
    public TriggerCommandFactory(Supplier<Command>... commandDealers) {
        this("Unnamed TriggerCommandFactory", commandDealers);
    }
    public TriggerCommandFactory(Supplier<Command> commandDealer) { this("Unnamed TriggerCommandFactory", commandDealer); }

    @Override
    public void initialize() {
        for (int i=0; i<commandDealers.length; i++) {
            if (commandDealers[i] == null) continue;
            currentActiveCommands[i] = commandDealers[i].get();
            currentActiveCommands[i].schedule();
        }
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
        if (wasInturrupted) {
            for (int i=0; i<commandDealers.length; i++) {
                if (currentActiveCommands[i] == null) continue;
                currentActiveCommands[i].cancel();
            }
        }
    }
}
