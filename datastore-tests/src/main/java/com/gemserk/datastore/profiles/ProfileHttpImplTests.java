package com.gemserk.datastore.profiles;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.gemserk.scores.Score;
import com.gemserk.scores.Scores;
import com.gemserk.scores.ScoresHttpImpl;


public class ProfileHttpImplTests {

	public static void main(String[] args) {
		test3();
	}

	private static void test1() {
		ProfilesHttpImpl profiles = new ProfilesHttpImpl("http://localhost:8080/");
		Profile profile = profiles.register("guest-123141", true);
		System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", profile.getPrivateKey(), profile.getPublicKey(), profile.getName(), profile.isGuest()));
	}
	
	private static void test2() {
		ProfilesHttpImpl profiles = new ProfilesHttpImpl("http://localhost:8080/");
		
		Profile profile = profiles.register("guest-123141", true);
		profile.setName("arielsan");
		
		profile = profiles.update(profile);
		
		System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", profile.getPrivateKey(), profile.getPublicKey(), profile.getName(), profile.isGuest()));
	}
	
	private static void test3() {
		Profiles profiles = new ProfilesHttpImpl("http://localhost:8080/");
		Scores scores = new ScoresHttpImpl("dsadfasfdsfaasd", "http://localhost:8080/");
		
		Profile profile = profiles.register("guest-123141", true);
		
		scores.submit(new Score(profile.getPublicKey(), profile.getName(), 2500, new HashSet<String>(), new HashMap<String, Object>()));
		scores.submit(new Score(profile.getPublicKey(), profile.getName(), 1500, new HashSet<String>(), new HashMap<String, Object>()));
		scores.submit(new Score(profile.getPublicKey(), profile.getName(), 5400, new HashSet<String>(), new HashMap<String, Object>()));
		
		Collection<Score> scoreList = scores.getOrderedByPoints(new HashSet<String>(), 20, false);
		for (Score score : scoreList) {
			System.out.println(score);
		}
		
		profile.setName("pipote");
		profile = profiles.update(profile);
		
		System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", profile.getPrivateKey(), profile.getPublicKey(), profile.getName(), profile.isGuest()));
		
		scoreList = scores.getOrderedByPoints(new HashSet<String>(), 20, false);
		for (Score score : scoreList) {
			System.out.println(score);
		}
		
	}
	
}
