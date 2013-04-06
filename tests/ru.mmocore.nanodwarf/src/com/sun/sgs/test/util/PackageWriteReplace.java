/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a package-access writeReplace method. */
public class PackageWriteReplace {
	Object writeReplace() {
		return this;
	}
}
