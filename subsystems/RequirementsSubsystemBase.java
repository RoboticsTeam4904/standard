package org.usfirst.frc4904.standard.subsystems;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// TO DO: extend RequirementsSubsystemBase instead
// TO DO: custom trigger that takes a command factory
// TO DO: parallel command group that doesn't require it's own subsystems
public class RequirementsSubsystemBase extends SubsystemBase {
    public final RequirementsSubsystemBase[] atomicComponents;

    /**
     * SubsystemBase that handles requirements better.
     * 
     * Most subsystems should pass `this` only, or each internal subsystem. See below for details.
     * 
     * <br><br><br>
     * 
     * On 4904, we sometimes use subsystems containing other subsystems
     * (composition).
     * 
     * This confuses requirements. Say P composes A and B. Parallel commands on P
     * (that use both A and B) should not require both P AND A and B, because
     * otherwise a new command that just means to take over A will inadvertantly
     * cancel P which will cancel the action on B.
     * 
     * 
     * Therefore,
     * - if your internal subsystems should never be controlled seprately from one another, (eg. left and right drive motors of a chassis), just pass `this` to group them together.
     * - if your internal subsystems may be controlled seprately, (eg. a pivot+extension arm subsystem where one degree of freedom may be held while the other is manipulated), pass both `ArmPivotSubsystem` and `ArmExtensionSubsystem` but not `this`
     * 
     * @param atomicSubsystemRequirements
     */
    public RequirementsSubsystemBase(RequirementsSubsystemBase... atomicSubsystemRequirements) {
        atomicComponents = Arrays.stream(atomicSubsystemRequirements)
            .flatMap((rsb) -> Arrays.stream(rsb.atomicComponents))
            .toArray(RequirementsSubsystemBase[]::new);
    }

    /**
     * Constructs a command that runs an action once and finishes. Requires this
     * subsystem.
     *
     * @param action the action to run
     * @return the command
     * @see InstantCommand
     */
    public CommandBase runOnce(Runnable action) {
        return Commands.runOnce(action, atomicComponents);
    }

    /**
     * Constructs a command that runs an action every iteration until interrupted.
     * Requires this
     * subsystem.
     *
     * @param action the action to run
     * @return the command
     * @see RunCommand
     */
    public CommandBase run(Runnable action) {
        return Commands.run(action, atomicComponents);
    }

    /**
     * Constructs a command that runs an action once and another action when the
     * command is
     * interrupted. Requires this subsystem.
     *
     * @param start the action to run on start
     * @param end   the action to run on interrupt
     * @return the command
     * @see StartEndCommand
     */
    public CommandBase startEnd(Runnable start, Runnable end) {
        return Commands.startEnd(start, end, atomicComponents);
    }

    /**
     * Constructs a command that runs an action every iteration until interrupted,
     * and then runs a
     * second action. Requires this subsystem.
     *
     * @param run the action to run every iteration
     * @param end the action to run on interrupt
     * @return the command
     */
    public CommandBase runEnd(Runnable run, Runnable end) {
        return Commands.runEnd(run, end, atomicComponents);
    }

    public CommandBase parallel(Command... commands) {
        return new ParallelCommandGroup(commands);  // do not require the atomic components, so that if one subcommand gets cancelled, the other one does not.
    }
}
