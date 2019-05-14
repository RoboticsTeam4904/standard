package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import org.usfirst.frc4904.standard.subsystems.motor.ServoSubsystem;
import org.usfirst.frc4904.standard.subsystems.motor.SwerveModule;

public class SwerveChassis extends Chassis {
	private final SwerveModule[] modules;
	private double[] wheelSpeed;
	private double[] wheelAngle;

	/**
	 * Constructs a swerve drive chassis
	 * 
	 * @param modules
	 * @param wheelSpeed
	 * @param wheelAngle
	 */
<<<<<<< HEAD
=======

>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
	public SwerveChassis(String name, SwerveModule... modules) {
		super(name);
		this.modules = modules;
	}

	@Override
	public void moveCartesian(double xSpeed, double ySpeed, double turnSpeed) {
		double[][] augmentedSystem = new double[3][modules.length + 1];
<<<<<<< HEAD
=======

>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
		for (int j = 0; j < augmentedSystem[0].length - 1; j++) {
			augmentedSystem[0][j] = Math.sin(modules[j].getCurrentPosition());
			augmentedSystem[1][j] = Math.cos(modules[j].getCurrentPosition());
			augmentedSystem[2][j] = modules[j].distanceFromCenter
<<<<<<< HEAD
				* (Math.sin(modules[j].angleFromCenter - modules[j].getCurrentPosition()));
=======
					* (sin(modules[j].angleFromCenter - modules[j].getCurrentPosition));
>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
		}
		augmentedSystem[0][augmentedSystem[0].length - 1] = xSpeed;
		augmentedSystem[1][augmentedSystem[1].length - 1] = ySpeed;
		augmentedSystem[2][augmentedSystem[2].length - 1] = turnSpeed;
		double[] wheelSpeeds = minMaxUndeterminedSystem(solveSystem(augmentedSystem));
<<<<<<< HEAD
=======

>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
		for (int i = 0; i < modules.length; i++) {
			double xVector;
			double yVector;
			xVector = xSpeed + modules[i].distanceFromCenter * Math.cos(modules[i].angleFromCenter);
			yVector = ySpeed + modules[i].distanceFromCenter * Math.sin(modules[i].angleFromCenter);
			wheelAngle[i] = Math.atan(xVector / yVector);
<<<<<<< HEAD
		}
		/*
		 * TODO: normalize
		 * code from previous version is here:
		 * frontLeftWheelSpeed /= maxSpeed;
		 * frontRightWheelSpeed /= maxSpeed;
		 * backLeftWheelSpeed /= maxSpeed;
		 * backRightWheelSpeed /= maxSpeed;
		 */
		for (int i = 0; i < modules.length; i++) {
			modules[i].setState(wheelAngle[i], wheelSpeeds[i]);
		}
=======

		}
		/*
		 * TODO: normalize code from previous version is here: frontLeftWheelSpeed /=
		 * maxSpeed; frontRightWheelSpeed /= maxSpeed; backLeftWheelSpeed /= maxSpeed;
		 * backRightWheelSpeed /= maxSpeed;
		 */

