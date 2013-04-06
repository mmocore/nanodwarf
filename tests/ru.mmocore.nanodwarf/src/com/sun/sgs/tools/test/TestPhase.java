/*LICENSE*/

package com.sun.sgs.tools.test;

/**
 * The {@code TestPhase} enumeration type specifies the possible phases for
 * tests marked with the {@link IntegrationTest} annotation.
 */
public enum TestPhase {

	/**
	 * Tests marked with the {@code SHORT} phase should only be executed during
	 * the unit test phase of testing.
	 */
	SHORT,
	/**
	 * Tests marked with the {@code LONG} phase should only be executed during
	 * the integration test phase of testing.
	 */
	LONG,
	/**
	 * Tests marked with the {@code BOTH} phase should be executed during both
	 * the unit test and integration test phases of testing.
	 */
	BOTH;

	/**
	 * This method will return true if and only if the current testing phase is
	 * the unit test phase. This is established with the system property
	 * {@code test.phase}. A value of {@code long} for this property means the
	 * current testing phase is the integration test phase. Any other value
	 * means the current testing phase is the unit test phase.
	 * 
	 * @return {@code true} if it is the unit test phase
	 */
	public static boolean isShortPhase() {
		return !isLongPhase();
	}

	/**
	 * This method will return true if and only if the current testing phase is
	 * the integration test phase. This is established with the system property
	 * {@code test.phase}. A value of {@code long} for this property means the
	 * current testing phase is the integration test phase. Any other value
	 * means the current testing phase is the unit test phase.
	 * 
	 * @return {@code true} if it is the unit test phase
	 * @see TestProfile#
	 */
	public static boolean isLongPhase() {
		return "long".equals(System.getProperty("test.phase"));
	}

	/**
	 * Given an {@code IntegrationTest} annotation, this method determines
	 * whether or not the class or method attached to this annotation should be
	 * run. A test should be run according to the following conditions:
	 * <ul>
	 * <li>If the current test phase is {@link TestPhase#LONG}, a test should be
	 * run if it has the {@code IntegrationTest} annotation <em>and</em> the
	 * annotation specifies either the {@link TestPhase#LONG} or
	 * {@link TestPhase#BOTH} phase.</li>
	 * <li>If the current test phase is {@link TestPhase#SHORT}, a test should
	 * be run if it does not have the {@code IntegrationTest} annotation
	 * <em>or</em> it has the {@code IntegrationTest} annotation and specifies
	 * either the {@link TestPhase#SHORT} or {@link TestPhase#BOTH} phase.</li>
	 * </ul>
	 * 
	 * @param annotation
	 *            the {@code IntegrationTest} to check
	 * @return {@code true} if the class or method attached to the annotation
	 *         should be run, {@code false} otherwise.
	 */
	static boolean shouldRun(IntegrationTest annotation) {
		if (annotation == null || annotation.value() == TestPhase.SHORT) {
			return !TestPhase.isLongPhase();
		} else if (annotation.value() == TestPhase.LONG) {
			return TestPhase.isLongPhase();
		} else {
			return true;
		}
	}
}
