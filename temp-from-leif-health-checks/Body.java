package org.usfirst.frc4904.standard.commands.safety;

import java.util.Collection;

public abstract class Body {
	public abstract void connectBodyParts(BodyPart a, BodyPart b);

	public abstract Collection<BodyPart> getConnectedParts(BodyPart a);

	public abstract Collection<BodyPart> getAllParts();

	public BodyPartsSong generateSong() {
		return new BodyPartsSongGeneratorImpl().generateSong(this);
	}
}
