/*LICENSE*/

package com.sun.sgs.test.util;

/** A class with a package-access readResolve method. */
public class PackageReadResolve {
	Object readResolve() {
		return this;
	}
}
