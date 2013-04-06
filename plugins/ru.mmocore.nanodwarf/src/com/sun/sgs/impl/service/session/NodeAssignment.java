/*LICENSE*/

package com.sun.sgs.impl.service.session;

/**
 * Contains a node ID assignment.
 * 
 * TBD: add a service-level interface for ClientSession?
 */
public interface NodeAssignment {

	/**
	 * Returns the node ID for this instance.
	 * 
	 * @return the node ID for this instance
	 */
	long getNodeId();

	/**
	 * Returns the ID of the new node that the client session is relocating to,
	 * or {@code -1} if the associated client session is not relocating.
	 * 
	 * @return the node ID of the new node, or {@code -1} if the session is not
	 *         relocating
	 */
	long getRelocatingToNodeId();
}
