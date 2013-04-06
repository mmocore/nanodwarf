/*LICENSE*/

package com.sun.sgs.app;

/**
 * Thrown when an operation fails because there are not enough resources to send
 * or receive a message.
 * 
 * @see ClientSession#send
 * @see Channel#send
 * @see ClientSessionListener#receivedMessage
 */
public class MessageRejectedException extends ResourceUnavailableException {

	/** The version of the serialized form. */
	private static final long serialVersionUID = 1;

	/**
	 * Creates an instance of this class with the specified detail message.
	 * 
	 * @param message
	 *            the detail message or <code>null</code>
	 */
	public MessageRejectedException(String message) {
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
	public MessageRejectedException(String message, Throwable cause) {
		super(message, cause);
	}
}
