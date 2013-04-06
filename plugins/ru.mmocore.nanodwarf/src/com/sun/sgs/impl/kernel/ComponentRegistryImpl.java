/*LICENSE*/

package com.sun.sgs.impl.kernel;

import com.sun.sgs.kernel.ComponentRegistry;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.MissingResourceException;

/**
 * This is a simple implementation of <code>ComponentRegistry</code> used to
 * startup and configure the system and individual applications. It can have any
 * objects added to it, but components cannot be removed. This implementation is
 * not thread-safe.
 */
class ComponentRegistryImpl implements ComponentRegistry {

	// the set of components
	private LinkedHashSet<Object> componentSet;

	/**
	 * Creates an empty instance of <code>ComponentRegistryImpl</code>.
	 */
	ComponentRegistryImpl() {
		componentSet = new LinkedHashSet<Object>();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T getComponent(Class<T> type) {
		Object matchingComponent = null;

		// iterate through the available components
		for (Object component : componentSet) {
			// see if provided type matches the component
			if (type.isAssignableFrom(component.getClass())) {
				// if this isn't the first match, it's an error
				if (matchingComponent != null) {
					throw new MissingResourceException("More than one "
						+ "matching component", type.getName(), null);
				}
				matchingComponent = component;
			}
		}

		// if no matches were found, it's an error
		if (matchingComponent == null) {
			throw new MissingResourceException("No matching components",
				type.getName(), null);
		}

		return type.cast(matchingComponent);
	}

	/**
	 * Adds a component to the set of available components.
	 * 
	 * @param component
	 *            the component to add to the registry
	 */
	void addComponent(Object component) {
		componentSet.add(component);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<Object> iterator() {
		return Collections.unmodifiableSet(componentSet).iterator();
	}
}
