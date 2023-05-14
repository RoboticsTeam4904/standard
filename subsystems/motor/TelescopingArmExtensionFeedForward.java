package org.usfirst.frc4904.standard.subsystems.motor;

import edu.wpi.first.math.controller.ElevatorFeedforward;

/**
 * Computes feed-forward values for an elevator motor, which is mounted on a varying angle.
 * Internally composes a wpilib ElevatorFeedForward.
 */
public class TelescopingArmExtensionFeedForward {
    public final double ks;
    public final double kg_vertical;
    public final double kv;
    public final double ka;
    private final ElevatorFeedforward elevFF;
    
    /**
     * Computes elevator feed-forward values with a varying cg based on rotation.
     * 
     * @param ks          Static gain: volts needed to overcome static friction
     * @param vertical_kg Gravity gain: usually found by characterizing the arm as
     *                    an elevator in the vertical orientation, equals volts
     *                    needed to hold the arm up against gravity when orinteted
     *                    vertically (max cg).
     * @param kv          Velocity gain
     * @param ka          Acceleration gain. Zero is usually an okay default.
     */
    public TelescopingArmExtensionFeedForward(double ks, double vertical_kg, double kv, double ka) {
        this.ks = ks;
        this.kg_vertical = vertical_kg;
        this.kv = kv;
        this.ka = ka;
        this.elevFF = new ElevatorFeedforward(ks, 0, kv, ka);
    }
    /**
     * @param posRads                 Position of the arm, in radians from
     *                                horizontal. 0 = horizontal, PI/2 = vertical.
     * @param velMetersPerSec         Velocity setpoint
     * @param acclMetersPerSecSquared Acceleration setpoint
     */
    public double calculate(double posRads, double velMetersPerSec, double acclMetersPerSecSquared) {
        return elevFF.calculate(velMetersPerSec, acclMetersPerSecSquared) + Math.sin(posRads) * kg_vertical;
    }
    /**
     * @param posRads                 Position of the arm, in radians from
     *                                horizontal. 0 = horizontal, PI/2 = vertical.
     * @param velMetersPerSec         Velocity setpoint
     */
    public double calculate(double posRads, double velMetersPerSec) {
        return calculate(posRads, velMetersPerSec, 0);
    }

    // Util methods to calculate max achievable velocity/acceleration from the
    // other setpoint. Allegedly obtained from rearranging the above equations;
    // see WPILib ElevatorFeedForward for details. 
    /**
     * Calculates the maximum achievable velocity given a maximum voltage supply
     * and an acceleration. Useful for ensuring that velocity and acceleration
     * constraints for a trapezoidal profile are simultaneously achievable -
     * enter the acceleration constraint, and this will give you a
     * simultaneously-achievable velocity constraint.
     */
    public double maxAchievableVelocity(double maxVoltage, double posRads, double acceleration) {
        return elevFF.maxAchievableVelocity(maxVoltage, acceleration) - Math.sin(posRads) * kg_vertical / kv;
    }
    /**
     * Calculates the minimum achievable velocity given a minimum voltage supply
     * and an acceleration. Useful for ensuring that velocity and acceleration
     * constraints for a trapezoidal profile are simultaneously achievable -
     * enter the acceleration constraint, and this will give you a
     * simultaneously-achievable velocity constraint.
     */
    public double minAchievableVelocity(double maxVoltage, double posRads, double acceleration) {
        return elevFF.maxAchievableVelocity(maxVoltage, acceleration) - Math.sin(posRads) * kg_vertical / kv;
    }
    /**
     * Calculates the maximum achievable acceleration given a maximum voltage
     * supply and a velocity. Useful for ensuring that velocity and acceleration
     * constraints for a trapezoidal profile are simultaneously achievable -
     * enter the velocity constraint, and this will give you a
     * simultaneously-achievable acceleration constraint.
     */
    public double maxAchievableAcceleration(double maxVoltage, double posRads, double velocity) {
        return elevFF.maxAchievableAcceleration(maxVoltage, velocity) - Math.sin(posRads) * kg_vertical / ka;
    }
    /**
     * Calculates the minimum achievable acceleration given a minimum voltage
     * supply and a velocity. Useful for ensuring that velocity and acceleration
     * constraints for a trapezoidal profile are simultaneously achievable -
     * enter the velocity constraint, and this will give you a
     * simultaneously-achievable acceleration constraint.
     */
    public double minAchievableAcceleration(double maxVoltage, double posRads, double velocity) {
        return maxAchievableAcceleration(-maxVoltage, posRads, velocity);
    }
}