/*LICENSE*/

package com.sun.sgs.services.app;

/**
 * A callback interface used to be notified when the result of an asynchronous
 * task has completed, or if that task failed. All implementations must
 * implement {@code Serializable} and may optionally implement
 * {@code ManagedObject}.
 */
public interface AsyncTaskCallback<T> {

	/**
	 * Notifies the callback listener of the given result. This method will be
	 * called within a transaction.
	 * 
	 * @param t
	 *            the result returned from successfully running the task
	 */
	public void notifyResult(T t);

	/**
	 * Notifies the callback listener that the task failed to complete. This may
	 * happen because of a failure running the task, or because the node where
	 * this task was running failed. It is up to the callback implementation to
	 * decide how to react to failures.
	 * 
	 * @param t
	 *            the reson that the task failed, or {@code null} if the failure
	 *            was due to the executing node itself failing
	 */
	public void notifyFailed(Throwable t);

}
