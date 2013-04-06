/*LICENSE*/

package com.sun.sgs.services.app;

import com.sun.sgs.app.Task;

/**
 * An interface used to run transactions. Each transaction has full access to
 * the {@code AppContext} and all associated {@code Manager}s.
 */
public interface TransactionRunner {

	/**
	 * Runs the given task synchronously, returning when the task has completed
	 * or throwing an exception if the task fails. Normal re-try is handled by
	 * this method, so an exception signifies a non-retriable, permanent
	 * failure.
	 * <p>
	 * Note that unlike with the methods on {@code TaskManager}, the
	 * {@code Task} instance provided here does not need to implement
	 * {@code Serializable} nor {@code ManagedObject}.
	 * 
	 * @param task
	 *            a {@code Task} that will be run in a transaction
	 * 
	 * @throws Exception
	 *             if the task fails permanently
	 */
	public void runTransaction(Task task) throws Exception;

}
