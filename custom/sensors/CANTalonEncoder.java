// // WAS PID SOURCE
// package org.usfirst.frc4904.standard.custom.sensors;

// import org.usfirst.frc4904.standard.Util;
// // TODO: VelocityMeasPeriod is gone, idk how to fix feedback device
// // I'm pretty sure it should be FeedbackSensoreSourceValue, but idk how to actually make it work
// import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
// import com.ctre.phoenix6.motorcontrol.VelocityMeasPeriod;
// import com.ctre.phoenix6.hardware.core.CoreTalonFX;

// /**
//  * Encoder class for the Built-in Encoders on Talon Motor Controllers
//  * Works for both Falcons (CANTalonFX) and SRXs (CANTalonSRX)
//  */
// public class CANTalonEncoder implements CustomEncoder {
// 	protected static final double DEFAULT_DISTANCE_PER_PULSE = 1.0;
// 	protected static final boolean DEFAULT_REVERSE_DIRECTION = false;
// 	protected static final String DEFAULT_NAME = "CANTalonEncoder";
// 	protected static final int DEFAULT_PERIOD = 20;
// 	protected static final double DECI_SECONDS_TO_SECONDS = 10.0; // getSpeed must be converted from ticks per 100ms to
// 																	// ticks per second, so *10.
// 	protected static final int PID_IDX = 1;
// 	protected static final FeedbackDevice DEFAULT_FEEDBACK_DEVICE = FeedbackDevice.IntegratedSensor;
// 	protected final CoreTalonFX talon;
// 	protected double distancePerPulse;
// 	protected boolean reverseDirection;

// 	public CANTalonEncoder(String name, CoreTalonFX talon, boolean reverseDirection, double distancePerPulse, FeedbackDevice feedbackDevice, double period) {
// 		this.talon = talon;
// 		setReverseDirection(reverseDirection);
// 		setDistancePerPulse(distancePerPulse);
// 		setFeedbackDevice(feedbackDevice);
// 		this.talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.valueOf(period));
// 	}

// 	public CANTalonEncoder(String name, CoreTalonFX talon, boolean reverseDirection, double distancePerPulse,
// 			FeedbackDevice feedbackDevice) {
// 		this(name, talon, reverseDirection, distancePerPulse, feedbackDevice, DEFAULT_PERIOD);
// 	}

// 	public CANTalonEncoder(String name, CoreTalonFX talon, boolean reverseDirection, double distancePerPulse) {
// 		this(name, talon, reverseDirection, distancePerPulse, DEFAULT_FEEDBACK_DEVICE);

// 	}

// 	public CANTalonEncoder(String name, CoreTalonFX talon, boolean reverseDirection) {
// 		this(name, talon, reverseDirection, DEFAULT_DISTANCE_PER_PULSE);
// 	}

// 	public CANTalonEncoder(String name, CoreTalonFX talon, double distancePerPulse) {
// 		this(name, talon, DEFAULT_REVERSE_DIRECTION, distancePerPulse);
// 	}

// 	public CANTalonEncoder(String name, CoreTalonFX talon) {
// 		this(name, talon, DEFAULT_REVERSE_DIRECTION);
// 	}

// 	public CANTalonEncoder(CoreTalonFX talon, boolean reverseDirection, double distancePerPulse) {
// 		this(DEFAULT_NAME, talon, reverseDirection, distancePerPulse);
// 	}

// 	public CANTalonEncoder(CoreTalonFX talon, double distancePerPulse) {
// 		this(DEFAULT_NAME, talon, distancePerPulse);
// 	}

// 	public CANTalonEncoder(CoreTalonFX talon) {
// 		this(DEFAULT_NAME, talon);
// 	}

// 	public void setFeedbackDevice(FeedbackDevice feedbackDevice) {
// 		talon.configSelectedFeedbackSensor(feedbackDevice);
// 	}

// 	@Override
// 	public double getDistance() {
// 		if (reverseDirection) {
// 			return distancePerPulse * talon.getSelectedSensorPosition(PID_IDX) * -1.0;
// 		} else {
// 			return distancePerPulse * talon.getSelectedSensorPosition(PID_IDX);
// 		}
// 	}

// 	@Override
// 	public boolean getDirection() {
// 		return !reverseDirection == (talon.getSelectedSensorVelocity(PID_IDX) >= 0);
// 	}

// 	@Override
// 	public boolean getStopped() {
// 		return Util.isZero(getRate());
// 	}

// 	public boolean isRevLimitSwitchClosed() {
// 		return talon.isRevLimitSwitchClosed() == 1;
// 	}

// 	public boolean isFwdLimitSwitchClosed() {
// 		return talon.isFwdLimitSwitchClosed() == 1;
// 	}

// 	@Override
// 	public double getRate() {
// 		if (reverseDirection) {
// 			return distancePerPulse * talon.getSelectedSensorVelocity(PID_IDX) * -DECI_SECONDS_TO_SECONDS;
// 		} else {
// 			return distancePerPulse * talon.getSelectedSensorVelocity(PID_IDX) * DECI_SECONDS_TO_SECONDS;
// 		}
// 	}

// 	@Override
// 	public double getDistancePerPulse() {
// 		return distancePerPulse;
// 	}

// 	@Override
// 	public void setDistancePerPulse(double distancePerPulse) {
// 		this.distancePerPulse = distancePerPulse;
// 	}

// 	@Override
// 	public boolean getReverseDirection() {
// 		return reverseDirection;
// 	}

// 	@Override
// 	public void setReverseDirection(boolean reverseDirection) {
// 		this.reverseDirection = reverseDirection;
// 	}

// 	@Override
// 	public void reset() {
// 		talon.setSelectedSensorPosition(0);
// 	}

// 	@Override
// 	public double getDistanceSafely() {
// 		return getDistance();
// 	}

// 	@Override
// 	public boolean getDirectionSafely() {
// 		return getDirection();
// 	}

// 	@Override
// 	public boolean getStoppedSafely() {
// 		return getStopped();
// 	}

// 	@Override
// 	public double getRateSafely() {
// 		return getRate();
// 	}

// 	public CoreTalonFX getTalon() {
// 		return talon;
// 	}
// }
