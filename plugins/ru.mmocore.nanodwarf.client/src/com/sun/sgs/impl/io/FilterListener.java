/*LICENSE*/

package com.sun.sgs.impl.io;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * Receives the messages resulting from processing by a
 * {@link CompleteMessageFilter}.
 */
interface FilterListener {
	/**
	 * Notifies this listener that a complete, filtered message has been
	 * received and should be dispatched to the final recipient.
	 * 
	 * @param buf
	 *            a {@code MINA IoBuffer} containing the complete message
	 */
	void filteredMessageReceived(IoBuffer buf);

	/**
	 * Notifies this listener that an outbound message has been filtered
	 * (prepending the message length) and should be sent "raw" on the
	 * underlying transport.
	 * 
	 * @param buf
	 *            a {@code MINA ByteBuffer} containing the message to send
	 */
	void sendUnfiltered(IoBuffer buf);
}
