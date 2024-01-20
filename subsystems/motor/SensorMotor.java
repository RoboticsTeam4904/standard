// package org.usfirst.frc4904.standard.subsystems.motor;

// import java.util.function.DoubleConsumer;

// import org.usfirst.frc4904.standard.custom.MCChassisController;
// import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
// import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
// import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
// import edu.wpi.first.wpilibj.motorcontrol.MotorController;

// @Deprecated
// public abstract class SensorMotor extends Motor implements DoubleConsumer {
// 	protected final MCChassisController.MineCraft minecraft;

// 	public SensorMotor(String name, boolean inverted, SpeedModifier speedModifier, MCChassisController.MineCraft minecraft,
// 			MotorController... motors) {
// 		super(name, inverted, speedModifier, motors);
// 		this.minecraft = minecraft;
// 	}

// 	public SensorMotor(String name, boolean isInverted, MCChassisController.MineCraft minecraft, MotorController... motors) {
// 		this(name, isInverted, new IdentityModifier(), minecraft, motors);
// 	}

// 	public SensorMotor(String name, SpeedModifier speedModifier, MCChassisController.MineCraft motionController,
// 			MotorController... motors) {
// 		this(name, false, speedModifier, motionController, motors);
// 	}

// 	public SensorMotor(String name, MCChassisController.MineCraft motionController, MotorController... motors) {
// 		this(name, false, new IdentityModifier(), motionController, motors);
// 	}

// 	public SensorMotor(boolean isInverted, SpeedModifier speedModifier, MCChassisController.MineCraft motionController,
// 			MotorController... motors) {
// 		this("SensorMotor", isInverted, speedModifier, motionController, motors);
// 	}

// 	public SensorMotor(boolean isInverted, MCChassisController.MineCraft motionController, MotorController... motors) {
// 		this("SensorMotor", isInverted, motionController, motors);
// 	}

// 	public SensorMotor(SpeedModifier speedModifier, MCChassisController.MineCraft motionController, MotorController... motors) {
// 		this("SensorMotor", speedModifier, motionController, motors);
// 	}

// 	public SensorMotor(MCChassisController.MineCraft motionController, MotorController... motors) {
// 		this("SensorMotor", motionController, motors);
// 	}

// 	public void reset() throws InvalidSensorException {
// 		minecraft.reset();
// 	}

// 	public void setInputRange(double minimum, double maximum) {
// 		minecraft.setInputRange(minimum, maximum);
// 	}

// 	@Override
// 	public void accept(double speed) {
// 		super.set(speed);
// 	}


// 	/**
// 	 * Set the position of a sensor motor
// 	 *
// 	 * @param position
// 	 * @throws InvalidSensorException
// 	 */
// 	public void setPositionSafely(double position) throws InvalidSensorException {
// 		minecraft.setSetpoint(position);
// 		double speed = minecraft.getSafely();
// 		// LogKitten.v(getName() + " set to position " + position + " at speed " + speed);
// 		super.set(speed); // TO DO remove and test I don't know why this is here.
// 	}

// 	/**
// 	 * Set the position of a sensor motor
// 	 *
// 	 * @param position
// 	 * @warning this does not indicate sensor failure
// 	 */
// 	public void setPosition(double position) {
// 		minecraft.setSetpoint(position);
// 		double speed = minecraft.getSafely();
// 		// LogKitten.v(getName() + " set to position " + position + " at speed " + speed);
// 		super.set(speed);
// 	}
// }
