/*LICENSE*/

package com.sun.sgs.impl.service.nodemap.affinity;

import java.util.NavigableSet;

/**
 * The affinity group finder finds affinity groups within a Darkstar cluster
 * using the LPA algorithm.
 */
public interface LPAAffinityGroupFinder extends AffinityGroupFinder {
	/**
	 * Finds affinity groups across all nodes in the Darkstar cluster. If no
	 * groups are found, an empty set is returned. If an error is encountered
	 * during a run, an {@code AffinityGroupFinderFailedException} is thrown.
	 * Errors include nodes not responding to server requests.
	 * 
	 * @throws AffinityGroupFinderFailedException
	 *             if there is an error
	 * @throws IllegalStateException
	 *             if the finder is disabled or shut down
	 * @return the affinity groups, or an empty set if none are found
	 */
	NavigableSet<RelocatingAffinityGroup> findAffinityGroups()
			throws AffinityGroupFinderFailedException;
}
