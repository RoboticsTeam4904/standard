package org.usfirst.frc4904.standard.commands;


import edu.wpi.first.wpilibj.command.Command;

public class SingleOp extends Command {
	protected Runnable op;

	public SingleOp(Runnable op) {
		this.op = op;
	}

	@Override
	protected void initialize() {
		op.run();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
