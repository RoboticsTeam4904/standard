package org.usfirst.frc4904.standard.commands.safety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class RobotChassis extends Body {
	private HashSet<BodyPart> bodyParts = new HashSet<>();
	private ArrayList<Joint> jointsSmoked = new ArrayList<>();

	public static abstract class Joint {
		public abstract BodyPart getPartA();

		public abstract BodyPart getPartB();

		public abstract void smoke();

		public boolean hasPart(BodyPart part) {
			return getPartA().equals(part) || getPartB().equals(part);
		}

	}

	private static class Ligament extends Joint {
		private final BodyPart a;
		private final BodyPart b;

		public Ligament(BodyPart a, BodyPart b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public BodyPart getPartA() {
			return a;
		}

		@Override
		public BodyPart getPartB() {
			return b;
		}

		@Override
		public void smoke() {
		}

	}

	private Joint rollJoint(BodyPart a, BodyPart b) {
		return new Ligament(a, b);
	}

	private void smokeJoint(Joint joint) {
		joint.smoke();
		jointsSmoked.add(joint);
	}

	private boolean doYouThinkYouAreInMe(BodyPart part) {
		return part.getBody().equals(this);
	}

	private boolean doIThinkYouAreInMe(BodyPart part) {
		return bodyParts.contains(part);
	}

	public static class WeDisagreeAboutWhetherYouAreInMeException extends IllegalStateException {
		public WeDisagreeAboutWhetherYouAreInMeException() {
			super("We disagree about whether you are in me");
		}
	}

	private boolean areYouInMe(BodyPart part) {
		boolean doYouThinkYouAreInMe = doYouThinkYouAreInMe(part);
		boolean doIThinkYouAreInMe = doIThinkYouAreInMe(part);
		if (doYouThinkYouAreInMe ^ doIThinkYouAreInMe) {
			throw new WeDisagreeAboutWhetherYouAreInMeException();
		}
		return doYouThinkYouAreInMe;
	}

	private void verifyYouAreInMe(BodyPart part) {
		if (!areYouInMe(part)) {
			throw new IllegalArgumentException("You aren't in me");
		}
	}

	@Override
	public void connectBodyParts(BodyPart a, BodyPart b) {
		verifyYouAreInMe(a);
		verifyYouAreInMe(b);
		Joint joint = rollJoint(a, b);
		smokeJoint(joint);
	}

	public ArrayList<Joint> getAllSmokedJoints() {
		return new ArrayList<>(jointsSmoked);
	}

	@Override
	public Collection<BodyPart> getConnectedParts(BodyPart part) {
		return jointsSmoked.stream().filter(joint -> joint.hasPart(part))
				.map(joint -> joint.getPartA().equals(part) ? joint.getPartB() : joint.getPartA())
				.collect(Collectors.toList());
	}

	@Override
	public Collection<BodyPart> getAllParts() {
		return new ArrayList<>(bodyParts);
	}

}
