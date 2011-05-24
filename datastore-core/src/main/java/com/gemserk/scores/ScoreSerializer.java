package com.gemserk.scores;

import java.util.Collection;
import java.util.Map;

public interface ScoreSerializer {

	Collection<Score> parse(String scores);

	String serialize(Collection<Score> scores);

	String serializeScoreData(Map<String, Object> data);
	
}