package org.usfirst.frc4904.standard.commands;


import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;

public class WaitUntil extends Command {
	protected final Supplier<Boolean> stopCondition;

	public WaitUntil(Supplier<Boolean> stopCondition) {
		this.stopCondition = stopCondition;
	}

	@Override
	protected boolean isFinished() {
		return stopCondition.get();
	}
}
