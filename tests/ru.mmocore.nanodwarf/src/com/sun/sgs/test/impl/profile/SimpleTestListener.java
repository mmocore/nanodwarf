/*LICENSE*/

package com.sun.sgs.test.impl.profile;

import com.sun.sgs.profile.ProfileListener;
import com.sun.sgs.profile.ProfileReport;
import java.beans.PropertyChangeEvent;

/** A simple profile listener that notes calls to the public APIs */
class SimpleTestListener implements ProfileListener {
	int propertyChangeCalls = 0;
	long reportedNodeId = -1L;
	int reportCalls = 0;
	int shutdownCalls = 0;
	final Runnable doReport;
	// Make the profile report available to the doReport runnable.
	static ProfileReport report;

	SimpleTestListener() {
		this.doReport = null;
	}

	SimpleTestListener(Runnable doReport) {
		this.doReport = doReport;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		propertyChangeCalls++;
		if (event.getPropertyName().equals("com.sun.sgs.profile.nodeid")) {
			reportedNodeId = (Long) event.getNewValue();
		}
	}

	@Override
	public void report(ProfileReport profileReport) {
		reportCalls++;
		if (doReport != null) {
			SimpleTestListener.report = profileReport;
			doReport.run();
		}
	}

	@Override
	public void shutdown() {
		shutdownCalls++;
	}
}
