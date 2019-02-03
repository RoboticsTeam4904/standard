


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.SolenoidSubsystem;

// import Solenoid.State;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidSet extends Command {
    SolenoidSubsystem solenoid;
    public SolenoidSet(SolenoidSubsystem solenoid){
        this.solenoid = solenoid;
        requires(solenoid);
        
        
    }
    public void set(State state){
        solenoid.State = state;
        LogKitten.d("Solenoids set to "+String(solenoid.State));
    }

    @Override
    public void execute(){    
        switch (solenoid.State){
            case Forward: 
                solenoid.extendAll();
                solenoid.State = Forward;
                LogKitten.d("all solenoids extended");
                break;
            case Reverse:
                solenoid.retractAll();
                solenoid.State = Reverse;
                LogKitten.d("all solenoids retracted");
                break;

            case Off: 
                solenoid.allOff();
                solenoid.State = Off;
                LogKitten.d("all solenoids turned off");
                break;
        }
    }
    @Override
	protected void interrupted() {
		LogKitten.d("SolenoidSet interupted (solenoid state undefined)");
	}
    @Override
	protected void end() {
		solenoid.allOff();
		LogKitten.d("MotorSet ended (solenoid states set to off)");
    }
    
    @Override
	protected boolean isFinished() {
		return false;
	}

}