package org.usfirst.frc4904.standard.commands.safety;

import java.util.stream.Collectors;

public class BodyPartsSongGeneratorImpl implements BodyPartsSongGenerator {

	@Override
	public BodyPartsSong generateSong(Body body) {
		if (!(body instanceof RobotChassis)) {
			throw new IllegalArgumentException("Human bodies not supported");
		}
		return new BodyPartsSongImpl(
				((RobotChassis) body)
						.getAllSmokedJoints().stream().map(joint -> "The " + joint.getPartA()
								+ " bone is connected to the " + joint.getPartB() + " bone.")
				.collect(Collectors.joining("\n")));
	}

}
