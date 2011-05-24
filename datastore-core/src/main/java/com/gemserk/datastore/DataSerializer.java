package com.gemserk.datastore;

import java.util.Collection;

public interface DataSerializer {

	Collection<Data> parse(String data);

	String serialize(Collection<Data> dataCollection);

}