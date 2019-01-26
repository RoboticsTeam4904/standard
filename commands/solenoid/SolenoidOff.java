import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.subsystems.solenoid.Solenoid;

import Solenoid.state;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidSet extends Command {
 
    public SolenoidSet(Solenoid solenoid){
        this.solenoid = solenoid;
        requires(solenoid);
        solenoid.allOff();
        
    }

}