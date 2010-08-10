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

}
