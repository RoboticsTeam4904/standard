package org.usfirst.frc4904.standard.subsystems;

import org.usfirst.frc4904.standard.commands.solenoid.SolenoidSet;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
public class SolenoidSubsystem extends Subsystem {
    public enum SolenoidState {
        OFF(DoubleSolenoid.Value.kOff), FORWARD(DoubleSolenoid.Value.kForward), REVERSE(DoubleSolenoid.Value.kReverse)
		DoubleSolenoid.Value value;

		private SolenoidState(DoubleSolenoid.Value value) {
			this.value = value;
		}
	}
	protected DoubleSolenoid[] solenoids;
	protected SolenoidState state;

    public SolenoidSubsystem(DoubleSolenoid... solenoids){
        this.solenoids = solenoids;
        this.state = SolenoidState.OFF;
    }

	public void setState(SolenoidState state) {
		this.state = state;
	}

	public SolenoidState getState() {
		return state;
	}

	public void set(SolenoidState state) {
		for (DoubleSolenoid solenoid : solenoids) {
			solenoid.set(state.value);
		}
	}
    // public void extendAll(){
    //     for(DoubleSolenoid solenoid:solenoids){
    //         solenoid.set(DoubleSolenoid.Value.kForward);
    //     }
        
    // }
    // public void retractAll(){
    //     for(DoubleSolenoid solenoid:solenoids){
    //         solenoid.set(DoubleSolenoid.Value.kReverse);
    //     }
    // }
    // public void allOff(){
    //     for(DoubleSolenoid solenoid:solenoids){
    //         solenoid.set(DoubleSolenoid.Value.kOff);
    //     }
    // }
    public DoubleSolenoid[] getSolenoids(){
        return solenoids;
    }

    public void initDefaultCommand(){
        setDefaultCommand(new SolenoidSet(this, SolenoidState.FORWARD));
    }
}
