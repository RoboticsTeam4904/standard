package org.usfirst.frc4904.standard.subsystems.motor;

import edu.wpi.first.math.controller.ArmFeedforward;

/**
 * Computes feed-forward values for an arm rotation motor, with a varying cg.
 * Internally composes a wpilib ArmFeedForward.
 */
public class TelescopingArmPivotFeedForward {
    public final double ks;
    public final double kv;
    public final double ka;
    public final double retracted_kg;
    public final double extended_kg;
    private final ArmFeedforward armFF;
    /**
     * Computes feed-forward values for an arm rotation motor, with a varying
     * cg. cg is calculated by linearly interpolating between retracted and
     * extended cgs. Internally composes a wpilib ArmFeedForward.
     * 
     * Gain units (eg. voltage) will dictate feed-forward units. 
     *
     * Use the recalc arm calculator or characterization to estimate the kg
     * (gravitational gain) on the arm for both the retracted and extended
     * positions. 
     *
     * @param retracted_kg  Gravity gain when the arm is not (0.0) extended.
     * @param extended_kg   Gravity gain when the arm is fully (1.0) extended.
     * @param ks    Static gain, eg. from recalc or sysID. The voltage required
     * to hold the arm parallel. 
     * @param kv    Velocity gain, eg. from recalc or sysID.
     * @param ka    Acceleration gain. Makes little difference, 0 is often an
     * okay default.
     */
    public TelescopingArmPivotFeedForward(double retracted_kg, double extended_kg, double ks, double kv, double ka) {
        this.ks = ks;
        this.kv = kv;
        this.ka = ka;
        this.retracted_kg = retracted_kg;
        this.extended_kg = extended_kg;
        this.armFF = new ArmFeedforward(ks, 0, kv, ka); // passes zero for kg because we will do our own kg calculations and add the result externally
    }
    private double lerpedCg(double param) {
        // You could pass something outside of [0, 1] here, it would just assume
        // the arm can extend in the other direction (and the cg still moves
        // linearly) or more than originally specified. The assumption of a
        // linear relationship between extension and cg remains the same,
        // because of lever arm mechanics. Prefer measuring/calculating cg for
        // the full range of motion to reduce relative error. 
        param = Math.min(Math.max(param, -0.5), 1.5);
        return (this.retracted_kg * (1-param)) + (this.extended_kg * param);
    }
    /**
     * @param armExtensionRatio The extension of the arm, relative to it's full extension. Should be between 0 and 1. Used as cg linear interpolation parameter.
     * @param posRads           Current angle, in radians, measured from horizontal (0 = parallel with floor).
     * @param velRadPerSec      Velocity setpoint.
     * @param accelRadPerSecSquared Acceleration setpoint.
     * @return  The computed feedforward.
     */
    public double calculate(double armExtensionRatio, double posRads, double velRadPerSec, double accelRadPerSecSquared) {
        return this.armFF.calculate(posRads, velRadPerSec, accelRadPerSecSquared) + lerpedCg(armExtensionRatio) * Math.cos(posRads);
    }

