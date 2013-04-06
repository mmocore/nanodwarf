/*LICENSE*/

package com.sun.sgs.impl.service.nodemap.affinity;

/**
 * Thrown if the {@link AffinityGroupFinder} could not find affinity groups.
 */
public class AffinityGroupFinderFailedException extends Exception {
	/** The version of the serialized form. */
	private static final long serialVersionUID = 1;

	/**
	 * Creates an instance of this class with the specified detail message.
	 * 
	 * @param message
	 *            the detail message or <code>null</code>
	 */
	public AffinityGroupFinderFailedException(String message) {
		super(message);
	}

	/**
	 * Creates an instance of this class with the specified detail message and
	 * cause.
	 * 
	 * @param message
	 *            the detail message or <code>null</code>
	 * @param cause
	 *            the cause or <code>null</code>
	 */
	public AffinityGroupFinderFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
