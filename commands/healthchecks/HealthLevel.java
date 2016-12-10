package org.usfirst.frc4904.standard.commands.healthchecks;


public enum HealthLevel implements Comparable<HealthLevel> {
	/**
	 * No problem here
	 */
	PERFECT,
	/**
	 * Within normal parameters
	 */
	SAFE,
	/**
	 * We don't like using nulls
	 */
	UNKNOWN,
	/**
	 * Please exit the building in a calm and orderly fashion
	 */
	UNSAFE,
	/**
	 * Skynet has begun.
	 */
	DANGEROUS;
}
