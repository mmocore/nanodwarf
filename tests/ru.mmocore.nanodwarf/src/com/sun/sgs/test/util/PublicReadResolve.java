/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a public readResolve method. */
public class PublicReadResolve {
	public Object readResolve() {
		return this;
	}
}
