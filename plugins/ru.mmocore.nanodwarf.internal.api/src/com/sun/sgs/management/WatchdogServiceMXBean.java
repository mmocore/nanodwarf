/*LICENSE*/

package com.sun.sgs.management;

import com.sun.sgs.service.Node.Health;
import com.sun.sgs.service.WatchdogService;

/**
 * The management interface for the watchdog service.
 * <p>
 * An instance implementing this MBean can be obtained from the from the
 * {@link java.lang.management.ManagementFactory.html#getPlatformMBeanServer()
 * getPlatformMBeanServer} method.
 * <p>
 * The {@code ObjectName} for uniquely identifying this MBean is
 * {@value #MXBEAN_NAME}.
 * 
 */
public interface WatchdogServiceMXBean {
	/** The name for uniquely identifying this MBean. */
	String MXBEAN_NAME = "com.sun.sgs.service:type=WatchdogService";

	/**
	 * Return the health of the local node.
	 * 
	 * @return the health of the local node.
	 */
	Health getNodeHealth();

	/**
	 * Set the health of the local node. If the specified health is worse than
	 * the node's health, then the node's health is set to the specified health.
	 * If the specified health is better than the node's health, the node's
	 * health will not change.
	 * 
	 * @param health
	 *            a node health
	 */
	void setNodeHealth(Health health);

	/**
	 * Returns the number of times {@link WatchdogService#addNodeListener
	 * addNodeListener} has been called.
	 * 
	 * @return the number of times {@code addNodeListener} has been called
	 */
	long getAddNodeListenerCalls();

	/**
	 * Returns the number of times {@link WatchdogService#addRecoveryListener
	 * addRecoveryListener} has been called.
	 * 
	 * @return the number of times {@code addRecoveryListener} has been called
	 */
	long getAddRecoveryListenerCalls();

	/**
	 * Returns the number of times {@link WatchdogService#getBackup getBackup}
	 * has been called.
	 * 
	 * @return the number of times {@code getBackup} has been called
	 */
	long getGetBackupCalls();

	/**
	 * Returns the number of times {@link WatchdogService#getNode getNode} has
	 * been called.
	 * 
	 * @return the number of times {@code getNode} has been called
	 */
	long getGetNodeCalls();

	/**
	 * Returns the number of times {@link WatchdogService#getNodes getNodes} has
	 * been called.
	 * 
	 * @return the number of times {@code getNodes} has been called
	 */
	long getGetNodesCalls();

	/**
	 * Returns the number of times {@link WatchdogService#getLocalNodeHealth
	 * getLocalNodeHealth} has been called.
	 * 
	 * @return the number of times {@code getLocalNodeHealth} has been called
	 */
	long getGetLocalNodeHealthCalls();

	/**
	 * Returns the number of times
	 * {@link WatchdogService#getLocalNodeHealthNonTransactional
	 * getLocalNodeHealthNonTransactional} has been called.
	 * 
	 * @return the number of times {@code getLocalNodeHealthNonTransactional}
	 *         has been called
	 */
	long getGetLocalNodeHealthNonTransactionalCalls();

	/**
	 * Returns the number of times {@link WatchdogService#isLocalNodeAlive
	 * isLocalNodeAlive} has been called.
	 * 
	 * @return the number of times {@code isLocalNodeAlive} has been called
	 */
	long getIsLocalNodeAliveCalls();

	/**
	 * Returns the number of times
	 * {@link WatchdogService#isLocalNodeAliveNonTransactional
	 * isLocalNodeAliveNonTransactional} has been called.
	 * 
	 * @return the number of times {@code isLocalNodeAliveNonTransactional} has
	 *         been called
	 */
	long getIsLocalNodeAliveNonTransactionalCalls();

	/**
	 * Returns status information about this node.
	 * 
	 * @return status information about this node
	 */
	NodeInfo getStatusInfo();
}
