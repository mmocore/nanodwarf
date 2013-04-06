/*LICENSE*/

package com.sun.sgs.nio.channels;

import java.io.IOException;

/**
 * Checked exception received by a thread when a timeout elapses before an
 * asynchronous I/O operation completes.
 */
public class AbortedByTimeoutException extends IOException {
	/** The version of the serialized representation of this class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an instance of this class.
	 */
	public AbortedByTimeoutException() {
		super();
	}
}
