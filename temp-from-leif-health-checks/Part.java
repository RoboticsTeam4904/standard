package org.usfirst.frc4904.standard.commands.safety;

import java.util.Collection;

public interface Part {
	public String getLocationOnBody();

	public Collection<Part> getConnectedParts();

	public PartHealthLevelProvider getPartHealthLevelProvider();

}
