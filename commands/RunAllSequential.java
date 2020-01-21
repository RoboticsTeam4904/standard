package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RunAllSequential extends SequentialCommandGroup {
    /**
     * Run a variable number of commands in sequence. This is a convenience class
     * for simple use of CommandGroup. For example, if you want to go 10 miles north
     * and then 5 miles east, use: new RunAllParallel(new GoNorth(10), new
     * GoEast(5)) If you will be using the same RunAllSequential more than once,
     * make a new class and extend CommandGroup.
     * 
     * @param commands The commands to be run in sequence
     */
    public RunAllSequential(CommandBase... commands) {
        super();
        setName("RunAllSequential[" + Arrays.stream(commands).map((CommandBase command) -> command.getName())
                .collect(Collectors.joining(" ")) + "]");
        addCommands(commands);
    }
}