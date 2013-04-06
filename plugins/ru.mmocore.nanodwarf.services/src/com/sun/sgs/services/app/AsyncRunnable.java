/*LICENSE*/

package com.sun.sgs.services.app;

/**
 * A {@code Runnable}-like interface that provides access to a
 * {@code TransactionRunner} for running transactions.
 */
public interface AsyncRunnable {

	/**
	 * Runs this method, with no notification about the result. The provided
	 * {@code TransactionRunner} may be used during the run of this method to
	 * run transactional tasks.
	 * 
	 * @param transactionRunner
	 *            a {@code TransactionRunner} used to start transactions
	 */
	public void run(TransactionRunner transactionRunner);

}
