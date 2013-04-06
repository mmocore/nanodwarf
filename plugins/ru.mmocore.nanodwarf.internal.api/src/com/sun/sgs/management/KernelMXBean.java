/*LICENSE*/

package com.sun.sgs.management;

/**
 * The management interface for the kernel.
 * <p>
 * An instance implementing this MBean can be obtained from the
 * {@link java.lang.management.ManagementFactory.html#getPlatformMBeanServer()
 * getPlatformMBeanServer} method.
 * <p>
 * The {@code ObjectName} for uniquely identifying this MBean is
 * {@value #MXBEAN_NAME}.
 */
public interface KernelMXBean {

	/** The name for uniquely identifying this MBean. */
	String MXBEAN_NAME = "com.sun.sgs:type=Kernel";

	/** Requests that this node starts an orderly shutdown. */
	void requestShutdown();

}
