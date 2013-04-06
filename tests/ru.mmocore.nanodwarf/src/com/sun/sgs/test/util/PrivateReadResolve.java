/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a private readResolve method. */
public class PrivateReadResolve {
	private Object readResolve() {
		return this;
	}
}
