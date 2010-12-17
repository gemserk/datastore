package com.gemserk.datastore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import com.google.common.collect.Sets;


public class DataStoreInMemoryImplTest {
	
	@Test
	public void shouldRemoveItems() {
		
		DataStoreInMemoryImpl dataStore = new DataStoreInMemoryImpl();
		
		Data elementWithTagA = new Data(Sets.newHashSet("A"), new HashMap<String, Object>());
		Data elementWithTagB = new Data(Sets.newHashSet("B"), new HashMap<String, Object>());
		
		dataStore.submit(elementWithTagA);
		dataStore.submit(elementWithTagB);

		Collection<Data> dataList = dataStore.get(Sets.<String>newHashSet());
		assertTrue(dataList.contains(elementWithTagA));
		assertTrue(dataList.contains(elementWithTagB));
		
		dataStore.remove(Sets.newHashSet("A"));
		
		dataList = dataStore.get(Sets.<String>newHashSet());
		assertFalse(dataList.contains(elementWithTagA));
		assertTrue(dataList.contains(elementWithTagB));
		
	}

}
