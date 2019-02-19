package org.usfirst.frc4904.standard.commands;


import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public class IdleVerbosely extends InjectedCommand {
	
	public IdleVerbosely(Command previous) {
		super(previous);
		LogKitten.v("IdleWithLogging(" + getName() + "): Construct");
	}
	
	public IdleVerbosely(String name, Command previous) {
		super(name, previous);
		LogKitten.v("IdleWithLogging(" + getName() + "): Construct");
	}
	
	public IdleVerbosely(double timeout, Command previous) {
		super(timeout, previous);
		LogKitten.v("IdleWithLogging(" + getName() + "): Construct");
	}
	
	public IdleVerbosely(String name, double timeout, Command previous) {
		super(name, timeout, previous);
		LogKitten.v("IdleWithLogging(" + getName() + "): Construct");
	}
	
	@Override
	protected void onInitialize() {
		LogKitten.v("IdleWithLogging(" + getName() + "): Initialize");
	}
	
	@Override
	protected void onInterrupted() {
		LogKitten.v("IdleWithLogging(" + getName() + "): Interrupted");
	}
	
	@Override
	protected void onEnd() {
		LogKitten.v("IdleWithLogging(" + getName() + "): End");
	}
	
	@Override
	protected void execute() {
		LogKitten.v("IdleWithLogging(" + getName() + "): Execute");
	}
	
	@Override
	protected boolean isFinished() {
		LogKitten.v("IdleWithLogging(" + getName() + "): isFinished?");
		return false;
	}
}