		for (int i = 0; i < modules.length; i++) {
			modules[i].setState(wheelAngle[i], wheelSpeeds[i]);
		}

>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
		/*
		 * for switching from cartesian to polar double totalSpeed =
		 * Math.sqrt(Math.pow(ySpeed,2)+Math.pow(xSpeed,2)); double angle = Math.PI/2 -
		 * Math.atan(ySpeed/xSpeed); movePolar(totalSpeed, angle, turnSpeed);
		 */
	}

	@Override
	public void movePolar(double speed, double angle, double turnSpeed) {
		moveCartesian(speed * Math.cos(angle), speed * Math.sin(angle), turnSpeed);
	}

	@Override
	public double[] getMotorSpeeds() {
		return wheelSpeed;
	}

	@Override
	public Motor[] getMotors() {
		Motor[] motors = new Motor[modules.length];
		for (int i = 0; i < modules.length; i++) {
			motors[i] = modules[i].linear;
		}
		return motors;
	}

	public double[] getServoAngles() {
		return wheelAngle;
	}

	public ServoSubsystem[] getServos() {
		ServoSubsystem[] servos = new ServoSubsystem[modules.length];
		for (int i = 0; i < modules.length; i++) {
			servos[i] = modules[i].rotation;
		}
		return servos;
	}

	public SwerveModule[] getSwerveModule() {
		return modules;
	}

	protected static double[][] solveSystem(double[][] augmentedSystem) { // Works at the moment
		for (int i = 0; i < augmentedSystem.length; i++) {
			for (int j = i + 1; j < augmentedSystem.length; j++) {
				double firstEquationCoefficent = augmentedSystem[j][i] / augmentedSystem[i][i];
				for (int k = i; k <= augmentedSystem[0].length - 1; k++) {
					augmentedSystem[j][k] -= augmentedSystem[i][k] * firstEquationCoefficent;
				}
			}
		}
		for (int i = augmentedSystem.length - 1; i >= 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				double secondEquationCoefficent = augmentedSystem[j][i] / augmentedSystem[i][i];
				for (int k = augmentedSystem[0].length - 1; k >= 0; k--) {
					augmentedSystem[j][k] -= augmentedSystem[i][k] * secondEquationCoefficent;
<<<<<<< HEAD
=======

>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
				}
			}
		}
		return augmentedSystem;
	}

	protected static double[] minMaxUndeterminedSystem(double[][] solvedMatrix) { // Untested
		double[] intersectionX = new double[(solvedMatrix.length * (solvedMatrix.length + 1)) / 2];
		int k = 0;
		for (int i = 0; i < solvedMatrix.length - 1; i++) {
			for (int j = i + 1; j < solvedMatrix.length; j++) {
<<<<<<< HEAD
				double determinant = solvedMatrix[i][i] * solvedMatrix[j][solvedMatrix[j].length - 2] -
					solvedMatrix[j][j] * solvedMatrix[i][solvedMatrix[i].length - 2];
				if (determinant != 0) {
					intersectionX[k] = (solvedMatrix[i][solvedMatrix[j].length - 1]
						* solvedMatrix[j][solvedMatrix[j].length - 2] -
						solvedMatrix[j][solvedMatrix[j].length - 1] * solvedMatrix[i][solvedMatrix[i].length - 2])
						/ determinant;
					intersectionX[k
						+ 1] = (-solvedMatrix[i][solvedMatrix[j].length - 1] * solvedMatrix[j][solvedMatrix[j].length - 2] -
							solvedMatrix[j][solvedMatrix[j].length - 1] * -solvedMatrix[i][solvedMatrix[i].length - 2])
							/ determinant;
					intersectionX[k
						+ 2] = (solvedMatrix[i][solvedMatrix[j].length - 1] * solvedMatrix[j][solvedMatrix[j].length - 2] +
							solvedMatrix[j][solvedMatrix[j].length - 1] * solvedMatrix[i][solvedMatrix[i].length - 2])
							/ determinant;
					intersectionX[k
						+ 3] = (-solvedMatrix[i][solvedMatrix[j].length - 1] * solvedMatrix[j][solvedMatrix[j].length - 2] +
							solvedMatrix[j][solvedMatrix[j].length - 1] * solvedMatrix[i][solvedMatrix[i].length - 2])
=======
				double determinant = solvedMatrix[i][i] * solvedMatrix[j][solvedMatrix[j].length - 2]
						- solvedMatrix[j][j] * solvedMatrix[i][solvedMatrix[i].length - 2];
				if (determinant != 0) {
					intersectionX[k] = (solvedMatrix[i][solvedMatrix[j].length - 1]
							* solvedMatrix[j][solvedMatrix[j].length - 2]
							- solvedMatrix[j][solvedMatrix[j].length - 1] * solvedMatrix[i][solvedMatrix[i].length - 2])
							/ determinant;
					intersectionX[k + 1] = (-solvedMatrix[i][solvedMatrix[j].length - 1]
							* solvedMatrix[j][solvedMatrix[j].length - 2]
							- solvedMatrix[j][solvedMatrix[j].length - 1]
									* -solvedMatrix[i][solvedMatrix[i].length - 2])
							/ determinant;
					intersectionX[k + 2] = (solvedMatrix[i][solvedMatrix[j].length - 1]
							* solvedMatrix[j][solvedMatrix[j].length - 2]
							+ solvedMatrix[j][solvedMatrix[j].length - 1] * solvedMatrix[i][solvedMatrix[i].length - 2])
							/ determinant;
					intersectionX[k + 3] = (-solvedMatrix[i][solvedMatrix[j].length - 1]
							* solvedMatrix[j][solvedMatrix[j].length - 2]
							+ solvedMatrix[j][solvedMatrix[j].length - 1] * solvedMatrix[i][solvedMatrix[i].length - 2])
>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
							/ determinant;
				} else {
					intersectionX[k] = Double.NaN;
				}
				k += 4;
			}
		}
		double minMaxValue = Double.POSITIVE_INFINITY;
		double[] bestValues = new double[solvedMatrix.length + 1];
		double[] currentValues = new double[solvedMatrix.length + 1];
<<<<<<< HEAD
		for (int i = 0; i < intersectionX.length; i++) {
			for (int j = 0; j < solvedMatrix.length; j++) {
				currentValues[j] = (solvedMatrix[j][solvedMatrix.length - 1]
					- solvedMatrix[j][solvedMatrix.length - 2] * intersectionX[i]) / solvedMatrix[j][j];
			}
			double maxValue = 0f;
			for (double value : currentValues) {
				double abs_val = Math.abs(value);
				if (abs_val > maxValue) {
					maxValue = abs_val;
				}
			}
			if (maxValue < minMaxValue) {
				minMaxValue = maxValue;
=======

		for (int i = 0; i < intersectionX.length; i++) {
			for (int j = 0; j < solvedMatrix.length; j++) {
				currentValues[j] = (solvedMatrix[j][solvedMatrix.length - 1]
						- solvedMatrix[j][solvedMatrix.length - 2] * intersectionX[i]) / solvedMatrix[j][j];
			}
			if (Math.max(Math.max(Math.abs(currentValues[0]), Math.abs(currentValues[1])),
					Math.max(Math.abs(currentValues[2]), Math.abs(currentValues[3]))) < minMaxValue) {
				minMaxValue = Math.max(Math.max(Math.abs(currentValues[0]), Math.abs(currentValues[1])),
						Math.max(Math.abs(currentValues[2]), Math.abs(currentValues[3])));
>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
				bestValues = currentValues;
			}
		}
		return bestValues;
	}
<<<<<<< HEAD
}
=======

}
>>>>>>> cbe9d2df14ac4a1a33d6b1cfbc4cc794938dbf43
