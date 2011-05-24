package com.gemserk.datastore;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataSerializerJSONImpl implements DataSerializer {
	
	private final Gson gson = new Gson();

	public Collection<Data> parse(String data) {
		Type collectionType = new TypeToken<Collection<Data>>(){}.getType();
		return gson.fromJson(data, collectionType);
	}

	public String serialize(Collection<Data> dataCollection) {
		return gson.toJson(dataCollection);
	}
	
}