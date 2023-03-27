package org.usfirst.frc4904.standard.subsystems.motor;

import edu.wpi.first.math.controller.ArmFeedforward;

/**
 * Computes feed-forward values for an arm rotation motor, with a varying cg.
 * Internally composes a wpilib ArmFeedForward.
 */
public class TelescopingArmPivotFeedForward {
    public final double retracted_ks;
    public final double extended_ks;
    public final double retracted_kv;
    public final double extended_kv;
    public final double retracted_ka;
    public final double extended_ka;
    public final double retracted_kg;
    public final double extended_kg;
    private final ArmFeedforward armFFextended;
    private final ArmFeedforward armFFretracted;

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
    public TelescopingArmPivotFeedForward(double retracted_kg, double extended_kg, double retracted_ks, double extended_ks, double retracted_kv, double extended_kv, double retracted_ka, double extended_ka) {
        this.retracted_ks = retracted_ks;
        this.extended_ks = extended_ks;

        this.extended_kv = extended_kv;
        this.retracted_kv = extended_kv;
        this.retracted_ka = retracted_ka;
        this.extended_ka = extended_ka;

        this.retracted_kg = retracted_kg;
        this.extended_kg = extended_kg;
        this.armFFextended = new ArmFeedforward(extended_ks, extended_kg, extended_kv, extended_ka); // passes zero for kg because we will do our own kg calculations and add the result externally
        this.armFFretracted = new ArmFeedforward(retracted_ks, retracted_kg, retracted_kv, retracted_ka);
    }
    /**
     * @param armExtensionRatio The extension of the arm, relative to it's full extension. Should be between 0 and 1. Used as cg linear interpolation parameter.
     * @param posRads           Current angle, in radians, measured from horizontal (0 = parallel with floor).
     * @param velRadPerSec      Velocity setpoint.
     * @param accelRadPerSecSquared Acceleration setpoint.
     * @return  The computed feedforward.
     */
    public double calculate(double armExtensionRatio, double posRads, double velRadPerSec, double accelRadPerSecSquared) {
        double param = Math.min(Math.max(armExtensionRatio, -0.5), 1.5);
        return (this.armFFextended.calculate(posRads, velRadPerSec, accelRadPerSecSquared) * (1-param)) + (this.armFFretracted.calculate(posRads, velRadPerSec, accelRadPerSecSquared) * param);    }
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
    // public double maxAchievableVelocity(double maxVoltage, double extension, double angle, double acceleration) {
    //     // Assume max velocity is positive
    //     return this.armFF.maxAchievableVelocity(maxVoltage, angle, acceleration) - Math.cos(angle) * lerpedCg(extension) / kv;
    // }
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
    // public double minAchievableVelocity(double maxVoltage, double extension, double angle, double acceleration) {
    //     // Assume min velocity is negative, ks flips sign
    //     return this.armFF.minAchievableVelocity(maxVoltage, angle, acceleration) - Math.cos(angle) * lerpedCg(extension) / kv;
    // }
    
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
    // public double maxAchievableAcceleration(double maxVoltage, double extension, double angle, double velocity) {
    //     return this.armFF.maxAchievableAcceleration(maxVoltage, angle, velocity) - Math.cos(angle) * lerpedCg(extension) / ka;
    // }
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
    // public double minAchievableAcceleration(double maxVoltage, double extension, double angle, double velocity) {
    //     return maxAchievableAcceleration(-maxVoltage, extension, angle, velocity);  // this negative voltage trick is the same as used in WPILib ArmFeedForward
    // }