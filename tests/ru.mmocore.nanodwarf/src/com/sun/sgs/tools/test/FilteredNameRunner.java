/*LICENSE*/

package com.sun.sgs.tools.test;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * This is a custom implementation of JUnit4's {@code BlockJUnit4ClassRunner}
 * that adds support for reporting the name of each test when it starts.
 * Additionally, it automatically filters which tests to run based on a
 * {@link TestFilter}.
 */
public class FilteredNameRunner extends BlockJUnit4ClassRunner {

	private boolean empty = false;

	/**
	 * Constructs a {@code FilteredNameRunner} for running tests in the given
	 * class.
	 * 
	 * @param c
	 *            the class to run tests with this runner
	 * @throws java.lang.Exception
	 *             if an error occurs initializing the runner
	 */
	public FilteredNameRunner(Class<?> c) throws Exception {
		super(c);

		// enable the filter
		try {
			filter(new TestFilter(c));
		} catch (NoTestsRemainException e) {
			empty = true;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Skips running the tests if they have all been filtered out by a
	 * {@link IntegrationTest} annotations.
	 */
	public void run(RunNotifier runNotifier) {
		if (empty) {
			return;
		}

		runNotifier.addListener(new RunListenerImpl());
		super.run(runNotifier);
	}

	/**
	 * A custom {@code RunListener} that prints out the name of each test to
	 * standard error when it is started.
	 */
	private static class RunListenerImpl extends RunListener {
		public void testStarted(Description description) throws Exception {
			if (description.isTest()) {
				System.err.println("Testcase: " + description.getDisplayName());
			}
		}
	}

}
