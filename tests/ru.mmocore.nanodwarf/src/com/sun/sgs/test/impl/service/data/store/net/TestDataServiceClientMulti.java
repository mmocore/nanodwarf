/*LICENSE*/

package com.sun.sgs.test.impl.service.data.store.net;

import com.sun.sgs.test.impl.service.data.BasicDataServiceMultiTest;
import com.sun.sgs.test.util.SgsTestNode;
import java.util.Properties;

/**
 * Perform multi-node tests on the {@code DataService} using the network data
 * store.
 */
public class TestDataServiceClientMulti extends BasicDataServiceMultiTest {

	@Override
	protected Properties getServerProperties() throws Exception {
		return SgsTestNode.getDefaultProperties(appName, null, null);
	}
}
