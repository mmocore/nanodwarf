/*LICENSE*/

package com.sun.sgs.transport;

import com.sun.sgs.nio.channels.AsynchronousByteChannel;

/**
 * Interface implemented by objects implementing a connection handler. A
 * connection handler is passed to {@link Transport#accept Transport.accept}.
 * When a new connection is received by the transport, {@link #newConnection
 * newConnection} is invoked with the new I/O channel for that connection.
 */
public interface ConnectionHandler {

	/**
	 * Notify the handler that a new connection has been initiated. If an
	 * exception is thrown the connection will be refused. The implementation of
	 * this method should return in a timely manner, starting a separate thread
	 * if necessary to perform any IO on the {@code channel}.
	 * 
	 * @param channel
	 *            on which the new connection can communicate.
	 * @throws Exception
	 *             if the handler rejects the connection.
	 */
	void newConnection(AsynchronousByteChannel channel) throws Exception;

	/**
	 * Notify the handler that the transport encountered an unrecoverable error
	 * and has shutdown.
	 */
	void shutdown();
}
