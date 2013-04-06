/*LICENSE*/

package com.sun.sgs.impl.kernel.schedule;

import com.sun.sgs.app.ExceptionRetryStatus;
import com.sun.sgs.app.TransactionTimeoutException;
import com.sun.sgs.kernel.schedule.ScheduledTask;
import com.sun.sgs.kernel.schedule.SchedulerRetryAction;
import com.sun.sgs.tools.test.FilteredNameRunner;
import java.util.Properties;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for the {@code NowOrLaterRetryPolicy} class in isolation.
 */
@RunWith(FilteredNameRunner.class)
public class TestNowOrLaterRetryPolicy {

	private NowOrLaterRetryPolicy policy;
	private ScheduledTask task;

	@Before
	public void setup() {
		Properties emptyProps = new Properties();
		policy = new NowOrLaterRetryPolicy(emptyProps);

		task = EasyMock.createMock(ScheduledTask.class);
	}

	@After
	public void tearDown() {
		policy = null;
		task = null;
	}

	private void setupTask(Throwable result) {
		EasyMock.expect(task.getLastFailure()).andStubReturn(result);
	}

	private void replayMocks() {
		EasyMock.replay(task);
	}

	private void verifyMocks() {
		EasyMock.verify(task);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullTask() {
		policy.getRetryAction(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testNullResult() {
		setupTask(null);
		replayMocks();
		policy.getRetryAction(task);
	}

	@Test
	public void testRetryableTrueResult() {
		setupTask(new RetryableException(true));
		EasyMock.expect(task.getTryCount()).andStubReturn(1);
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.RETRY_NOW, action);
		verifyMocks();
	}

	@Test
	public void testRetryableFalseResult() {
		setupTask(new RetryableException(false));
		EasyMock.expect(task.isRecurring()).andStubReturn(true);
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.DROP, action);
		verifyMocks();
	}

	@Test
	public void testRetryableTrueResultAndRetryCountAboveBackoffThreshold() {
		setupTask(new RetryableException(true));
		// program the expected behavior of the task
		EasyMock.expect(task.getTryCount()).andStubReturn(
				NowOrLaterRetryPolicy.DEFAULT_RETRY_BACKOFF_THRESHOLD + 1);
		EasyMock.expect(task.getTimeout()).andStubReturn(100L);
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.RETRY_LATER, action);
		verifyMocks();
	}

	@Test
	public void testTimeoutResultAndRetryCountAboveBackoffThreshold() {
		setupTask(new TransactionTimeoutException("timed out"));
		// program the expected behavior of the task
		EasyMock.expect(task.getTryCount()).andStubReturn(
				NowOrLaterRetryPolicy.DEFAULT_RETRY_BACKOFF_THRESHOLD + 1);
		EasyMock.expect(task.getTimeout()).andStubReturn(100L);
		task.setTimeout(100L * 2);
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.RETRY_LATER, action);
		verifyMocks();
	}

	@Test
	public void testTimeoutResultAndTimeoutSetTooHigh() {
		setupTask(new TransactionTimeoutException("timed out"));
		// program the expected behavior of the task
		EasyMock.expect(task.getTryCount()).andStubReturn(
				NowOrLaterRetryPolicy.DEFAULT_RETRY_BACKOFF_THRESHOLD + 1);
		EasyMock.expect(task.getTimeout()).andStubReturn(
				(long) (Integer.MAX_VALUE / 2 + Integer.MAX_VALUE / 4));
		// note that task.setTimeout is not called, which is verified below
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.RETRY_LATER, action);
		verifyMocks();
	}

	@Test
	public void testNotRetryableExceptionAndRecurringTask() {
		setupTask(new Exception());
		// record recurring task
		EasyMock.expect(task.isRecurring()).andStubReturn(true);
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.DROP, action);
		verifyMocks();
	}

	@Test
	public void testNotRetryableExceptionAndNotRecurringTask() {
		setupTask(new Exception());
		// record recurring task
		EasyMock.expect(task.isRecurring()).andStubReturn(false);
		replayMocks();

		// verify
		SchedulerRetryAction action = policy.getRetryAction(task);
		Assert.assertEquals(SchedulerRetryAction.DROP, action);
		verifyMocks();
	}

	private static class RetryableException extends Exception implements
			ExceptionRetryStatus {

		private final boolean retryable;

		public RetryableException(boolean retryable) {
			this.retryable = retryable;
		}

		public boolean shouldRetry() {
			return retryable;
		}

	}

}
