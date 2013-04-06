/*LICENSE*/

package com.sun.sgs.test.app;

import com.sun.sgs.app.Delivery;
import com.sun.sgs.tools.test.FilteredNameRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the {@link Delivery} enum.
 */
@RunWith(FilteredNameRunner.class)
public class TestDelivery extends Assert {

	@Test
	public void testSupportsDeliveryNullDelivery() {
		for (Delivery delivery : Delivery.values()) {
			try {
				delivery.supportsDelivery(null);
				fail("expected NullPointerException: " + delivery);
			} catch (NullPointerException e) {
				System.err.println(e);
			}
		}
	}

	@Test
	public void testSupportsDeliveryAllCombinations() {
		for (Delivery delivery : Delivery.values()) {
			assertTrue(Delivery.RELIABLE.supportsDelivery(delivery));
			assertTrue(delivery.supportsDelivery(delivery));
			assertTrue(delivery.supportsDelivery(Delivery.UNRELIABLE));
			if (delivery != Delivery.UNRELIABLE) {
				assertFalse(Delivery.UNRELIABLE.supportsDelivery(delivery));
			}
		}
	}
}
