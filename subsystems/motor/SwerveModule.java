public class SwerveModule {
		public final Motor linear;
		public final ServoSubsystem rotation;
		public double speed;
		public double angle;

		/**
		 * Constructs a single swerve module
		 * 
		 * @param rotation
		 * @param linear
         * @param angle degrees
         * @param turn
		 */

		public SwerveModule(Motor linear, ServoSubsystem drive, Double angle, Double distance) {
			this.linear = drive;
            this.rotation = turn;
            this.angle = angle;
            this.distance = distance;
            
		}

		public void setState(double speed, double angle) {
			// add stuff that allows for modulating speed based on driver input
			this.speed = speed;
			if (angle >= 180) { // TODO: radians
				this.speed *= -1;
				this.angle = angle - 180;
			} else {
				this.angle = angle;
			}
			// I don't know if you'd be able to just set any angle to the servo. Maybe set it incrementally?
			// Should end up actually calling the motors
		}
	}