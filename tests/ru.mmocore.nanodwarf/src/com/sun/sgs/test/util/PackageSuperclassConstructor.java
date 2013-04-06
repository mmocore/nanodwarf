/*LICENSE*/

package com.sun.sgs.test.util;

import java.io.Serializable;

/**
 * Define a serializable class in a different package with a non-serializable
 * superclass with a default access no-arguments constructor.
 */
public class PackageSuperclassConstructor extends PackageConstructor implements
		Serializable {
	private static final long serialVersionUID = 1;

	public PackageSuperclassConstructor() {
	}
}
