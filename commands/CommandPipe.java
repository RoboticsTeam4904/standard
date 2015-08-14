package org.usfirst.frc4904.cmdbased.commands;


import org.usfirst.frc4904.cmdbased.InPipable;
import org.usfirst.frc4904.cmdbased.OutPipable;
import org.usfirst.frc4904.logkitten.LogKitten;
import edu.wpi.first.wpilibj.command.Command;

public class CommandPipe extends Command {
	private final InPipable dataRead;
	private final OutPipable dataWrite;
	private final LogKitten logger;
	
	public CommandPipe(InPipable dataRead, OutPipable dataWrite) {
		this.dataRead = dataRead;
		this.dataWrite = dataWrite;
		logger = new LogKitten(LogKitten.LEVEL_VERBOSE, LogKitten.LEVEL_ERROR);
		logger.v("CommandPipe created");
	}
	
	protected void execute() {
		logger.d("CommandPipe executing");
		dataWrite.writePipe(dataRead.readPipe());
	}
	
	protected void end() {
		logger.v("CommandPipe ended");
	}
	
	protected void initialize() {
		logger.v("CommandPipe initialized");
	}
	
	protected void interrupted() {
		logger.w("CommandPipe interupted");
	}
	
	protected boolean isFinished() {
		return false;
	}
}
