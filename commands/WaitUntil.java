package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.Supplier;

public class WaitUntil extends Command {

    protected final Supplier<Boolean> stopCondition;

    public WaitUntil(Supplier<Boolean> stopCondition) {
        this.stopCondition = stopCondition;
    }

    @Override
    public boolean isFinished() {
        return stopCondition.get();
    }
}
