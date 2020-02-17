package org.usfirst.frc4904.standard.custom;

public interface Overridable {
	/**
	 * Set whether this object is overridden.
	 * 
	 * @param isOverridden Whether to override the object or not
	 */
	public void setOverridden(boolean isOverridden);

	/**
	 * Get whether this object is overridden.
	 * 
	 * @returns Whether this object is overridden.
	 */
	public boolean isOverridden();

	/**
	 * Get whether this object is NOT overridden. Useful for creating
	 * BooleanSuppliers with Java 8 syntax (e.g. this::isNotOverridden).
	 * 
	 * @returns Whether this object is NOT overridden.
	 */
	default boolean isNotOverridden() {
		return !isOverridden();
	}
}