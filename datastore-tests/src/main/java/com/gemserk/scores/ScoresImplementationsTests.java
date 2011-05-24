package com.gemserk.scores;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.google.common.collect.Sets;

public class ScoresImplementationsTests {

	public static void main(String[] args) {
		test4();
	}

	public static void test1() {
		Scores scoresHttpImpl = new ScoresHttpImpl("dsadfasfdsfaasd", "http://localhost:8080");

		HashSet<String> tags = Sets.newHashSet("level1", "easy", "superuser");

		HashMap<String, Object> data = new HashMap<String, Object>() {{ 
			put("timeAlive", 180);
			put("enemiesKilled", 500);
		}};

		String scoreId = scoresHttpImpl.submit(new Score("player", 12500, tags, data));

		System.out.println("Score submited with id: " + scoreId);
	}

	public static void test2() {
		ScoresHttpImpl scoresHttpImpl = new ScoresHttpImpl("dsadfasfdsfaasd", "http://localhost:8080");

		HashSet<String> tags = Sets.newHashSet("level1", "easy");

		Collection<Score> scores = scoresHttpImpl.getOrderedByPoints(tags, 10, true);

		for (Score score : scores) {
			System.out.println(score.toString());
		}
	}

	public static void test3() {
		ScoresFileImpl scoresFileImpl = new ScoresFileImpl(new File(System.getProperty("user.home") + "/.gemserk/jylonwars/scores.data"), new ScoreSerializerJSONImpl());

		HashSet<String> tags = Sets.newHashSet("easy");

		Collection<Score> scores = scoresFileImpl.getOrderedByPoints(tags, 10, true);

		for (Score score : scores) {
			System.out.println(score.toString());
		}
	}

	public static void test4() {
		
		Scores scores = new ScoresHttpImpl("9eba9d1d13f8190d934e3dd0f58f58ca", "http://gemserkscores.appspot.com/");

		HashSet<String> tags = Sets.newHashSet("level01");
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		scores.submit(new Score("ruben01", 50, tags, data));

	}
	
}
