package com.gemserk.scores;

import java.util.Collection;

import net.sf.json.JSONArray;

public class ScoreSerializerJSONImpl implements ScoreSerializer {

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Score> parse(String scoresString) {
		return JSONArray.toCollection(JSONArray.fromObject(scoresString), Score.class);
	}

	public String serialize(Collection<Score> scores) {
		JSONArray jobject = JSONArray.fromObject(scores);
		String jsonData = jobject.toString(1);
		return jsonData;
	}

}