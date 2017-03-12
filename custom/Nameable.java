package org.usfirst.frc4904.standard.custom;


public interface Nameable {
	default String getName() {
		return getClass().getSimpleName();
	};
}
