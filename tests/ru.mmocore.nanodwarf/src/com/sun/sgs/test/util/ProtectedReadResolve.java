/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a protected readResolve method. */
public class ProtectedReadResolve {
	protected Object readResolve() {
		return this;
	}
}
