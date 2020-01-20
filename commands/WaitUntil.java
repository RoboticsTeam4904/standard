package org.usfirst.frc4904.standard.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitUntil extends CommandBase {
	protected final Supplier<Boolean> stopCondition;

	public WaitUntil(Supplier<Boolean> stopCondition) {
		this.stopCondition = stopCondition;
	}

	@Override
	public boolean isFinished() {
		return stopCondition.get();
	}
}
