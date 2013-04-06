/*LICENSE*/

package com.sun.sgs.test.util;

/**
 * Contains constant values to be used with tests.
 */
public final class Constants {

	/**
	 * This value is the maximum clock granularity that is expected for calls to
	 * {@link java.lang.System#currentTimeMillis() System.currentTimeMillis()}
	 * across all operating systems. In other words, this is the shortest amount
	 * of time (in milliseconds) between calls to
	 * {@code System.currentTimeMillis()} where you can be guaranteed to get
	 * different values.
	 */
	public static final Long MAX_CLOCK_GRANULARITY = Long.getLong(
			"test.clock.granularity", 20);

	/**
	 * This class should not be instantiated,
	 */
	private Constants() {

	}
}
