/*LICENSE*/

package com.sun.sgs.impl.service.session;

import com.sun.sgs.impl.profile.ProfileCollectorImpl;
import com.sun.sgs.management.ClientSessionServiceMXBean;
import com.sun.sgs.profile.AggregateProfileOperation;
import com.sun.sgs.profile.ProfileCollector;
import com.sun.sgs.profile.ProfileCollector.ProfileLevel;
import com.sun.sgs.profile.ProfileConsumer;
import com.sun.sgs.profile.ProfileConsumer.ProfileDataType;
import com.sun.sgs.profile.ProfileOperation;
import com.sun.sgs.service.Node;

/**
 * The Statistics MBean object for the client session service.
 */
class ClientSessionServiceStats implements ClientSessionServiceMXBean {

	final ProfileOperation addSessionStatusListenerOp;
	final ProfileOperation getSessionProtocolOp;
	final ProfileOperation isRelocatingToLocalNodeOp;

	private final ClientSessionServiceImpl service;

	ClientSessionServiceStats(ProfileCollector collector,
			ClientSessionServiceImpl service) {
		this.service = service;
		ProfileConsumer consumer = collector
				.getConsumer(ProfileCollectorImpl.CORE_CONSUMER_PREFIX
						+ "ClientSessionService");
		ProfileLevel level = ProfileLevel.MAX;
		ProfileDataType type = ProfileDataType.TASK_AND_AGGREGATE;

		addSessionStatusListenerOp = consumer.createOperation(
				"addSessionStatusListener", type, level);
		getSessionProtocolOp = consumer.createOperation("getSessionProtocol",
				type, level);
		isRelocatingToLocalNodeOp = consumer.createOperation(
				"isRelocatingToLocalNode", type, level);
	}

	/** {@inheritDoc} */
	public long getAddSessionStatusListenerCalls() {
		return ((AggregateProfileOperation) addSessionStatusListenerOp)
				.getCount();
	}

	/** {@inheritDoc} */
	public long getGetSessionProtocolCalls() {
		return ((AggregateProfileOperation) getSessionProtocolOp).getCount();
	}

	/** {@inheritDoc} */
	public long getIsRelocatingToLocalNodeCalls() {
		return ((AggregateProfileOperation) isRelocatingToLocalNodeOp)
				.getCount();
	}

	@Override
	public Node.Health getSessionServiceHealth() {
		return service.getHealth();
	}

	@Override
	public int getNumSessions() {
		return service.getNumSessions();
	}

	@Override
	public int getLoginHighWater() {
		return service.getLoginHighWater();
	}

	@Override
	public void setLoginHighWater(int highWater) {
		service.setLoginHighWater(highWater);
	}
}