    // Util methods to calculate max achievable velocity/acceleration from the
    // other setpoint. Allegedly obtained from rearranging the above equations;
    // see WPILib ArmFeedForward for details. 
    /**
     * Calculates the maximum achievable velocity given a maximum voltage
     * supply, a position, and an acceleration. Useful for ensuring that
     * velocity and acceleration constraints for a trapezoidal profile are
     * simultaneously achievable - enter the acceleration constraint, and this
     * will give you a simultaneously-achievable velocity constraint.
     *
     * @param maxVoltage   The maximum voltage that can be supplied to the arm.
     * @param extension    The extension of the arm, relative to the full
     *                     extension. Should be between 0 and 1.
     * @param angle        The angle of the arm. This angle should be measured
     *                     from the horizontal (i.e. if the provided angle is 0,
     *                     the arm should be parallel with the floor). If your
     *                     encoder does not follow this convention, an offset
     *                     should be added.
     * @param acceleration The acceleration of the arm.
     * @return The maximum possible velocity at the given acceleration and
     * angle.
     */
    public double maxAchievableVelocity(double maxVoltage, double extension, double angle, double acceleration) {
        // Assume max velocity is positive
        return this.armFF.maxAchievableVelocity(maxVoltage, angle, acceleration) - Math.cos(angle) * lerpedCg(extension) / kv;
    }
    /**
     * Calculates the minimum achievable velocity given a maximum voltage
     * supply, a position, and an acceleration. Useful for ensuring that
     * velocity and acceleration constraints for a trapezoidal profile are
     * simultaneously achievable - enter the acceleration constraint, and this
     * will give you a simultaneously-achievable velocity constraint.
     *
     * @param maxVoltage   The maximum voltage that can be supplied to the arm.
     * @param extension    The extension of the arm, relative to the full
     *                     extension. Should be between 0 and 1.
     * @param angle        The angle of the arm. This angle should be measured from
     *                     the horizontal (i.e. if
     *                     the provided angle is 0, the arm should be parallel with
     *                     the floor). If your encoder does
     *                     not follow this convention, an offset should be added.
     * @param acceleration The acceleration of the arm.
     * @return The minimum possible velocity at the given acceleration and angle.
     */
    public double minAchievableVelocity(double maxVoltage, double extension, double angle, double acceleration) {
        // Assume min velocity is negative, ks flips sign
        return this.armFF.minAchievableVelocity(maxVoltage, angle, acceleration) - Math.cos(angle) * lerpedCg(extension) / kv;
    }
    
    /**
     * Calculates the maximum achievable acceleration given a maximum voltage
     * supply, a position, and a velocity. Useful for ensuring that velocity and
     * acceleration constraints for a trapezoidal profile are simultaneously
     * achievable - enter the velocity constraint, and this will give you a
     * simultaneously-achievable acceleration constraint.
     *
     * @param maxVoltage The maximum voltage that can be supplied to the arm.
     * @param extension  The extension of the arm, relative to the full
     *                   extension. Should be between 0 and 1.
     * @param angle      The angle of the arm. This angle should be measured from
     *                   the horizontal (i.e. if
     *                   the provided angle is 0, the arm should be parallel with
     *                   the floor). If your encoder does
     *                   not follow this convention, an offset should be added.
     * @param velocity   The velocity of the arm.
     * @return The maximum possible acceleration at the given velocity.
     */
    public double maxAchievableAcceleration(double maxVoltage, double extension, double angle, double velocity) {
        return this.armFF.maxAchievableAcceleration(maxVoltage, angle, velocity) - Math.cos(angle) * lerpedCg(extension) / ka;
    }
    /**
     * Calculates the minimum achievable acceleration given a maximum voltage
     * supply, a position, and a velocity. Useful for ensuring that velocity and
     * acceleration constraints for a trapezoidal profile are simultaneously
     * achievable - enter the velocity constraint, and this will give you a
     * simultaneously-achievable acceleration constraint.
     *
     * @param maxVoltage The maximum voltage that can be supplied to the arm.
     * @param extension  The extension of the arm, relative to the full
     *                   extension. Should be between 0 and 1.
     * @param angle      The angle of the arm. This angle should be measured from
     *                   the horizontal (i.e. if
     *                   the provided angle is 0, the arm should be parallel with
     *                   the floor). If your encoder does
     *                   not follow this convention, an offset should be added.
     * @param velocity   The velocity of the arm.
     * @return The minimum possible acceleration at the given velocity.
     */
    public double minAchievableAcceleration(double maxVoltage, double extension, double angle, double velocity) {
        return maxAchievableAcceleration(-maxVoltage, extension, angle, velocity);  // this negative voltage trick is the same as used in WPILib ArmFeedForward
    }
}