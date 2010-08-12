package com.gemserk.scores;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.google.common.collect.Sets;

public class ScoresImplementationsTests {

	public static void main(String[] args) {
		test2();
	}

	public static void test1() {
		ScoresHttpImpl scoresHttpImpl = new ScoresHttpImpl("dsadfasfdsfaasd", "http://localhost:8080");

		HashSet<String> tags = Sets.newHashSet("level1", "easy", "superuser");

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("playedTime", 10);
		data.put("enemiesDead", 500);

		String scoreId = scoresHttpImpl.submit(new Score("player", 10000, tags, data));

		System.out.println(scoreId);
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
		ScoresFileImpl scoresFileImpl = new ScoresFileImpl(new File(System.getProperty("user.home") + "/.gemserk/jylonwars/scores.data"));

		HashSet<String> tags = Sets.newHashSet("easy");

		Collection<Score> scores = scoresFileImpl.getOrderedByPoints(tags, 10, true);

		for (Score score : scores) {
			System.out.println(score.toString());
		}
	}

}
