package org.usfirst.frc4904.standard.custom;


public interface Named {
	default String getName() {
		return getClass().getSimpleName();
	};
}
