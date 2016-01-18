package org.usfirst.frc4904.standard.commands.healthchecks;


public enum HealthLevel implements Comparable<HealthLevel> {
	UNKNOWN, PERFECT, SAFE, MEDIUM, UNSAFE, DANGEROUS;
}
