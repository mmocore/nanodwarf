/*LICENSE*/

package com.sun.sgs.services.app;

/**
 * A {@code Callable}-like interface that provides access to a
 * {@code TransactionRunner} for running transactions.
 * 
 * @param <T>
 *            the type returned by this callable on completion
 */
public interface AsyncCallable<T> {

	/**
	 * Calls this method, usually doing some work that returns a value if
	 * successful or fails with an exception. The provided
	 * {@code TransactionRunner} may be used during the run of this method to
	 * run transactional tasks.
	 * 
	 * @param transactionRunner
	 *            a {@code TransactionRunner} used to start transactions
	 * 
	 * @return the result of calling this method
	 * 
	 * @throws Exception
	 *             if there is any error calling this method
	 */
	public T call(TransactionRunner transactionRunner) throws Exception;

}
