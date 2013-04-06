/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a protected writeReplace method. */
public class ProtectedWriteReplace {
	protected Object writeReplace() {
		return this;
	}
}
