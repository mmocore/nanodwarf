/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a private writeReplace method. */
public class PrivateWriteReplace {
	private Object writeReplace() {
		return this;
	}
}
