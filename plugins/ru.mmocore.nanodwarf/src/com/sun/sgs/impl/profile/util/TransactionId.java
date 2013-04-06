/*LICENSE*/

package com.sun.sgs.impl.profile.util;

import java.math.BigInteger;

/**
 * A utility that wraps a transaction identifier (an array of bytes) by assuming
 * that the identifier represents a non-negative long value. This is
 * particularly useful for {@code ProfileListener}s that want to key off of
 * transaction identifiers. Each transaction's identifier is available from its
 * associated {@code ProfileReport}.
 */
public final class TransactionId {

	// the non-negative numeric value of the id
	private final long txnId;

	/**
	 * Creates an instance of {@code TransactionId}.
	 * 
	 * @param txnId
	 *            the transaction identifier
	 */
	public TransactionId(byte[] txnId) {
		// assert that this really fits into a long
		assert txnId.length <= 8;
		this.txnId = (new BigInteger(1, txnId)).longValue();
	}

	/** {@inheritDoc} */
	public String toString() {
		return String.valueOf(txnId);
	}

	/** {@inheritDoc} */
	public boolean equals(Object o) {
		if ((o == null) || (!(o instanceof TransactionId))) {
			return false;
		}
		return txnId == ((TransactionId) o).txnId;
	}

	/** {@inheritDoc} */
	public int hashCode() {
		// the hash code specified in the javadoc for java.lang.Long
		return (int) (txnId ^ (txnId >>> 32));
	}

}
