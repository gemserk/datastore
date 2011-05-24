package com.gemserk.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class DataStoreInMemoryImpl implements DataStore {
	
	Collection<Data> dataCollection = new ArrayList<Data>();

	@Override
	public Collection<Data> get(Set<String> tags) {
		return dataCollection;
	}

	@Override
	public String submit(Data data) {
		data.setId(Integer.toHexString(System.identityHashCode(data)));
		dataCollection.add(data);
		return data.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(final Set<String> tags) {
		
		ArrayList<Data> filteredCollection = new ArrayList(Collections2.filter(dataCollection, new Predicate<Data>() {
			@Override
			public boolean apply(Data data) {
				return data.getTags().containsAll(tags);
			}
		}));
		
		dataCollection.removeAll(filteredCollection);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(final Data data) {
		ArrayList<Data> filteredCollection = new ArrayList(Collections2.filter(dataCollection, new Predicate<Data>() {
			@Override
			public boolean apply(Data internalData) {
				return internalData.getId().equals(data.getId());
			}
		}));
		dataCollection.removeAll(filteredCollection);
		dataCollection.add(data);
	}

}
