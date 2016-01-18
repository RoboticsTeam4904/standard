package org.usfirst.frc4904.standard.commands.healthchecks;


public enum HealthStatus implements Comparable<HealthStatus> {
	SAFE, UNCERTAIN, CAUTION, DANGEROUS;
}
