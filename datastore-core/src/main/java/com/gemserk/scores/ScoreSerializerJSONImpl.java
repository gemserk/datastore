package com.gemserk.scores;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

public class ScoreSerializerJSONImpl implements ScoreSerializer {

	private final Gson gson;

	public ScoreSerializerJSONImpl() {
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

	@Override
	public Collection<Score> parse(String scoresString) {
		if ("".equals(scoresString) || scoresString == null)
			return new ArrayList<Score>();
		Type collectionType = new TypeToken<Collection<Score>>() {
		}.getType();
		return gson.fromJson(scoresString, collectionType);
	}

	public String serialize(Collection<Score> scores) {
		return gson.toJson(scores);
	}

	@Override
	public String serializeScoreData(Map<String, Object> data) {
		return gson.toJson(data);
	}
}