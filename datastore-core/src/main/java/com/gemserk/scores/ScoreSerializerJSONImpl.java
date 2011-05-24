package com.gemserk.scores;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ScoreSerializerJSONImpl implements ScoreSerializer {

	private final Gson gson = new Gson();

	@Override
	public Collection<Score> parse(String scoresString) {
		if ("".equals(scoresString) || scoresString == null)
			return new ArrayList<Score>();
		Type collectionType = new TypeToken<Collection<Score>>(){}.getType();
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