package org.usfirst.frc4904.standard.commands.safety;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BodyPart implements Part {
	protected final Body body;

	protected BodyPart(Body body) {
		this.body = body;
	}

	@Override
	public String getLocationOnBody() {
		return "Part " + this + " on body " + body;
	}

	@Override
	public Collection<Part> getConnectedParts() {
		Collection<BodyPart> currentBodyParts = body.getConnectedParts(this);
		ArrayList<Part> blazeit = new ArrayList<>();
		for (BodyPart p : currentBodyParts) {
			blazeit.add(p);
		}
		return blazeit;
	}

	public Body getBody() {
		return body;
	}
}
