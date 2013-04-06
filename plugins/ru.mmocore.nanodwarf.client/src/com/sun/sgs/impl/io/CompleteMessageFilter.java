/*LICENSE*/

package com.sun.sgs.impl.io;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.mina.core.buffer.IoBuffer;

import com.sun.sgs.impl.sharedutil.LoggerWrapper;

// TODO move this functionality into protocol decode; we should
// do framing in the protocol, not the transport. -JM

/**
 * This filter guarantees that only complete messages are delivered to its
 * {@link FilterListener}.
 * <p>
 * It prepends the message length on sending, and reads the length of each
 * message on receiving. If the message is partial, the filter will hold the
 * partial message until the rest of the message is received, even if the
 * message spans multiple calls to {@code filterReceive}.
 * <p>
 * The {@code filterReceive} portion of this filter is not thread-safe since it
 * retains state information about partial messages. For this reason, each
 * source of data should have its own instance, and {@code filterReceive} should
 * be called by only one thread at a time. {@code filterSend}, however, is
 * thread-safe.
 */
class CompleteMessageFilter {

	/** The logger for this class. */
	private static final LoggerWrapper logger = new LoggerWrapper(
			Logger.getLogger(CompleteMessageFilter.class.getName()));

	/** The default recv processing buffer size. */
	private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

	/** The largest we expect the recv processing buffer size to get. */
	private static final int MAX_BUFFER_SIZE = 512 * 1024;

	/** The data being processed, or a partial message awaiting more data. */
	private final IoBuffer msgBuf;

	/**
	 * Default constructor.
	 */
	CompleteMessageFilter() {
		msgBuf = IoBuffer.allocate(DEFAULT_BUFFER_SIZE, false);
	}

	/**
	 * Processes network data of arbitrary length and dispatches zero or more
	 * complete messages to the given {@code listener}. If a partial message
	 * remains, it is buffered until more data is received.
	 * 
	 * @param listener
	 *            the {@code FilterListener} to receive complete messages
	 * @param buf
	 *            the data to filter and optionally deliver to the
	 *            {@code FilterListener}
	 */
	void filterReceive(FilterListener listener, IoBuffer buf) {

		logger.log(Level.FINEST, "processing {0,number,#} bytes",
				buf.remaining());

		// Append the new data to the buffer
		
		try {
			msgBuf.expand(buf.remaining());
		} catch (Exception e) {
			logger.log(Level.WARNING, "FIXME expand Bug");
		}
		msgBuf.put(buf);
		msgBuf.flip();

		processReceiveBuffer(listener);
	}

	private void processReceiveBuffer(FilterListener listener) {

		if (msgBuf.remaining() > MAX_BUFFER_SIZE) {
			logger.log(Level.WARNING,
					"Recv filter buffer is larger than expected: {0,number,#}",
					msgBuf.remaining());
		}

		// Process complete messages, if any
		while (msgBuf.hasRemaining()) {
			if (msgBuf.remaining() < 2) {
				break;
			}

			if (!msgBuf.prefixedDataAvailable(2)) {
				break;
			}

			int msgLen = msgBuf.getShort() & 0xFFFF;

			// Get a read-only buffer view on the complete message
			IoBuffer completeMessage = msgBuf.slice().asReadOnlyBuffer()
					.limit(msgLen);

			// Advance the underlying message buffer
			msgBuf.skip(msgLen);

			logger.log(Level.FINER,
					"dispatching complete message of size {0,number,#}", msgLen);

			try {
				listener.filteredMessageReceived(completeMessage);
			} catch (RuntimeException e) {
				logger.logThrow(Level.WARNING, e,
						"Exception in message disptach; dropping message");

				logger.logThrow(Level.FINE, e,
						"Exception in message disptach; dropping message {0}",
						completeMessage);

				// ignore exception; continue processing the buffer
			}
		}

		msgBuf.compact();

		if (logger.isLoggable(Level.FINEST)) {
			logger.log(Level.FINEST, "partial message {0,number,#} bytes",
					msgBuf.position());
		}
	}

	/**
	 * Prepends the length of the given byte array as a 2-byte {@code short} in
	 * network byte-order, and passes the result to the
	 * {@linkplain FilterListener#sendUnfiltered sendUnfiltered} method of the
	 * given {@code listener}.
	 * 
	 * @param listener
	 *            the {@code FilterListener} on which to send the data
	 * @param message
	 *            the data to filter and forward to the listener
	 */
	void filterSend(FilterListener listener, byte[] message) {
		IoBuffer buffer = IoBuffer.allocate(message.length + 2, false);
		buffer.putShort((short) message.length);
		buffer.put(message);
		buffer.flip();
		// Don't worry about the listener throwing an exception, since
		// this method has no other side effects.
		listener.sendUnfiltered(buffer);
	}
}
