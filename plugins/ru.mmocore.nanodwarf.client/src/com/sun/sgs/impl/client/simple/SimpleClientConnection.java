/*LICENSE*/

package com.sun.sgs.impl.client.simple;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.impl.client.comm.ClientConnection;
import com.sun.sgs.impl.client.comm.ClientConnectionListener;
import com.sun.sgs.impl.sharedutil.HexDumper;
import com.sun.sgs.impl.sharedutil.LoggerWrapper;
import com.sun.sgs.io.Connection;
import com.sun.sgs.io.ConnectionListener;

/**
 * A {@code ClientConnection} is the central point of communication with the
 * server. This {@code SimpleClientConnection} uses an {@link Connection} for
 * its transport. All outbound messages to the server go out via the
 * Connection.send. Incoming messages come in on the
 * {@code ConnectionListener.messageReceived} callback and are dispatched to the
 * appropriate callback on either the associated
 * {@link ClientConnectionListener}.
 */
class SimpleClientConnection implements ClientConnection, ConnectionListener {

	/** The logger for this class. */
	private static final LoggerWrapper logger = new LoggerWrapper(
			Logger.getLogger(SimpleClientConnection.class.getName()));

	/** the listener for this connection. */
	private final ClientConnectionListener ccl;

	/** The underlying IO connection, or {@code null} if disconnected. */
	private Connection myHandle = null;

	/**
	 * Creates a new connection implementation.
	 * 
	 * @param listener
	 *            the listener to receive notification of events
	 */
	SimpleClientConnection(ClientConnectionListener listener) {
		this.ccl = listener;
	}

	// ClientConnection methods

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation disconnects the underlying {@code Connection}.
	 */
	public void disconnect() throws IOException {
		if (myHandle == null) {
			RuntimeException re = new IllegalStateException("Not connected");
			logger.logThrow(Level.FINE, re, re.getMessage());
			throw re;
		}
		myHandle.close();
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendMessage(ByteBuffer message) throws IOException {
		if (logger.isLoggable(Level.FINEST)) {
			logger.log(Level.FINEST, "send on {0}: {1}", myHandle,
					HexDumper.format(message));
		}
		try {
			byte[] bytes = new byte[message.remaining()];
			message.get(bytes);
			myHandle.sendBytes(bytes);
		} catch (IOException e) {
			logger.logThrow(Level.FINE, e, "Send failed:");
			throw e;
		}
	}

	// ConnectionListener methods

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation notifies the associated
	 * {@code ClientConnectionListener}.
	 */
	public void connected(Connection conn) {
		if (logger.isLoggable(Level.FINER)) {
			logger.log(Level.FINER, "connected: {0}", conn);
		}
		this.myHandle = conn;
		ccl.connected(this);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation notifies the associated
	 * {@code ClientConnectionListener}.
	 */
	public void disconnected(Connection conn) {
		if (logger.isLoggable(Level.FINER)) {
			logger.log(Level.FINER, "disconnected: {0}", conn);
		}
		assert conn.equals(this.myHandle);
		// TODO what is graceful?
		ccl.disconnected(true, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void exceptionThrown(Connection conn, Throwable exception) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.logThrow(Level.WARNING, exception, "exception on: {0}: ",
					conn);
		}
		assert conn.equals(this.myHandle);

		// TODO should we take any action here? Bubble this up
		// to the CCL somehow?
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation forwards the message to the associated
	 * {@code ClientConnectionListener}.
	 */
	public void bytesReceived(Connection conn, byte[] message) {
		if (logger.isLoggable(Level.FINEST)) {
			logger.log(Level.FINEST, "recv on {0}: {1}", conn,
					HexDumper.format(message, 0x50));
		}
		assert conn.equals(this.myHandle);
		ccl.receivedMessage(message);
	}

}
