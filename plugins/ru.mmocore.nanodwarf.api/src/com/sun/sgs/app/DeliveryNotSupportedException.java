/*LICENSE*/

package com.sun.sgs.app;

/**
 * An exception that indicates a {@link Delivery delivery guarantee} is not
 * supported.
 */
public class DeliveryNotSupportedException extends RuntimeException {

	/** The serial version for this class. */
	private static final long serialVersionUID = 1L;

	/** The delivery guarantee. */
	private final Delivery delivery;

	/**
	 * Constructs and instance with the specified detail {@code message} and
	 * unsupported {@code delivery} guarantee.
	 * 
	 * @param message
	 *            a detail message, or {@code null}
	 * @param delivery
	 *            an unsupported delivery guarantee
	 */
	public DeliveryNotSupportedException(String message, Delivery delivery) {
		super(message);
		if (delivery == null) {
			throw new NullPointerException("null delivery");
		}
		this.delivery = delivery;
	}

	/**
	 * Returns the delivery guarantee that is not supported (specified during
	 * construction).
	 * 
	 * @return a delivery guarantee
	 */
	public Delivery getDelivery() {
		return delivery;
	}
}
