package com.gemserk.datastore;

import java.util.Collection;

public interface DataSerializer {

	Collection<Data> parseData(String data);

	String serializeData(Collection<Data> dataCollection);

}