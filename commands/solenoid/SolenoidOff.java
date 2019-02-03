import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.solenoid.SolenoidSubsystem;

import SolenoidSubsystem.state;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidOff extends Command {
 
    public SolenoidOff(SolenoidSubsystem solenoid){
        this.solenoid = solenoid;
        requires(solenoid);
        solenoid.allOff();
        
    }

}