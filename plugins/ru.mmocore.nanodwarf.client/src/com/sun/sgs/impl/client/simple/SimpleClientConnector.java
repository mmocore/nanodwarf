/*LICENSE*/

package com.sun.sgs.impl.client.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Properties;

import com.sun.sgs.impl.client.comm.ClientConnectionListener;
import com.sun.sgs.impl.client.comm.ClientConnector;
import com.sun.sgs.impl.io.SocketEndpoint;
import com.sun.sgs.impl.io.TransportType;
import com.sun.sgs.impl.sharedutil.MessageBuffer;
import com.sun.sgs.impl.sharedutil.PropertiesWrapper;
import com.sun.sgs.io.Connector;

/**
 * A basic implementation of a {@code ClientConnector} which uses an
 * {@code Connector} to establish connections.
 * <p>
 * The {@link SimpleClientConnector#SimpleClientConnector(Properties)
 * constructor} supports the following properties:
 * <p>
 * <ul>
 * 
 * <li><i>Key:</i> {@code host} <br>
 * <i>No default &mdash; required</i> <br>
 * Specifies the server host.
 * <p>
 * <li><i>Key:</i> {@code port} <br>
 * <i>No default &mdash; required</i> <br>
 * Specifies the server port.
 * <p>
 * <li><i>Key:</i> {@code connectTimeout} <br>
 * <i>Default:</i>
 * {@value com.sun.sgs.impl.client.simple.SimpleClientConnector#DEFAULT_CONNECT_TIMEOUT}
 * <br>
 * Specifies the timeout (in milliseconds) for a connect attempt to the server.
 * </ul>
 */
class SimpleClientConnector extends ClientConnector {

	/**
	 * The default timeout for connect operations:
	 * {@value #DEFAULT_CONNECT_TIMEOUT} ms
	 */
	public static final long DEFAULT_CONNECT_TIMEOUT = 5000;

	/**
	 * The default connect failure message:
	 * {@value #DEFAULT_CONNECT_FAILURE_MESSAGE}
	 */
	public static final String DEFAULT_CONNECT_FAILURE_MESSAGE = "Unable to connect to server";

	/** The underlying IO connector. */
	private final Connector<SocketAddress> connector;

	/** The connect timeout. */
	private final long connectTimeout;

	/** The connect timeout watchdog thread. */
	private Thread connectWatchdog;

	/**
	 * Creates a new connector with the given properties.
	 * 
	 * @param properties
	 *            the properties for this connector
	 */
	SimpleClientConnector(Properties properties) {

		String host = properties.getProperty("host");
		if (host == null) {
			throw new IllegalArgumentException("Missing Property: host");
		}

		String portStr = properties.getProperty("port");
		if (portStr == null) {
			throw new IllegalArgumentException("Missing Property: port");
		}
		int port = Integer.parseInt(portStr);
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("Bad port number: " + port);
		}

		PropertiesWrapper wrappedProperties = new PropertiesWrapper(properties);
		connectTimeout = wrappedProperties.getLongProperty("connectTimeout",
				DEFAULT_CONNECT_TIMEOUT);
		// TODO only RELIABLE supported for now.
		TransportType transportType = TransportType.RELIABLE;

		SocketAddress socketAddress = new InetSocketAddress(host, port);
		connector = new SocketEndpoint(socketAddress, transportType)
				.createConnector();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cancel() throws IOException {
		// TODO implement cancel()
		throw new UnsupportedOperationException("Cancel not yet implemented");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect(ClientConnectionListener connectionListener)
			throws IOException {
		SimpleClientConnection connection = new SimpleClientConnection(
				connectionListener);

		connector.connect(connection);
		connectWatchdog = new ConnectWatchdogThread(connectionListener);
		connectWatchdog.start();
	}

	/**
	 * A thread that awaits a connection attempt timeout.
	 */
	private class ConnectWatchdogThread extends Thread {

		/** The listener for timed-out connection attempts. */
		private final ClientConnectionListener listener;

		/**
		 * Creates a new timeout watchdog thread with the specified listener.
		 * 
		 * @param listener
		 *            the timeout listener
		 */
		ConnectWatchdogThread(ClientConnectionListener listener) {
			super("ConnectWatchdogThread-" + connector.getEndpoint().toString());
			this.listener = listener;
			setDaemon(true);
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			boolean connectComplete = false;
			String connectFailureMessage = null;
			try {
				connectComplete = connector.waitForConnect(connectTimeout);
			} catch (IOException e) {
				connectFailureMessage = e.getMessage();
			} catch (InterruptedException e) {
				// ignore
			}
			try {
				if (!connector.isConnected()) {
					String reason = connectFailureMessage != null ? connectFailureMessage
							: DEFAULT_CONNECT_FAILURE_MESSAGE;
					MessageBuffer buf = new MessageBuffer(
							MessageBuffer.getSize(reason));
					buf.putString(reason);
					listener.disconnected(false, buf.getBuffer());
					if (!connectComplete) {
						try {
							connector.shutdown();
						} catch (IllegalStateException e) {
							// ignore; connect attempt may have completed
						}
					}
				}
			} catch (RuntimeException e) {
				// TBD: log exception
			}
		}
	}
}
