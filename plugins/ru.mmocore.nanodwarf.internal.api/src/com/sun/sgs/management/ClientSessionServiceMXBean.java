/*LICENSE*/

package com.sun.sgs.management;

import com.sun.sgs.service.ClientSessionService;
import com.sun.sgs.service.Node;

/**
 * The management interface for the client session service.
 * <p>
 * An instance implementing this MBean can be obtained from the from the
 * {@link java.lang.management.ManagementFactory.html#getPlatformMBeanServer()
 * getPlatformMBeanServer} method.
 * <p>
 * The {@code ObjectName} for uniquely identifying this MBean is
 * {@value #MXBEAN_NAME}.
 * 
 */
public interface ClientSessionServiceMXBean {
	/** The name for uniquely identifying this MBean. */
	String MXBEAN_NAME = "com.sun.sgs.service:type=ClientSessionService";

	// Maybe add number of connects/disconnects?
	// number of channels a client is connected to
	// amount of communications traffic this client sends/receives?

	/**
	 * Gets the health of the service.
	 * 
	 * @return health
	 */
	Node.Health getSessionServiceHealth();

	/**
	 * Gets the number of sessions logged into the session service.
	 * 
	 * @return the number of sessions logged in
	 */
	int getNumSessions();

	/**
	 * Gets the login high water.
	 * 
	 * @return the login high water
	 */
	int getLoginHighWater();

	/**
	 * Sets the login high water.
	 * 
	 * @param highWater
	 *            the high water value
	 */
	void setLoginHighWater(int highWater);

	/**
	 * Returns the number of times
	 * {@link ClientSessionService#addSessionStatusListener
	 * addSessionStatusListener} has been called.
	 * 
	 * @return the number of times {@code addSessionStatusListener} has been
	 *         called
	 */
	long getAddSessionStatusListenerCalls();

	/**
	 * Returns the number of times
	 * {@link ClientSessionService#getSessionProtocol getSessionProtocol} has
	 * been called.
	 * 
	 * @return the number of times {@code getSessionProtocol} has been called
	 */
	long getGetSessionProtocolCalls();

	/**
	 * Returns the number of times
	 * {@link ClientSessionService#isRelocatingToLocalNode
	 * isRelocatingToLocalNode} has been called.
	 * 
	 * @return the number of times {@code isRelocatingToLocalNode} has been
	 *         called
	 */
	long getIsRelocatingToLocalNodeCalls();
}
