package com.gemserk.datastore;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

public class DataSerializerJSONImpl implements DataSerializer {

	private final Gson gson;

	public DataSerializerJSONImpl() {
		gson = new GsonBuilder().registerTypeAdapter(Object.class, new JsonDeserializer<Object>() {
			@Override
			public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				JsonPrimitive value = json.getAsJsonPrimitive();
				if (value.isBoolean()) {
					return value.getAsBoolean();
				} else if (value.isNumber()) {
					return value.getAsNumber();
				} else {
					return value.getAsString();
				}
			}
		}).create();
	}

	public Collection<Data> parse(String dataCollectionJson) {
		Type collectionType = new TypeToken<Collection<Data>>() {}.getType();
		return gson.fromJson(dataCollectionJson, collectionType);
	}

	public String serialize(Collection<Data> dataCollection) {
		return gson.toJson(dataCollection);
	}

}