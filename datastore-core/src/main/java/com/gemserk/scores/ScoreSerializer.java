package com.gemserk.scores;

import java.util.Collection;

public interface ScoreSerializer {

	Collection<Score> parse(String scoresString);

	String serialize(Collection<Score> scores);

}