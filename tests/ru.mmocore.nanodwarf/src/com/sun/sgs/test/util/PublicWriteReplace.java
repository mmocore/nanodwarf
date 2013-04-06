/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a public writeReplace method. */
public class PublicWriteReplace {
	public Object writeReplace() {
		return this;
	}
}
