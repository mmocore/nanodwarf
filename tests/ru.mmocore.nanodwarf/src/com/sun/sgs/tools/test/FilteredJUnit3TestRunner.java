/*LICENSE*/

package com.sun.sgs.tools.test;

import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.notification.RunNotifier;

/**
 * This is a custom implementation of JUnit's {@code JUnit38ClassRunner} that
 * adds support for filtering entire classes of tests based on the
 * {@link IntegrationTest} annotation. This class is introduced only for
 * backwards compatibility with old JUnit3 tests. Filtering at the test method
 * level with the {@code IntegrationTest} annotation is not supported and
 * neither is filtering using the "test.method" system property. It is strongly
 * encouraged that any JUnit3 tests be ported to use JUnit4 and either the
 * {@link FilteredNameRunner} or {@link ParameterizedFilteredNameRunner}
 * instead.
 */
public class FilteredJUnit3TestRunner extends JUnit38ClassRunner {

	private boolean empty = false;

	/**
	 * Constructs a new {@code FilteredJUnit3TestRunner} for the specified
	 * class.
	 * 
	 * @param c
	 *            the class to run with this runner
	 */
	public FilteredJUnit3TestRunner(Class<?> c) {
		super(c);

		IntegrationTest annotation = c.getAnnotation(IntegrationTest.class);
		if (!TestPhase.shouldRun(annotation)) {
			empty = true;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Skips running the tests if they have been filtered out by a class level
	 * {@link IntegrationTest} annotation.
	 */
	public void run(RunNotifier runNotifier) {
		if (empty) {
			return;
		}

		super.run(runNotifier);
	}

}
