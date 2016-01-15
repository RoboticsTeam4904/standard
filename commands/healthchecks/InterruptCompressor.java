package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Command;

public class InterruptCompressor extends Command {
	private final Compressor compressor;
	
	public InterruptCompressor(Compressor compressor) {
		this.compressor = compressor;
	}
	
	protected void initialize() {
		compressor.stop();
	}
	
	protected void execute() {}
	
	protected boolean isFinished() {
		return true; // Once we stop the compressor, we are done
	}
	
	protected void end() {}
	
	protected void interrupted() {}
}
