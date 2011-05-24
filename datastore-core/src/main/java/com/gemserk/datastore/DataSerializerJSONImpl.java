package com.gemserk.datastore;

import java.util.Collection;

import net.sf.json.JSONArray;

public class DataSerializerJSONImpl implements DataSerializer {

	@SuppressWarnings("unchecked")
	public Collection<Data> parseData(String data) {
		return JSONArray.toCollection(JSONArray.fromObject(data), Data.class);
	}

	public String serializeData(Collection<Data> dataCollection) {
		JSONArray jobject = JSONArray.fromObject(dataCollection);
		String jsonData = jobject.toString(1);
		return jsonData;
	}

}