/*LICENSE*/

package com.sun.sgs.management;

/**
 * The management interface for the affinity graph builder.
 */
public interface AffinityGraphBuilderMXBean {
	/** The name for uniquely identifying this MBean. */
	String MXBEAN_NAME = "com.sun.sgs:type=AffinityGraphBuilder";

	/**
	 * Returns the configured time, in milliseconds, for each snapshot.
	 * 
	 * @return the snapshot time period, in milliseconds
	 */
	long getSnapshotPeriod();

	/**
	 * Returns the configured number of full snapshots to consider live. Dead
	 * snapshots are eventually removed.
	 * 
	 * @return the number of live snapshots
	 */
	int getSnapshotCount();

	/**
	 * Returns the number of vertices in the affinity graph.
	 * 
	 * @return the number of vertices in the affinity graph
	 */
	long getNumberVertices();

	/**
	 * Returns the number of edges in the affinity graph.
	 * 
	 * @return the number of edges in the affinity graph
	 */
	long getNumberEdges();

	/**
	 * Returns the number of updates made to the affinity graph, which add graph
	 * data.
	 * 
	 * @return the number of updates made to the affinity graph
	 */
	long getUpdateCount();

	/**
	 * Returns the number of times the affinity graph has been pruned to remove
	 * dead data.
	 * 
	 * @return the number of times the affinity graph has been pruned
	 */
	long getPruneCount();

	/**
	 * Returns the time, in milliseconds, spent processing the affinity graph
	 * due to modifications such as updates or pruning.
	 * 
	 * @return the amount of time spent processing the affinity graph
	 */
	long getProcessingTime();
}
