/*LICENSE*/
package com.sun.sgs.test.util;

import com.sun.sgs.impl.util.AbstractKernelRunnable;

/**
 * A subclass of {@code AbstractKernelRunnable} used for test purposes. For ease
 * of use, this class supplies a public no-arg constructor (which
 * {@code AbstractKernelRunnable} lacks).
 */
public abstract class TestAbstractKernelRunnable extends AbstractKernelRunnable {

	/** Constructs an instance. */
	public TestAbstractKernelRunnable() {
		super(null);
	}
}
