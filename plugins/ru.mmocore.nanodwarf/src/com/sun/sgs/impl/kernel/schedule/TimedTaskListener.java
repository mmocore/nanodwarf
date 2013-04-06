/*LICENSE*/

package com.sun.sgs.impl.kernel.schedule;

import com.sun.sgs.kernel.schedule.ScheduledTask;

/** Package-private interface for notifying when delayed tasks are ready. */
interface TimedTaskListener {

	/**
	 * Called when a delayed task has reached its time to run.
	 * 
	 * @param task
	 *            the {@code ScheduledTask} that is ready to run
	 */
	void timedTaskReady(ScheduledTask task);

}
