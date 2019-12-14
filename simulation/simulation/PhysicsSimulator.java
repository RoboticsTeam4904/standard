package org.usfirst.frc4904.standard.simulation.simulation;

import java.util.ArrayList;
import org.usfirst.frc4904.standard.simulation.devices.Motor;

public class PhysicsSimulator extends Thread {

    public static PhysicsSimulator inst; // = new PhysicsSimulator().start();;

    public int timeStep;

    ArrayList<Motor> motors;

    public PhysicsSimulator(ArrayList<Motor> motors) {
        this.motors = motors;
    }

    @Override
    public void run() {
        while(true) {
            for(Motor m : this.motors) {
                System.out.println(m.angularVelocity);
            }
        }   
    }
}