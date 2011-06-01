package com.gemserk.datastore.profiles;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class ProfileJsonSerializer {

	Gson gson = new Gson();

	public ProfileJsonSerializer() {
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

	public Profile parse(String profileJson) {
		return gson.fromJson(profileJson, Profile.class);
	}

	public String serialize(Profile profile) {
		return gson.toJson(profile);
	}

}