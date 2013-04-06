/*LICENSE*/

package com.sun.sgs.management;

import com.sun.sgs.app.ChannelManager;

/**
 * The management interface for the channel service.
 * <p>
 * An instance implementing this MBean can be obtained from the from the
 * {@link java.lang.management.ManagementFactory.html#getPlatformMBeanServer()
 * getPlatformMBeanServer} method.
 * <p>
 * The {@code ObjectName} for uniquely identifying this MBean is
 * {@value #MXBEAN_NAME}.
 * 
 */
public interface ChannelServiceMXBean {
	/** The name for uniquely identifying this MBean. */
	String MXBEAN_NAME = "com.sun.sgs.service:type=ChannelService";

	// Maybe add the number of channels in the system?
	// Maybe add, for each channel, a way to get to the channel name,
	// approx number of users, number joins/leaves,
	// channel coordinator node id?
	// amount of traffic on channel

	/**
	 * Returns the number of times {@link ChannelManager#createChannel
	 * createChannel} has been called.
	 * 
	 * @return the number of times {@code createChannel} has been called
	 */
	long getCreateChannelCalls();

	/**
	 * Returns the number of times {@link ChannelManager#getChannel getChannel}
	 * has been called.
	 * 
	 * @return the number of times {@code getChannel} has been called
	 */
	long getGetChannelCalls();
}
