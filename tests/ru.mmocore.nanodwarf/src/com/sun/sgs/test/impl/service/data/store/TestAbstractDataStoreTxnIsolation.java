/*LICENSE*/

package com.sun.sgs.test.impl.service.data.store;

import com.sun.sgs.service.store.DataStore;
import com.sun.sgs.test.util.InMemoryDataStore;
import com.sun.sgs.tools.test.FilteredNameRunner;
import org.junit.runner.RunWith;

/**
 * Tests the isolation that {@link AbstractDataStore} enforces between
 * transactions.
 */
@RunWith(FilteredNameRunner.class)
public class TestAbstractDataStoreTxnIsolation extends BasicTxnIsolationTest {

	/** Creates an {@link InMemoryDataStore}. */
	protected DataStore createDataStore() {
		return new InMemoryDataStore(props, env.systemRegistry, txnProxy);
	}
}
