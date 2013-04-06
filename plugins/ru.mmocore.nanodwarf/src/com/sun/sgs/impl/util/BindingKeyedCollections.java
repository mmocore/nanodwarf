/*LICENSE*/

package com.sun.sgs.impl.util;

import com.sun.sgs.kernel.ComponentRegistry;

/**
 * A factory for creating collections whose key/value pairs are stored as
 * service bindings. This factory may be obtained from the
 * {@link ComponentRegistry}.
 * 
 * <p>
 * The {@code keyPrefix} supplied to the {@link #newMap newMap} and
 * {@link #newSet set} methods specifies the service binding name prefix to use
 * for each key in the map. That is, a value in the map (or set) is stored in
 * the data service using its associated key (a String) as a suffix to the
 * {@code keyPrefix} specified during construction.
 */
public interface BindingKeyedCollections {

	/**
	 * Constructs an instance of a {@link BindingKeyedMap} with the specified
	 * {@code keyPrefix}.
	 * 
	 * @param <V>
	 *            the type of the map's values
	 * @param keyPrefix
	 *            a key prefix
	 * @return a {@code BindingKeyedMap} with the specified {@code keyPrefix}
	 * @throws IllegalArgumentException
	 *             if {@code keyPrefix} is empty
	 */
	<V> BindingKeyedMap<V> newMap(String keyPrefix);

	/**
	 * Constructs an instance of a {@link BindingKeyedSet} with the specified
	 * {@code keyPrefix}.
	 * 
	 * @param <V>
	 *            the type of the set's values
	 * @param keyPrefix
	 *            a key prefix
	 * @return a {@code BindingKeyedSet} with the specified {@code keyPrefix}
	 * @throws IllegalArgumentException
	 *             if {@code keyPrefix} is empty
	 */
	<V> BindingKeyedSet<V> newSet(String keyPrefix);
}
