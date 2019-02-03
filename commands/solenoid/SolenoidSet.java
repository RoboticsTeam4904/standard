package org.usfirst.frc4904.standard.commands.solenoid;

import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem.SolenoidState;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidSet extends Command {
	protected final SolenoidSubsystem system;
	protected final SolenoidState state;

    public SolenoidSet(SolenoidSubsystem system, SolenoidState state){
		this.system = system;
		this.state = state;
        requires(system);
        
        
    }
    public void set(SolenoidState state){
        system.setState(state);
        LogKitten.d(state); //TODO: make a better message
    }

    @Override
    public void execute(){    
        switch (system.getState()){
            case Forward: 
                system.extendAll();
                LogKitten.d("all solenoids extended");
                break;
            case Reverse:
                system.retractAll();
                LogKitten.d("all solenoids retracted");
                break;

            case Off: 
                system.allOff();
                LogKitten.d("all solenoids turned off");
                break;
        }
    }
    @Override
	protected void interrupted() {
        LogKitten.d("SolenoidSet interupted (solenoid state undefined)");
        end();
	}
    @Override
	protected void end() {
		system.allOff();
		LogKitten.d("MotorSet ended (solenoid states set to off)");
    }
    
    @Override
	protected boolean isFinished() {
		return false;
	}

}
