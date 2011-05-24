package com.gemserk.datastore;

import java.util.Collection;
import java.util.Set;

public interface DataStore {
	
	/**
	 * @param data
	 * @return the id of the entry
	 */
	String submit(Data data);
	
	/**
	 * Returns all the data tagged with the given tags
	 * @param tags
	 * @return
	 */
	Collection<Data> get(Set<String> tags);

	/**
	 * Removes from all items with the given tags.
	 * @param tags the Tags the items should have to be removed.
	 */
	void remove(Set<String> tags);
	
	/**
	 * Updates the specified data if it exists in the store.
	 * @param data
	 */
	void update(Data data);
}
